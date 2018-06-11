package escom.tt.ceres.ceresmobile.tools

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import escom.tt.ceres.ceresmobile.activities.MainActivity

class Utils {
  companion object {
    var mManager: NotificationManager? = null

    fun generateNotification(context: Context, title: String, text: String, ticker: String, idIcon: Int) {
      val nb = NotificationCompat.Builder(context).apply {
        setSmallIcon(idIcon)
        setContentTitle(title)
        setContentText(text)
        setTicker(ticker)
        setAutoCancel(true)
      }

      val resultIntent = Intent(context, MainActivity::class.java)
      val stackBuilder = TaskStackBuilder.create(context)
      stackBuilder.addParentStack(MainActivity::class.java)
      stackBuilder.addNextIntent(resultIntent)
      val resultPendingIntent = stackBuilder.getPendingIntent(
          0,
          PendingIntent.FLAG_ONE_SHOT
      )

      nb.setContentIntent(resultPendingIntent)
      nb.setAutoCancel(true)
      context.getSystemService(Context.NOTIFICATION_SERVICE)

      val notificationManager =
          context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

      notificationManager.notify(11221, nb.build())
    }
  }
}