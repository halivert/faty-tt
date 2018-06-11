package escom.tt.ceres.ceresmobile.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationManagerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.PatientMainActivity
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DIETS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FRAGMENT
import escom.tt.ceres.ceresmobile.tools.HNotification

// private const val ARG_PARAM1 = "param1"

class NotificationTestFragment : Fragment() {
  // private var param1: String? = null
  private var listener: OnNotificationTestInteraction? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      // param1 = it.getString(ARG_PARAM1)
    }
    createNotificationChannel()
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = getString(R.string.channel_name)
      val description = getString(R.string.channel_description)
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID, name, importance)
      channel.description = description

      val notificationManager = activity.getSystemService(NotificationManager::class.java)
      notificationManager.createNotificationChannel(channel)
    }
  }

  private fun showNotification(notificationId: Int) {
    val intent = Intent(activity, PatientMainActivity::class.java)
    intent.putExtra(FRAGMENT, DIETS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)

    val mBuilder = HNotification.createNotification(
        activity,
        "Titulo horrible",
        "Texto cortito...",
        null,
        null,
        null,
        pendingIntent
    )

    val manager = NotificationManagerCompat.from(activity)
    manager.notify(notificationId, mBuilder.build())
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_notification_test, container, false)

    view.findViewById<Button>(R.id.btn_notification).setOnClickListener {
      showNotification(notificationId++)
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnNotificationTestInteraction) {
      listener = context
    } else {
      throw RuntimeException(
          context.toString() + " must implement OnNotificationTestInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface OnNotificationTestInteraction

  companion object {
    var notificationId = 0
    val CHANNEL_ID = "CERES_MOBILE"

    @JvmStatic
    fun newInstance() =
        NotificationTestFragment().apply {
          arguments = Bundle().apply {
            // putString(ARG_PARAM1, param1)
          }
        }
  }
}
