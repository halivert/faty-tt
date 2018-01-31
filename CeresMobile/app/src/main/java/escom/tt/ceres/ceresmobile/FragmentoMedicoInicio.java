package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_IO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.RESPUESTA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.TOKEN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.VOID;

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

    ImageView imageView = main.findViewById(R.id.imageView);
    imageView.setImageResource(R.drawable.fruits);

    TextView textView = main.findViewById(R.id.textNombre);
    if (textView != null)
      textView.setText(nombreUsuario + ' ' + apPaterno + ' ' + apMaterno);

    Button btnCrearToken = main.findViewById(R.id.btnNuevoToken);
    btnCrearToken.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        crearToken();
      }
    });

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

  public void crearToken() {
    Activity act = getActivity();
    Context context = act.getApplicationContext();
    SharedPreferences preferences = act.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    int idMedico = preferences.getInt(ID_USUARIO, -1);
    String urlToken = FuncionesPrincipales.urlTokenMedico(idMedico), token = VOID;

    try {
      String resultado = new GetReq().execute(urlToken).get();
      JSONObject respuesta = new JSONObject(resultado);

      if (respuesta.has(MENSAJE) && respuesta.has(RESPUESTA)) {
        token = (respuesta.getString(RESPUESTA).equals(TOKEN) ? respuesta.getString(MENSAJE) : ERROR);
      }
      TextView textView = act.findViewById(R.id.textTokenMedico);
      textView.setText(token);
    } catch (Exception e) {
      Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show();
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
