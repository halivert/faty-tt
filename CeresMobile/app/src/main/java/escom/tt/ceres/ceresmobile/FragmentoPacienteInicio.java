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

import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE_COMPLETO;


public class FragmentoPacienteInicio extends Fragment {
  private static final String ARG1 = "a1";
  private ComunicacionFPacienteI mListener;
  private int idUsuario;

  public FragmentoPacienteInicio() {
  }

  public static FragmentoPacienteInicio newInstance(int idUsuario) {
    FragmentoPacienteInicio fragment = new FragmentoPacienteInicio();
    Bundle args = new Bundle();
    args.putInt(ARG1, idUsuario);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      idUsuario = getArguments().getInt(ARG1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View main = inflater.inflate(R.layout.fragment_paciente_inicio, container, false);

    SharedPreferences preferences = getActivity().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    String nombreUsuario = preferences.getString(NOMBRE_COMPLETO, null);

    TextView textView = main.findViewById(R.id.textNombre);
    if (textView != null)
      textView.setText(nombreUsuario);

    return main;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ComunicacionFPacienteI) {
      mListener = (ComunicacionFPacienteI) activity;
    } else {
      throw new RuntimeException(ERROR);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface ComunicacionFPacienteI {
  }
}
