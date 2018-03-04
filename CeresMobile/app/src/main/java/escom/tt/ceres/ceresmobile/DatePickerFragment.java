package escom.tt.ceres.ceresmobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by hali on 30/10/17.
 */

public class DatePickerFragment extends DialogFragment {
  private static int year = -1, month = -1, day = -1;
  private DatePickerDialog.OnDateSetListener listener;

  public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setListener(listener);
    return fragment;
  }

  public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, Calendar date) {
    year = date.get(Calendar.YEAR);
    month = date.get(Calendar.MONTH);
    day = date.get(Calendar.DAY_OF_MONTH);

    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setListener(listener);
    return fragment;
  }

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    year = year >= 0 ? year : c.get(Calendar.YEAR);
    month = month >= 0 ? month : c.get(Calendar.MONTH);
    day = day >= 0 ? day : c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), listener, year, month, day);
  }

  public void setListener(DatePickerDialog.OnDateSetListener listener) {
    this.listener = listener;
  }
}
