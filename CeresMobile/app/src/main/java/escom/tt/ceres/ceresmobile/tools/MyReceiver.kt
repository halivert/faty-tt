package escom.tt.ceres.ceresmobile.tools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import escom.tt.ceres.ceresmobile.R

class MyReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    try {
      var tick: String = intent.getStringExtra("TICK")
      var title: String = intent.getStringExtra("TITLE")
      var text: String = intent.getStringExtra("TEXT")
      var idRes: Int = intent.getIntExtra("ID_IMAGE", R.drawable.ic_utensils_white)
      Utils.generateNotification(context, title, text, tick, idRes)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}