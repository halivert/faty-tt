package escom.tt.ceres.ceresmobile;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by hali on 27/02/18.
 */

public class TimePickerFragment extends DialogFragment {
  private static int hours = -1, minutes = -1;
  private TimePickerDialog.OnTimeSetListener listener;

  public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener) {
    TimePickerFragment fragment = new TimePickerFragment();
    fragment.setListener(listener);
    return fragment;
  }

  public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener, Calendar time) {
    hours = time.get(Calendar.HOUR_OF_DAY);
    minutes = time.get(Calendar.MINUTE);

    TimePickerFragment fragment = new TimePickerFragment();
    fragment.setListener(listener);
    return fragment;
  }

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    hours = hours >= 0 ? hours : c.get(Calendar.HOUR_OF_DAY);
    minutes = minutes >= 0 ? minutes : c.get(Calendar.MINUTE);

    return new TimePickerDialog(getActivity(), listener, hours, minutes, true);
  }

  public void setListener(TimePickerDialog.OnTimeSetListener listener) {
    this.listener = listener;
  }
}
