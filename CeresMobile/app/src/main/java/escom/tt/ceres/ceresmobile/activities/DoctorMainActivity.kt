package escom.tt.ceres.ceresmobile.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.DoctorGenerateCodeFragment
import escom.tt.ceres.ceresmobile.fragments.DoctorMainFragment
import escom.tt.ceres.ceresmobile.fragments.DoctorPatientsFragment
import escom.tt.ceres.ceresmobile.fragments.PatientDetailFragment
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.models.User
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MESSAGE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPONSE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.UNSUCCESSFUL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_MEDICO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.USER_JSON
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject

class DoctorMainActivity :
    AppCompatActivity(),
    DoctorMainFragment.OnDoctorMainInteraction,
    DoctorGenerateCodeFragment.OnDoctorGenerateCodeInteraction,
    DoctorPatientsFragment.OnDoctorPatientsInteraction,
    PatientDetailFragment.OnPatientDetailInteraction {
  override fun onSelectedPatient(position: Int) {
    val patientDetailFragment =
        PatientDetailFragment.newInstance(patients[position].toJSONString())
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameFragment, patientDetailFragment).commit()
  }

  private lateinit var homeFragment: DoctorMainFragment
  private lateinit var patientsFragment: DoctorPatientsFragment
  private lateinit var generateCodeFragment: DoctorGenerateCodeFragment
  private lateinit var navigationView: BottomNavigationView
  private lateinit var progressBar: ProgressBar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.medic_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    val preferences = getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    idUser = intent.getIntExtra(ID_USUARIO, -1)
    val medicJSON = preferences.getString(USER_JSON, User().toJSONString())
    try {
      medic = User(JSONObject(medicJSON))
    } catch (e: Exception) {
    }

    progressBar = findViewById(R.id.progressBar)
    homeFragment = DoctorMainFragment.newInstance(idUser)
    patientsFragment = DoctorPatientsFragment.newInstance()
    generateCodeFragment = DoctorGenerateCodeFragment.newInstance()
    navigationView = findViewById(R.id.bottom_navigation_view)
    findViewById<TextView>(R.id.title_bar_text).text = getString(R.string.welcome)

    val userName = preferences.getString(NAME, null)
    val lastName = preferences.getString(APELLIDO_PATERNO, null)
    val mothersLastName = preferences.getString(APELLIDO_MATERNO, null)

    val textView = findViewById<TextView>(R.id.userName)
    if (textView != null) {
      textView.text = "$userName $lastName $mothersLastName"
    }

    launch(UI) {
      getPatients()

      if (idUser >= 0) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, patientsFragment).commit()
      }

      navigationView.setOnNavigationItemSelectedListener {
        navigationItemSelectedListener(it)
      }
    }
  }

  private fun navigationItemSelectedListener(it: MenuItem): Boolean {
    when {
    /*
    it.itemId == R.id.home_item -> {
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
    }
    */
      it.itemId == R.id.patients_item -> {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, patientsFragment).commit()
      }
      it.itemId == R.id.generate_code_item -> {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, generateCodeFragment).commit()
      }
      it.itemId == R.id.sign_out_item -> {
        logOut()
      }
    }

    return true
  }

  override fun onBackPressed() {
    // val itemHome = navigationView.menu.findItem(R.id.home_item)
    // val homeChecked = itemHome.isChecked
    val itemPatients = navigationView.menu.findItem(R.id.patients_item)
    val patientsChecked = itemPatients.isChecked

    if (patientsChecked) return super.onBackPressed()

    itemPatients.isChecked = true
    navigationItemSelectedListener(itemPatients)
  }

  private fun logOut() {
    val noButtonClick = DialogInterface.OnClickListener { _, _ ->
      onBackPressed()
    }

    AlertDialog.Builder(this)
        .setTitle(R.string.logging_out)
        .setMessage(R.string.log_out_confirmation)
        .setPositiveButton(R.string.yes) { _, _ ->
          val editor = getSharedPreferences(Constants.Strings.LOGIN, Context.MODE_PRIVATE).edit()
          editor.clear()
          editor.apply()

          val intent = Intent(applicationContext, MainActivity::class.java)
          intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(intent)
          finish()
        }
        .setNegativeButton(R.string.no, noButtonClick)
        .setCancelable(false)
        .show()
  }

  private suspend fun getPatients() {
    patients.clear()
    val urlPatients = "$URL_MEDICO/$idUser/pacientes/"
    progressBar.visibility = View.VISIBLE
    val queue = CeresRequestQueue.getInstance(this)
    val response = queue.apiArrayRequest(GET, urlPatients, null).await()

    if (response.length() > 0) {
      if (response.getJSONObject(0).has(UNSUCCESSFUL)) {
        Toast.makeText(
            this, getString(R.string.patients_dont_found), Toast.LENGTH_LONG).show()
        progressBar.visibility = View.INVISIBLE
        return
      }
    }

    for (i in 0 until response.length()) {
      val responseObject = response.getJSONObject(i)
      var patient: Patient?
      if (responseObject.has(RESPONSE)) {
        val responseString = responseObject.getString(RESPONSE)
        if (responseString != ERROR) {
          patient = Patient(responseObject)
          patients.add(patient)
        } else {
          if (responseObject.has(MESSAGE)) {
            Toast.makeText(
                this, responseObject.getString(MESSAGE), Toast.LENGTH_LONG).show()
          }
          continue
        }
      }
    }

    progressBar.visibility = View.INVISIBLE
  }

  companion object {
    var idUser = -1
    var medic = User(JSONObject())
    var patients: MutableList<Patient> = mutableListOf()
  }
}