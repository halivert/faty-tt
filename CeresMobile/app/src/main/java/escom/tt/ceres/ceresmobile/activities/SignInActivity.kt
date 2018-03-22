package escom.tt.ceres.ceresmobile.activities

import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast

import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.DoctorSignInFragment
import escom.tt.ceres.ceresmobile.fragments.LoginFragment
import escom.tt.ceres.ceresmobile.fragments.MainFragment
import escom.tt.ceres.ceresmobile.fragments.PatientSignInFragment

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.MEDICO
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO

class SignInActivity : AppCompatActivity(), MainFragment.OnMainInteraction, PatientSignInFragment.CFPacienteRegistro, DoctorSignInFragment.OnDoctorSignInInteraction, LoginFragment.OnLoginInteraction {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.actividad_registro)

    val myToolbar = findViewById<Toolbar>(R.id.appBar)
    setSupportActionBar(myToolbar)
    val actionBar = supportActionBar
    actionBar!!.setDisplayShowTitleEnabled(false)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    val ft = fragmentManager.beginTransaction()
    val fragment = MainFragment()
    ft.replace(R.id.frameFragment, fragment)
    ft.commit()
  }

  override fun onBackPressed() {
    if (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStack()
    } else {
      super.onBackPressed()
      finish()
    }
  }

  override fun userSelection(user: Int) {
    val ft = fragmentManager.beginTransaction()
    if (user == PACIENTE) {
      val fragment = PatientSignInFragment()
      ft.replace(R.id.frameFragment, fragment)
    } else if (user == MEDICO) {
      val fragment = DoctorSignInFragment()
      ft.replace(R.id.frameFragment, fragment)
    } else {
      Toast.makeText(this, R.string.tipo_usuario_incorrecto, Toast.LENGTH_SHORT).show()
      return
    }
    ft.addToBackStack(null)
    ft.commit()
  }

  override fun successfulSignIn(email: String, keyword: String) {
    val ft = fragmentManager.beginTransaction()
    val fragment = LoginFragment.newInstance(email, keyword)
    ft.replace(R.id.frameFragment, fragment)
    ft.commit()
  }

  override fun successfulLogin(idUser: Int, rolUser: Int) {
    var intent: Intent? = null
    if (rolUser == PACIENTE) {
      intent = Intent(this, PatientMainActivity::class.java)
    } else if (rolUser == MEDICO) {
      intent = Intent(this, DoctorMainActivity::class.java)
    }
    intent!!.putExtra(ID_USUARIO, idUser)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    finish()
  }

  override fun registroExitoso(email: String, keyword: String) {

  }
}
