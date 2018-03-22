package escom.tt.ceres.ceresmobile.activities

import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import escom.tt.ceres.ceresmobile.fragments.LoginFragment
import escom.tt.ceres.ceresmobile.R

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.MEDICO
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_ROL
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginInteraction {

  public override fun onResume() {
    val preferences = getSharedPreferences(INSTANCE.getLOGIN(), Context.MODE_PRIVATE)
    val idUsuario = preferences.getInt(INSTANCE.getID_USUARIO(), -1)
    val rol = preferences.getInt(INSTANCE.getID_ROL(), -1)

    if (idUsuario > -1 && rol > -1) {
      successfulLogin(idUsuario, rol)
    }
    super.onResume()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.actividad_principal)

    val preferences = getSharedPreferences(INSTANCE.getLOGIN(), Context.MODE_PRIVATE)
    val idUsuario = preferences.getInt(INSTANCE.getID_USUARIO(), -1)
    val rol = preferences.getInt(INSTANCE.getID_ROL(), -1)

    if (idUsuario > -1 && rol > -1) {
      successfulLogin(idUsuario, rol)
    } else {
      val myToolbar = findViewById<Toolbar>(R.id.appBar)
      setSupportActionBar(myToolbar)
      val actionBar = supportActionBar
      actionBar!!.setDisplayShowTitleEnabled(false)

      val ft = fragmentManager.beginTransaction()
      val fragment = LoginFragment()
      ft.replace(R.id.frameFragment, fragment)
      ft.commit()
    }
  }

  override fun onBackPressed() {
    if (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStack()
    } else {
      super.onBackPressed()
    }
  }

  fun irARegistro(view: View) {
    val intent = Intent(this, SignInActivity::class.java)
    startActivityForResult(intent, 611)
  }

  override fun successfulLogin(idUser: Int, rolUser: Int) {
    var intent: Intent? = null
    if (rolUser == INSTANCE.getPACIENTE()) {
      intent = Intent(this, PatientMainActivity::class.java)
    } else if (rolUser == INSTANCE.getMEDICO()) {
      intent = Intent(this, DoctorMainActivity::class.java)
    }
    intent!!.putExtra(INSTANCE.getID_USUARIO(), idUser)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    finish()
  }
}
