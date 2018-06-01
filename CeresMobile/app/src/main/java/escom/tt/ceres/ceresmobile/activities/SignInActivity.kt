package escom.tt.ceres.ceresmobile.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.LoginFragment
import escom.tt.ceres.ceresmobile.fragments.MainFragment
import escom.tt.ceres.ceresmobile.fragments.MedicSignInFragment
import escom.tt.ceres.ceresmobile.fragments.PatientSignInFragment
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.MEDICO
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO

class SignInActivity : AppCompatActivity(),
    MainFragment.OnMainInteraction,
    PatientSignInFragment.OnPatientSignInInteraction,
    MedicSignInFragment.OnDoctorSignInInteraction,
    LoginFragment.OnLoginInteraction {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.sign_in_activity)

    val myToolbar = findViewById<Toolbar>(R.id.appBar)
    setSupportActionBar(myToolbar)
    val actionBar = supportActionBar
    actionBar!!.setDisplayShowTitleEnabled(false)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    val fragment = MainFragment()
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.frameFragment, fragment)
      commit()
    }
  }

  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount > 0)
      supportFragmentManager.popBackStack()
    else {
      super.onBackPressed()
      finish()
    }
  }

  override fun userSelection(user: Int) {
    val ft = supportFragmentManager.beginTransaction()
    when (user) {
      PACIENTE -> {
        val fragment = PatientSignInFragment()
        ft.replace(R.id.frameFragment, fragment)
      }
      MEDICO -> {
        val fragment = MedicSignInFragment()
        ft.replace(R.id.frameFragment, fragment)
      }
      else -> {
        Toast.makeText(this, R.string.user_type_invalid, Toast.LENGTH_SHORT).show()
        return
      }
    }
    ft.addToBackStack(null)
    ft.commit()
  }

  override fun successfulPatientSignIn(email: String, keyword: String) {
    val ft = supportFragmentManager.beginTransaction()
    val fragment = LoginFragment.newInstance(email, keyword)
    ft.replace(R.id.frameFragment, fragment)
    ft.commit()
  }

  override fun successfulDoctorSignIn(email: String, keyword: String) {
    val ft = supportFragmentManager.beginTransaction()
    val fragment = LoginFragment.newInstance(email, keyword)
    ft.replace(R.id.frameFragment, fragment)
    ft.commit()
  }

  override fun successfulLogin(idUser: Int, rolUser: Int) {
    var intent: Intent? = null
    if (rolUser == PACIENTE)
      intent = Intent(this, PatientMainActivity::class.java)
    else if (rolUser == MEDICO)
      intent = Intent(this, MedicMainActivity::class.java)

    intent!!.putExtra(ID_USUARIO, idUser)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    finish()
  }

}
