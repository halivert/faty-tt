package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.android.volley.Request.Method.POST;
import static escom.tt.ceres.ceresmobile.Vars.Strings.AZUCAR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.FECHA_REGISTRO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.OK;
import static escom.tt.ceres.ceresmobile.Vars.Strings.RESPUESTA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.URL_PACIENTE;

public class RegistrarGlucosaFragment extends Fragment {
  private String urlRegistroGlucosa = URL_PACIENTE + "/";
  private OnRegistrarGlucosaListener mListener;
  private RequestQueue requestQueue;

  public RegistrarGlucosaFragment() {
  }

  public static RegistrarGlucosaFragment newInstance() {
    RegistrarGlucosaFragment fragment = new RegistrarGlucosaFragment();
    /*
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    */
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /*
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
    */
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_registrar_glucosa, container, false);

    requestQueue = Volley.newRequestQueue(view.getContext());
    SharedPreferences preferences = getActivity().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    int idPaciente = preferences.getInt(ID_USUARIO, -1);
    if (idPaciente >= 0) {
      urlRegistroGlucosa += String.valueOf(idPaciente) + "/registroglucosa";
    } else {
      urlRegistroGlucosa = "";
    }

    Button guardar = view.findViewById(R.id.btnGuardarAzucar);
    guardar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Activity activity = getActivity();
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
          inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
        guardarAzucar();
      }
    });

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = dateFormat.format(Calendar.getInstance().getTime());
    dateFormat = new SimpleDateFormat("HH:mm");
    String currentTime = dateFormat.format(Calendar.getInstance().getTime());

    final EditText fechaAzucar = view.findViewById(R.id.etFechaAzucar);
    final EditText horaAzucar = view.findViewById(R.id.etHoraAzucar);
    fechaAzucar.setText(currentDate);
    horaAzucar.setText(currentTime);
    fechaAzucar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Vars.showDatePicker(getActivity(), fechaAzucar);
      }
    });
    horaAzucar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Vars.showTimePicker(getActivity(), horaAzucar);
      }
    });

    return view;
  }

  private void guardarAzucar() {
    final Activity activity = getActivity();
    final Context context = activity.getApplicationContext();
    final Toast toast = new Toast(context);

    HashMap<String, String> parametros = new HashMap<>();
    EditText azucar = getActivity().findViewById(R.id.etAzucar);
    EditText fecha = getActivity().findViewById(R.id.etFechaAzucar);
    EditText hora = getActivity().findViewById(R.id.etHoraAzucar);

    if (azucar.getText().toString().isEmpty()) {
      toast.makeText(context, "Falta ázucar", Toast.LENGTH_LONG).show();
      return;
    }
    if (fecha.getText().toString().isEmpty()) {
      toast.makeText(context, "Falta fecha", Toast.LENGTH_LONG).show();
      return;
    }
    if (hora.getText().toString().isEmpty()) {
      toast.makeText(context, "Falta hora", Toast.LENGTH_LONG).show();
      return;
    }

    parametros.put(AZUCAR, azucar.getText().toString());
    // parametros.put(FECHA_REGISTRO, fecha + " " + hora + ":00.0");
    parametros.put(FECHA_REGISTRO, fecha.getText().toString() + " " + hora.getText().toString());
    JSONObject jsonObject = new JSONObject(parametros);

    JsonObjectRequest request = new JsonObjectRequest(POST, urlRegistroGlucosa, jsonObject,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                try {
                  if (response.has(RESPUESTA)) {
                    String respuesta = response.getString(RESPUESTA);
                    if (respuesta.equals(OK) && response.has(MENSAJE)) {
                      toast.makeText(context, response.getString(MENSAJE), Toast.LENGTH_LONG).show();
                    }
                  }

                  mListener.finRegistroGlucosa();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
              }
            }
    );

    requestQueue.add(request);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnRegistrarGlucosaListener) {
      mListener = (OnRegistrarGlucosaListener) activity;
    } else {
      throw new RuntimeException(activity.toString()
              + " must implement OnRegistrarGlucosaListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnRegistrarGlucosaListener {
    int finRegistroGlucosa();
  }
}
