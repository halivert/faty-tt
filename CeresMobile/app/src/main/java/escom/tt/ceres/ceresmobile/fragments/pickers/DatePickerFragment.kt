package escom.tt.ceres.ceresmobile.fragments.pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import java.util.*

class DatePickerFragment : DialogFragment() {
  private var listener: DatePickerDialog.OnDateSetListener? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val c = Calendar.getInstance()
    year = if (year >= 0) year else c.get(Calendar.YEAR)
    month = if (month >= 0) month else c.get(Calendar.MONTH)
    day = if (day >= 0) day else c.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(activity, listener, year, month, day)
  }

  fun setListener(listener: DatePickerDialog.OnDateSetListener) {
    this.listener = listener
  }

  companion object {
    private var year = -1
    private var month = -1
    private var day = -1

    fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
      val fragment = DatePickerFragment()
      fragment.setListener(listener)
      return fragment
    }

    fun newInstance(listener: DatePickerDialog.OnDateSetListener, date: Calendar): DatePickerFragment {
      year = date.get(Calendar.YEAR)
      month = date.get(Calendar.MONTH)
      day = date.get(Calendar.DAY_OF_MONTH)

      val fragment = DatePickerFragment()
      fragment.setListener(listener)
      return fragment
    }
  }
}
