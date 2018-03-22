package escom.tt.ceres.ceresmobile.fragments.pickers

import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.os.Bundle
import java.util.*

/**
 * Created by hali on 27/02/18.
 */

class TimePickerFragment : DialogFragment() {
  private var listener: TimePickerDialog.OnTimeSetListener? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val c = Calendar.getInstance()
    hours = if (hours >= 0) hours else c.get(Calendar.HOUR_OF_DAY)
    minutes = if (minutes >= 0) minutes else c.get(Calendar.MINUTE)

    return TimePickerDialog(activity, listener, hours, minutes, true)
  }

  fun setListener(listener: TimePickerDialog.OnTimeSetListener) {
    this.listener = listener
  }

  companion object {
    private var hours = -1
    private var minutes = -1

    fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {
      val fragment = TimePickerFragment()
      fragment.setListener(listener)
      return fragment
    }

    fun newInstance(listener: TimePickerDialog.OnTimeSetListener, time: Calendar):
        TimePickerFragment {
      hours = time.get(Calendar.HOUR_OF_DAY)
      minutes = time.get(Calendar.MINUTE)

      val fragment = TimePickerFragment()
      fragment.setListener(listener)
      return fragment
    }
  }
}
