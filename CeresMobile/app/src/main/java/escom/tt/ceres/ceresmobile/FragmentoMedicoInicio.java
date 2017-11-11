package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;

public class FragmentoMedicoInicio extends Fragment {
  private ComunicacionFMI mListener;

  public FragmentoMedicoInicio() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View main = inflater.inflate(R.layout.fragment_medico_inicio, container, false);
    SharedPreferences preferences = getActivity().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    String nombreUsuario = preferences.getString(NOMBRE, null);
    String apPaterno = preferences.getString(APELLIDO_PATERNO, null);
    String apMaterno = preferences.getString(APELLIDO_MATERNO, null);

    TextView textView = main.findViewById(R.id.textNombre);
    if (textView != null)
      textView.setText(nombreUsuario + ' ' + apPaterno + ' ' + apMaterno);

    return main;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ComunicacionFMI) {
      mListener = (ComunicacionFMI) activity;
    } else {
      throw new RuntimeException(getString(R.string.error));
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface ComunicacionFMI {
  }
}
