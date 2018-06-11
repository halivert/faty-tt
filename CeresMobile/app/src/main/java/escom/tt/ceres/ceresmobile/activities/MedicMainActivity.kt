package escom.tt.ceres.ceresmobile.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.*
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.models.User
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.USER_JSON
import org.json.JSONObject

class MedicMainActivity :
    AppCompatActivity(),
    MedicMainFragment.OnDoctorMainInteraction,
    MedicGenerateCodeFragment.OnDoctorGenerateCodeInteraction,
    MedicPatientsFragment.OnDoctorPatientsInteraction,
    PatientDetailFragment.OnPatientDetailInteraction,
    DietDetailFragment.OnDietDetailInteraction,
    PatientDietFragment.OnDietListener {

  private lateinit var navigationView: BottomNavigationView

  override fun showDiets(idPatient: Int) {
    val fragment = PatientDietFragment.newInstance(idPatient, true)
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.frameFragment, fragment, PatientDietFragment.TAG)
      addToBackStack(null)
      commit()
    }
  }

  override fun onSelectedPatient(position: Int) {
    val fragment =
        PatientDetailFragment.newInstance(patients[position].toJSONString())
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.frameFragment, fragment, PatientDetailFragment.TAG)
      addToBackStack(null)
      commit()
    }
  }

  override fun onSelectedDiet(idDiet: Int) {
    val fragment = DietDetailFragment.newInstance(idDiet)
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.frameFragment, fragment, DietDetailFragment.TAG)
      addToBackStack(null)
      commit()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.medic_main_activity)
    setSupportActionBar(findViewById(R.id.app_bar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    homeFragment = MedicMainFragment.newInstance(idUser)
    patientsFragment = MedicPatientsFragment.newInstance(true)
    generateCodeFragment = MedicGenerateCodeFragment.newInstance()
    navigationView = findViewById(R.id.bottom_navigation_view)

    val preferences = getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    idUser = intent.getIntExtra(ID_USUARIO, -1)
    val medicJSON = preferences.getString(USER_JSON, User().toJSONString())
    try {
      medic = User(JSONObject(medicJSON))
    } catch (e: Exception) {
    }

    /*
    val userName = preferences.getString(NAME, null)
    val lastName = preferences.getString(APELLIDO_PATERNO, null)
    val mothersLastName = preferences.getString(APELLIDO_MATERNO, null)

    val textView = findViewById<TextView>(R.id.userName)
    if (textView != null) {
      textView.text = "$userName $lastName $mothersLastName"
    }
    */

    if (idUser >= 0) {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.frameFragment, patientsFragment, MedicPatientsFragment.TAG)
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
        commit()
      }
    }

    navigationView.setOnNavigationItemSelectedListener {
      navigationItemSelectedListener(it)
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    /*
    val frag = supportFragmentManager.findFragmentById(R.id.frameFragment)
    val name = frag.tag
    Log.w("Save", "Save instance ${supportFragmentManager.backStackEntryCount} $name")
    */
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    Log.w("Qué", "Ola")
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
        supportFragmentManager.beginTransaction().apply {
          replace(R.id.frameFragment, patientsFragment, MedicPatientsFragment.TAG)
          supportFragmentManager.popBackStack(
              null,
              FragmentManager.POP_BACK_STACK_INCLUSIVE)
          commit()
        }
      }
      it.itemId == R.id.generate_code_item -> {
        supportFragmentManager.beginTransaction().apply {
          replace(R.id.frameFragment, generateCodeFragment, MedicGenerateCodeFragment.TAG)
          supportFragmentManager.popBackStack(
              null,
              FragmentManager.POP_BACK_STACK_INCLUSIVE)
          commit()
        }
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
    if (supportFragmentManager.backStackEntryCount > 0) {
      supportFragmentManager.popBackStack()
    } else {
      val itemPatients = navigationView.menu.findItem(R.id.patients_item)
      val patientsChecked = itemPatients.isChecked

      if (patientsChecked) return super.onBackPressed()

      itemPatients.isChecked = true
      navigationItemSelectedListener(itemPatients)
    }
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

  /*
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
  */

  companion object {
    var idUser = -1
    var medic = User(JSONObject())
    var patients: MutableList<Patient> = mutableListOf()

    private lateinit var homeFragment: MedicMainFragment
    private lateinit var patientsFragment: MedicPatientsFragment
    private lateinit var generateCodeFragment: MedicGenerateCodeFragment
  }
}