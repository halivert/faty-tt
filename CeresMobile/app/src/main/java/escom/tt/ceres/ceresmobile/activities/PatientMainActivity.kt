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
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.*
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DIETS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FRAGMENT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.MyReceiver
import java.util.*

class PatientMainActivity : AppCompatActivity(),
    PatientMainFragment.OnPatientMainInteraction,
    PatientSugarRecordingFragment.OnSugarRegisterListener,
    PatientDietFragment.OnDietListener,
    DietDetailFragment.OnDietDetailInteraction,
    NotificationTestFragment.OnNotificationTestInteraction {
  private var pendingIntent: PendingIntent? = null

  override fun onSelectedDiet(idDiet: Int) {
    val dietDetailFragment = DietDetailFragment.newInstance(idDiet)
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameFragment, dietDetailFragment).commit()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.patient_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    idPatient = intent.getIntExtra(ID_USUARIO, -1)
    val loadFragment = intent.getStringExtra(FRAGMENT)

    if (idPatient != -1) {
      val homeFragment = PatientMainFragment.newInstance(idPatient)
      val dietsFragment = PatientDietFragment.newInstance(idPatient)
      val bottomNavigationView =
          findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

      val now = Calendar.getInstance()
      val nowHour = now.get(Calendar.HOUR_OF_DAY)
      val alarmHour = Calendar.getInstance()

      val tick = "Ceres"
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

      val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
      if (alarmHour.timeInMillis != now.timeInMillis) {
        alarmHour.apply {
          set(Calendar.MINUTE, 0)
          set(Calendar.SECOND, 0)
          set(Calendar.MILLISECOND, 0)
        }
        alarmManager.set(AlarmManager.RTC, alarmHour.timeInMillis, pendingIntent)
      }

      val fragmentTransaction = supportFragmentManager.beginTransaction()
      var fragment: Fragment = homeFragment
      if (loadFragment != null) {
        when (loadFragment) {
          DIETS -> {
            fragment = dietsFragment
          }
        }
      }

      fragmentTransaction.replace(R.id.frameFragment, fragment).commit()

      bottomNavigationView.setOnNavigationItemSelectedListener {
        navigationItemSelectedListener(it)
      }
    }
  }

  private fun navigationItemSelectedListener(it: MenuItem): Boolean {
    val homeFragment = PatientMainFragment.newInstance(idPatient)
    val sugarFragment = PatientSugarRecordingFragment.newInstance()
    val dietFragment =
        PatientDietFragment.newInstance(idPatient, true)
    val notificationTest = NotificationTestFragment.newInstance()

    when {
      it.itemId == R.id.home_item -> {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, homeFragment).commit()
      }
      it.itemId == R.id.generateDietItem -> {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, dietFragment).commit()
      }
      it.itemId == R.id.registerSugarItem -> {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameFragment, sugarFragment).commit()
      }
    /*
    it.itemId == R.id.notification_test -> {
      val transaction = supportFragmentManager.beginTransaction()
      transaction.replace(R.id.frameFragment, notificationTest).commit()
    }
    */
      it.itemId == R.id.sign_out_item -> {
        logOut(null)
      }
    }

    return true
  }

  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount > 0) {
      supportFragmentManager.popBackStack()
    } else {
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
    idPatient = -1
  }

  override fun endSugarRegister(): Int {
    onBackPressed()
    return 0
  }

  companion object {
    var idPatient = -1
  }
}
