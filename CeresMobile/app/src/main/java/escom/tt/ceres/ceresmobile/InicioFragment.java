package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static escom.tt.ceres.ceresmobile.Functions.Ints.MEDICO;
import static escom.tt.ceres.ceresmobile.Functions.Ints.NULL;
import static escom.tt.ceres.ceresmobile.Functions.Ints.PACIENTE;
import static escom.tt.ceres.ceresmobile.Functions.Strings.ERROR;

public class InicioFragment extends Fragment {
  private EnSeleccionUsuario mListener;

  public InicioFragment() {
  }

  public static InicioFragment newInstance(int arg1) {
    InicioFragment fragment = new InicioFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_inicio, container, false);

    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        irAPrincipal(view);
      }
    };

    TextView textView = view.findViewById(R.id.soyPaciente);
    textView.setOnClickListener(onClickListener);
    textView = view.findViewById(R.id.soyMedico);
    textView.setOnClickListener(onClickListener);

    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof EnSeleccionUsuario) {
      mListener = (EnSeleccionUsuario) activity;
    } else {
      throw new RuntimeException(ERROR);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public void irAPrincipal(View view) {
    int usuario = NULL;
    if (view.getTag().toString().equals(getString(R.string.soy_paciente)))
      usuario = PACIENTE;
    else if (view.getTag().toString().equals(getString(R.string.soy_medico)))
      usuario = MEDICO;

    boolean bUsuario = usuario == MEDICO || usuario == PACIENTE;
    if (mListener != null && bUsuario) {
      mListener.seleccionUsuario(usuario);
    }
  }

  public interface EnSeleccionUsuario {
    void seleccionUsuario(int usuario);
  }
}
