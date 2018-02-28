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

  private DatePickerDialog.OnDateSetListener listener;

  public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setListener(listener);
    return fragment;
  }

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), listener, year, month, day);
  }

  public void setListener(DatePickerDialog.OnDateSetListener listener) {
    this.listener = listener;
  }
}
