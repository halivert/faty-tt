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
import android.widget.Toast
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.PatientDietFragment
import escom.tt.ceres.ceresmobile.fragments.PatientMainFragment
import escom.tt.ceres.ceresmobile.fragments.PatientSugarRecordingFragment
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.sql.Timestamp

class PatientMainActivity : AppCompatActivity(),
    PatientMainFragment.OnPatientMainInteraction,
    PatientSugarRecordingFragment.OnSugarRegisterListener,
    PatientDietFragment.OnDietListener {

  private var idPatient = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.patient_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    idPatient = intent.getIntExtra(ID_USUARIO, -1)

    val homeFragment = PatientMainFragment.newInstance(idPatient)
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

    launch(UI) {
      getDiets()

      if (idPatient >= 0) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
      }

      bottomNavigationView.setOnNavigationItemSelectedListener {
        navigationItemSelectedListener(it)
      }
    }
  }

  private suspend fun getDiets() {
    var urlDiets = "$URL_PATIENT/$idPatient/dietas"
    var response = CeresRequestQueue.getInstance(this).apiArrayRequest(GET, urlDiets, null).await()

    for (i in 0 until response.length()) {
      var responseObject = response.getJSONObject(i)
      if (responseObject.has(ERROR)) {
        Toast.makeText(this, responseObject.getString(ERROR), Toast.LENGTH_LONG).show()
        break
      }

      var idDiet = if (responseObject.has("id_DIETA")) responseObject.getInt("id_DIETA") else -1
      var idPatient = if (responseObject.has("id_PACIENTE")) responseObject.getInt("id_PACIENTE") else -1
      var idDoctor = if (responseObject.has("id_MEDICO")) responseObject.getInt("id_MEDICO") else -1
      var description = if (responseObject.has("descripcion")) responseObject.getString("descripcion") else ""
      var assignDate = if (responseObject.has("fecha_ASIGNACION"))
        Timestamp(responseObject.getLong("fecha_ASIGNACION")) else Timestamp(0)

      var diet = Diet(idDiet, description, assignDate, idPatient, idDoctor)
      diets.add(diet)
    }
  }

  private fun navigationItemSelectedListener(it: MenuItem): Boolean {
    val homeFragment = PatientMainFragment.newInstance(idPatient)
    val sugarFragment = PatientSugarRecordingFragment.newInstance()
    val dietFragment = PatientDietFragment.newInstance()

    when {
      it.itemId == R.id.home_item -> {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
      }
      it.itemId == R.id.generateDietItem -> {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, dietFragment).commit()
      }
      it.itemId == R.id.registerSugarItem -> {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, sugarFragment).commit()
      }
      it.itemId == R.id.sign_out_item -> {
        logOut(null)
      }
    }

    return true
  }

  override fun onBackPressed() {
    val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
    var itemHome = navigationView.menu.findItem(R.id.home_item)
    var homeChecked = itemHome.isChecked

    if (homeChecked) {
      super.onBackPressed()
      return
    }

    itemHome.isChecked = true
    navigationItemSelectedListener(itemHome)
  }

  private fun logOut(view: View?) {
    val noButtonPressed = DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
      onBackPressed()
    }

    AlertDialog.Builder(this)
        .setTitle(R.string.logging_out)
        .setMessage(R.string.log_out_confirmation)
        .setPositiveButton(R.string.yes) { _, _ ->
          val editor = getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit()
          editor.clear()
          editor.apply()

          val intent = Intent(applicationContext, MainActivity::class.java)
          intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(intent)
          finish()
        }
        .setNegativeButton(R.string.no, noButtonPressed)
        .setCancelable(false)
        .show()
  }

  override fun endSugarRegister(): Int {
    onBackPressed()
    return 0
  }

  companion object {
    var diets: MutableList<Diet> = mutableListOf()
  }
}
