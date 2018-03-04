package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DietaFragment extends Fragment {
  private OnDietaListener mListener;

  public DietaFragment() {
  }

  public static DietaFragment newInstance() {
    DietaFragment fragment = new DietaFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dieta, container, false);
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnDietaListener) {
      mListener = (OnDietaListener) activity;
    } else {
      throw new RuntimeException(activity.toString()
              + " must implement OnDietaListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnDietaListener {
  }
}
