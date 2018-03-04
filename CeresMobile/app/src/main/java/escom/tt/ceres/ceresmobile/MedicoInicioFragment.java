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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static escom.tt.ceres.ceresmobile.Functions.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Functions.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Functions.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Functions.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Functions.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Functions.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Functions.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Functions.Strings.RESPUESTA;
import static escom.tt.ceres.ceresmobile.Functions.Strings.TOKEN;

public class MedicoInicioFragment extends Fragment {
  RequestQueue requestQueue;
  private ComunicacionFMI mListener;

  public MedicoInicioFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_medico_inicio, container, false);
    SharedPreferences preferences = getActivity().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    String nombreUsuario = preferences.getString(NOMBRE, null);
    String apPaterno = preferences.getString(APELLIDO_PATERNO, null);
    String apMaterno = preferences.getString(APELLIDO_MATERNO, null);

    ImageView imageView = view.findViewById(R.id.ivFruits);
    imageView.setImageResource(R.drawable.fruits);

    TextView textView = view.findViewById(R.id.textNombre);
    if (textView != null)
      textView.setText(nombreUsuario + ' ' + apPaterno + ' ' + apMaterno);

    Button btnCrearToken = view.findViewById(R.id.btnNuevoToken);
    btnCrearToken.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        crearToken();
      }
    });
    requestQueue = Volley.newRequestQueue(getActivity());

    return view;
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
    final Activity act = getActivity();
    final Context context = act.getApplicationContext();
    SharedPreferences preferences = act.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    int idMedico = preferences.getInt(ID_USUARIO, -1);
    // TODO: Agregar url de token
    String urlToken = "http://";
    // String urlToken = FuncionesPrincipales.urlTokenMedico(idMedico);
    final ProgressBar progressBar = act.findViewById(R.id.pbHeaderProgress);
    progressBar.setProgress(0);
    progressBar.setVisibility(View.VISIBLE);

    JsonObjectRequest request = new JsonObjectRequest(urlToken, null,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                try {
                  String token = "Error";
                  if (response.has(MENSAJE) && response.has(RESPUESTA)) {
                    token = (response.getString(RESPUESTA).equals(TOKEN) ? response.getString(MENSAJE) : ERROR);
                  }
                  TextView textView = act.findViewById(R.id.textTokenMedico);
                  textView.setText(token);
                } catch (Exception e) {
                  Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
              }
            }
    );

    requestQueue.add(request);

    // GetReq req = new GetReq();
    // req.setProgressBar(pbHeaderProgress);
    // String resultado = req.execute(urlToken).get();
    // JSONObject respuesta = new JSONObject(resultado);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface ComunicacionFMI {
  }
}
