package escom.tt.ceres.ceresmobile.activities

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
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
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.DietDetailFragment
import escom.tt.ceres.ceresmobile.fragments.PatientDietFragment
import escom.tt.ceres.ceresmobile.fragments.PatientMainFragment
import escom.tt.ceres.ceresmobile.fragments.PatientSugarRecordingFragment
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import escom.tt.ceres.ceresmobile.tools.MyReceiver
import java.sql.Timestamp
import java.util.*


class PatientMainActivity : AppCompatActivity(),
    PatientMainFragment.OnPatientMainInteraction,
    PatientSugarRecordingFragment.OnSugarRegisterListener,
    PatientDietFragment.OnDietListener,
    DietDetailFragment.OnDietDetailInteraction {
  private var pendingIntent: PendingIntent? = null

  override fun onSelectedDiet(position: Int) {
    val dietDetailFragment =
        DietDetailFragment.newInstance(diets[position].idDiet, position)
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameFragment, dietDetailFragment).commit()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.patient_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    idPatient = intent.getIntExtra(ID_USUARIO, -1)

    val homeFragment = PatientMainFragment.newInstance(idPatient)
    val bottomNavigationView =
        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

    var now = Calendar.getInstance()
    var nowHour = now.get(Calendar.HOUR_OF_DAY)
    var alarmHour = Calendar.getInstance()

    var tick = "Ceres"
    var title = "Hora de "
    var text = "Es la hora de "
    var image = R.drawable.ic_utensils_white

    when (nowHour) {
      in 0..6, in 21..23 -> {
        if (nowHour in 21..23)
          alarmHour.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) + 1)
        alarmHour.set(Calendar.HOUR_OF_DAY, 7)
        title += "desayunar"
        text += "desayunar"
        image = R.drawable.ic_shield_alt_white
      }
      in 7..10 -> {
        alarmHour.apply {
          set(Calendar.HOUR_OF_DAY, 11)
        }
        title += "comer colación 1"
        text += "comer colación 1"
        image = R.drawable.ic_bolt_white
      }
      in 11..13 -> {
        alarmHour.apply {
          set(Calendar.HOUR_OF_DAY, 14)
        }
        title += "comer"
        text += "comer"
        image = R.drawable.ic_calendar_alt_white
      }
      in 14..16 -> {
        alarmHour.apply {
          set(Calendar.HOUR_OF_DAY, 17)
        }
        title += "comer colación 2"
        text += "comer colación 2"
        image = R.drawable.ic_star_white
      }
      in 17..20 -> {
        alarmHour.apply {
          set(Calendar.HOUR_OF_DAY, 21)
        }
        title += "cenar"
        text += "cenar"
        image = R.drawable.ic_star_white
      }
    }

    text += ", revisa tus dietas"

    val myIntent =
        Intent(this@PatientMainActivity, MyReceiver::class.java).apply {
          putExtra("TICK", tick)
          putExtra("TITLE", title)
          putExtra("TEXT", text)
          putExtra("ID_IMAGE", image)
        }
    pendingIntent =
        PendingIntent.getBroadcast(this@PatientMainActivity, 0, myIntent, 0)

    Log.e("Día", alarmHour.get(Calendar.DAY_OF_MONTH).toString())
    Log.e("Hora", alarmHour.get(Calendar.HOUR_OF_DAY).toString())

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (alarmHour.timeInMillis != now.timeInMillis) {
      alarmHour.apply {
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
      }
      alarmManager.set(AlarmManager.RTC, alarmHour.timeInMillis, pendingIntent)
    }

    if (idPatient >= 0) {
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
    }

    bottomNavigationView.setOnNavigationItemSelectedListener {
      navigationItemSelectedListener(it)
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
          clearPatient()
          startActivity(intent)
          finish()
        }
        .setNegativeButton(R.string.no, noButtonPressed)
        .setCancelable(false)
        .show()
  }

  private fun clearPatient() {
    diets.clear()
    idPatient = -1
  }

  override fun endSugarRegister(): Int {
    onBackPressed()
    return 0
  }

  companion object {
    var diets: MutableList<Diet> = mutableListOf()
    var idPatient = -1
  }
}
