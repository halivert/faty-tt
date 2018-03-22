package escom.tt.ceres.ceresmobile.activities

import android.app.AlertDialog
import android.app.FragmentTransaction
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.DoctorMainFragment

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN

class DoctorMainActivity : AppCompatActivity(), DoctorMainFragment.OnDoctorMainInteraction {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.actividad_principal_medico)

    val myToolbar = findViewById<Toolbar>(R.id.appBar)
    setSupportActionBar(myToolbar)
    val actionBar = supportActionBar
    actionBar!!.setDisplayShowTitleEnabled(false)

    val ft = fragmentManager.beginTransaction()
    val fragment = DoctorMainFragment()
    ft.replace(R.id.frameFragment, fragment)
    ft.commit()
  }

  override fun onBackPressed() {
    if (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStack()
    } else {
      super.onBackPressed()
    }
  }

  fun logOut(view: View) {
    AlertDialog.Builder(this)
        .setTitle(R.string.cerrando_sesion)
        .setMessage(R.string.confirmar_cerrar_sesion)
        .setPositiveButton(R.string.si) { dialog, which ->
          val editor = getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit()
          editor.clear()
          editor.apply()

          val intent = Intent(applicationContext, MainActivity::class.java)
          intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(intent)
          finish()
        }
        .setNegativeButton(R.string.no, null)
        .show()
  }
}