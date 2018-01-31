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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.EMAIL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_ROL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.KEYWORD;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE_COMPLETO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.OK;
import static escom.tt.ceres.ceresmobile.Vars.Strings.RESPUESTA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.URL_DATOS;
import static escom.tt.ceres.ceresmobile.Vars.Strings.URL_LOGIN;

public class FragmentoLogin extends Fragment {
  private OnLoginInteraction mListener;
  private String email, keyword;

  public FragmentoLogin() {
  }

  public static FragmentoLogin newInstance(String email, String keyword) {
    FragmentoLogin fragment = new FragmentoLogin();
    Bundle args = new Bundle();
    args.putString(EMAIL, email);
    args.putString(KEYWORD, keyword);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      email = getArguments().getString(EMAIL);
      keyword = getArguments().getString(KEYWORD);
      login(email, keyword);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_login, container, false);

    ((EditText) view.findViewById(R.id.editEmail)).setText("");
    ((EditText) view.findViewById(R.id.editKeyword)).setText("");
    Button btn = view.findViewById(R.id.btnEntrar);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        btnLoginPress();
      }
    });
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnLoginInteraction) {
      mListener = (OnLoginInteraction) activity;
    } else {
      throw new RuntimeException(ERROR);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  public void btnLoginPress() {
    Activity act = getActivity();
    String email = ((EditText) act.findViewById(R.id.editEmail)).getText().toString();
    String key = ((EditText) act.findViewById(R.id.editKeyword)).getText().toString();

    if (validate(R.id.editEmail, R.id.editKeyword))
      login(email, key);
  }

  public void login(String email, String key) {
    Activity act = getActivity();
    Context context = act.getApplicationContext();
    String urlDatos = URL_DATOS;
    String resultado, respuesta, mensaje, nombre, apellidoPaterno, apellidoMaterno, nombreCompleto;
    try {
      JSONObject dataS = new JSONObject();
      dataS.put(EMAIL, email);
      dataS.put(KEYWORD, key);

      resultado = new PostJSONReq().execute(URL_LOGIN, dataS.toString()).get();
      JSONObject jsonObject = new JSONObject(resultado);

      mensaje = jsonObject.has(MENSAJE) ? jsonObject.getString(MENSAJE) : ERROR;
      if (jsonObject.has(RESPUESTA)) {
        respuesta = jsonObject.getString(RESPUESTA);
        if (respuesta.equals(OK)) {
          int idUsuario = jsonObject.has(ID_USUARIO) ? jsonObject.getInt(ID_USUARIO) : -1;
          resultado = new GetReq().execute(urlDatos + String.valueOf(idUsuario)).get();

          SharedPreferences preferences = act.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = preferences.edit();
          JSONObject guardar = new JSONObject(resultado);
          int idRol = guardar.has(ID_ROL) ? guardar.getInt(ID_ROL) : -1;
          nombre = guardar.has(NOMBRE) ? guardar.getString(NOMBRE) : ERROR;
          apellidoPaterno = guardar.has(APELLIDO_PATERNO) ? guardar.getString(APELLIDO_PATERNO) : ERROR;
          apellidoMaterno = guardar.has(APELLIDO_MATERNO) ? guardar.getString(APELLIDO_MATERNO) : ERROR;
          nombreCompleto = nombre + " " + apellidoPaterno + " " + apellidoMaterno;

          editor.putInt(ID_USUARIO, idUsuario);
          editor.putString(NOMBRE_COMPLETO, nombreCompleto);
          editor.putString(NOMBRE, nombre);
          editor.putString(APELLIDO_PATERNO, apellidoPaterno);
          editor.putString(APELLIDO_MATERNO, apellidoMaterno);
          editor.putInt(ID_ROL, idRol);
          editor.apply();

          mListener.accesoExitoso(idUsuario, idRol);
        } else {
          Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
      }
    } catch (Exception e) {
      Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show();
    }
  }

  public boolean validate(int idEditEmail, int idEditKeyword) {
    EditText editEmail = getActivity().findViewById(idEditEmail);
    EditText editKey = getActivity().findViewById(idEditKeyword);
    String email = editEmail.getText().toString();
    String pass = editKey.getText().toString();
    Context ctx = getActivity().getApplicationContext();

    if (email.isEmpty()) {
      Toast.makeText(ctx, R.string.falta_email, Toast.LENGTH_SHORT).show();
      editEmail.requestFocus();
      return false;
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      Toast.makeText(ctx, R.string.formato_incorrecto, Toast.LENGTH_SHORT).show();
      editEmail.requestFocus();
      return false;
    }

    if (pass.isEmpty()) {
      Toast.makeText(ctx, R.string.falta_keyword, Toast.LENGTH_SHORT).show();
      editKey.requestFocus();
      return false;
    }

    return true;
  }

  public interface OnLoginInteraction {
    void accesoExitoso(int idUsuario, int rolUsuario);
  }
}
