package escom.tt.ceres.ceresmobile.tools

import android.app.Activity
import android.app.PendingIntent
import android.support.v4.app.NotificationCompat
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.CHANNEL_ID

object HNotification {
  fun createNotification(
      activity: Activity,
      title: String,
      smallText: String): NotificationCompat.Builder {

    return createNotification(
        activity,
        title,
        smallText,
        null,
        null,
        null
    )
  }

  fun createNotification(
      activity: Activity,
      title: String,
      smallText: String,
      bigText: String): NotificationCompat.Builder {

    return createNotification(
        activity,
        title,
        smallText,
        bigText,
        null,
        null
    )
  }


  fun createNotification(
      activity: Activity,
      title: String,
      smallText: String,
      bigText: String?,
      idIcon: Int?,
      priority: Int?): NotificationCompat.Builder {
    return createNotification(
        activity,
        title,
        smallText,
        bigText,
        idIcon,
        priority,
        null)
  }

  fun createNotification(
      activity: Activity,
      title: String,
      smallText: String,
      bigText: String?,
      idIcon: Int?,
      priority: Int?,
      pendingIntent: PendingIntent?): NotificationCompat.Builder {

    return NotificationCompat.Builder(activity, CHANNEL_ID).apply {
      setSmallIcon(idIcon ?: R.drawable.ic_utensils_white)
      setContentTitle(title)
      setContentText(smallText)

      if (bigText != null) {
        setStyle(NotificationCompat.BigTextStyle()
            .bigText(bigText))
      }

      setPriority(priority ?: NotificationCompat.PRIORITY_DEFAULT)

      if (pendingIntent != null) {
        setContentIntent(pendingIntent)
      }

      setAutoCancel(true)
    }
  }
}