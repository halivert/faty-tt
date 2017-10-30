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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Iterator;

import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.EMAIL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_CONEXION;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_INDIVIDUO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.KEYWORD;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ROL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ROL_INDIVIDUO;

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
    /*
    ProgressBar progressBar = act.findViewById(R.id.progressBar);
    progressBar.setVisibility(View.VISIBLE);
    */
    String email = ((EditText) act.findViewById(R.id.editEmail)).getText().toString();
    String key = ((EditText) act.findViewById(R.id.editKeyword)).getText().toString();

    if (validate(R.id.editEmail, R.id.editKeyword))
      login(email, key);
  }

  public void login(String email, String key) {
    Activity act = getActivity();
    Context context = act.getApplicationContext();
    String url = "http://35.188.191.232/tt-escom-diabetes/sesion/login/";
    String urlDatos = "http://35.188.191.232/tt-escom-diabetes/sesion/individuos/?idIndividuo=";
    try {
      JSONObject dataS = new JSONObject();
      dataS.put(EMAIL, email);
      dataS.put(KEYWORD, key);

      String resultado = new PostJSONReq().execute(url, dataS.toString()).get();
      JSONObject jsonObject = new JSONObject(resultado);

      if (jsonObject.has(CODIGO_ERROR)) {
        Toast.makeText(context, jsonObject.getString(ERROR), Toast.LENGTH_SHORT).show();
      } else if (jsonObject.has(ID_INDIVIDUO)) {
        int idUsuario = jsonObject.getInt(ID_INDIVIDUO);
        String sIdUsuario = String.valueOf(idUsuario);
        int rolUsuario = jsonObject.getInt(ROL_INDIVIDUO);
        String sRolUsuario = String.valueOf(rolUsuario);
/*
        Log.e(ERROR, urlDatos + sIdUsuario + "&idRol=" + sRolUsuario);
*/
        resultado = new GetReq().execute(urlDatos + sIdUsuario + "&idRol=" + sRolUsuario).get();

        SharedPreferences preferences = act.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        JSONObject guardar = new JSONObject(resultado);
        guardar.put(ROL, rolUsuario);
        guardarDatosUsuario(preferences, guardar);

        mListener.accesoExitoso(idUsuario, rolUsuario);
      } else {
        Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show();
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

  public void guardarDatosUsuario(SharedPreferences preferences, JSONObject jsonObject)
          throws Exception {
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();

    Iterator<String> iter = jsonObject.keys();
    while (iter.hasNext()) {
      String key = iter.next();
      Object value = jsonObject.get(key);
      try {
        editor.putInt(key, Integer.parseInt(value.toString()));
      } catch (Exception e) {
        editor.putString(key, value.toString());
      }
    }

    editor.apply();
  }

  public interface OnLoginInteraction {
    void accesoExitoso(int idUsuario, int rolUsuario);
  }
}
