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
import android.util.Log
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
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_MEDICO
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

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
    setContentView(R.layout.doctor_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    idUser = intent.getIntExtra(Constants.Strings.ID_USUARIO, -1)

    progressBar = findViewById(R.id.progressBar)
    homeFragment = DoctorMainFragment.newInstance(idUser)
    patientsFragment = DoctorPatientsFragment.newInstance()
    generateCodeFragment = DoctorGenerateCodeFragment.newInstance()
    navigationView = findViewById(R.id.bottom_navigation_view)
    val welcome = getString(R.string.welcome)
    findViewById<TextView>(R.id.title_bar_text).text = welcome

    launch(UI) {
      getPatients()

      if (idUser >= 0) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
      }

      navigationView.setOnNavigationItemSelectedListener {
        navigationItemSelectedListener(it)
      }
    }
  }

  private fun navigationItemSelectedListener(it: MenuItem): Boolean {
    when {
      it.itemId == R.id.home_item -> {
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
      }
      it.itemId == R.id.patients_item -> {
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, patientsFragment).commit()
      }
      it.itemId == R.id.generate_code_item -> {
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, generateCodeFragment).commit()
      }
      it.itemId == R.id.sign_out_item -> {
        logOut(null)
      }
    }

    return true
  }

  override fun onBackPressed() {
    var itemHome = navigationView.menu.findItem(R.id.home_item)
    var homeChecked = itemHome.isChecked

    if (homeChecked) return super.onBackPressed()

    itemHome.isChecked = true
    navigationItemSelectedListener(itemHome)
  }

  private fun logOut(view: View?) {
    var noButtonClick = DialogInterface.OnClickListener { _, _ ->
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

  suspend fun getPatients() {
    var urlPatients = "$URL_MEDICO/$idUser/pacientes/"
    progressBar.visibility = View.VISIBLE
    var queue = CeresRequestQueue.getInstance(this)
    var response = queue.apiArrayRequest(GET, urlPatients, null).await()

    patients.clear()
    for (i in 0 until response.length()) {
      var responseObject = response.getJSONObject(i)
      var patient: Patient?
      if (responseObject.has(ERROR)) {
        Toast.makeText(this, responseObject.getString(ERROR), Toast.LENGTH_LONG).show()
        break
      } else {
        patient = Patient(responseObject)
        patients.add(patient)
      }
    }
    progressBar.visibility = View.INVISIBLE
  }

  companion object {
    var idUser = -1
    var patients: MutableList<Patient> = mutableListOf()
  }
}