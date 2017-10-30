package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;

import static escom.tt.ceres.ceresmobile.Vars.Ints.SEXO_HOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Ints.SEXO_MUJER;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.EMAIL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_CONEXION;
import static escom.tt.ceres.ceresmobile.Vars.Strings.FECHA_NACIMIENTO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_SEXO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.INFO_GUARDADA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.KEYWORD;
import static escom.tt.ceres.ceresmobile.Vars.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ROL;

public class FragmentoPacienteRegistro extends Fragment {
  private CFPacienteRegistro mListener;

  public FragmentoPacienteRegistro() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View main = inflater.inflate(R.layout.fragmento_paciente_registro, container, false);

    main.findViewById(R.id.btnRegistro).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        registrarse();
      }
    });

    main.findViewById(R.id.dtpFechaNac).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showDatePicker();
      }
    });

    return main;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof CFPacienteRegistro) {
      mListener = (CFPacienteRegistro) activity;
    } else {
      throw new RuntimeException(ERROR);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  private boolean verifEditText(int stringId, int idElem) {
    Activity ac = getActivity();
    Context ctx = ac.getApplicationContext();
    EditText editText = ac.findViewById(idElem);
    Toast.makeText(ctx, stringId, Toast.LENGTH_SHORT).show();
    editText.requestFocus();
    return false;
  }

  private boolean verifRadioGroup(int stringId, int idElem) {
    Activity ac = getActivity();
    Context ctx = ac.getApplicationContext();
    RadioGroup radioGroup = ac.findViewById(idElem);
    Toast.makeText(ctx, stringId, Toast.LENGTH_SHORT).show();
    radioGroup.requestFocus();
    return false;
  }

  public void showDatePicker() {
    DatePickerFragment datePickerFragment;
    Activity ac = getActivity();
    final EditText fechaNac = ac.findViewById(R.id.dtpFechaNac);
    datePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int dia, int mes, int year) {
        final String fecha = dia + "/" + (mes + 1) + "/" + year;
        fechaNac.setText(fecha);
      }
    });

    datePickerFragment.show(ac.getFragmentManager(), "datePicker");
  }

  private boolean validar() {
    Activity ac = getActivity();
    String nombre, apPaterno, apMaterno, codigoMed, email, keyword, keywordRep, fechaNac;
    nombre = ((EditText) ac.findViewById(R.id.editNombre)).getText().toString();
    apPaterno = ((EditText) ac.findViewById(R.id.editApPat)).getText().toString();
    apMaterno = ((EditText) ac.findViewById(R.id.editApMat)).getText().toString();
    codigoMed = ((EditText) ac.findViewById(R.id.editTokenMed)).getText().toString();
    email = ((EditText) ac.findViewById(R.id.editEmail)).getText().toString();
    keyword = ((EditText) ac.findViewById(R.id.editKeyword)).getText().toString();
    keywordRep = ((EditText) ac.findViewById(R.id.editKeywordConf)).getText().toString();
    fechaNac = ((EditText) ac.findViewById(R.id.dtpFechaNac)).getText().toString();

    if (nombre.isEmpty())
      return verifEditText(R.string.falta_nombre, R.id.editNombre);

    if (apPaterno.isEmpty())
      return verifEditText(R.string.falta_ap_paterno, R.id.editApPat);

    if (apMaterno.isEmpty())
      return verifEditText(R.string.falta_ap_materno, R.id.editApMat);

    if (codigoMed.isEmpty())
      return verifEditText(R.string.falta_codigo_medico, R.id.editTokenMed);

    if (email.isEmpty())
      return verifEditText(R.string.falta_email, R.id.editEmail);
    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
      return verifEditText(R.string.formato_incorrecto, R.id.editEmail);

    if (keyword.isEmpty())
      return verifEditText(R.string.falta_keyword, R.id.editKeyword);
    else if (keywordRep.isEmpty())
      return verifEditText(R.string.falta_confirmacion, R.id.editKeywordConf);
    else if (!keyword.equals(keywordRep))
      return verifEditText(R.string.keyword_no_coincide, R.id.editKeyword);

    if (!((RadioButton) ac.findViewById(R.id.radioSexoHombre)).isChecked()) {
      if (!((RadioButton) ac.findViewById(R.id.radioSexoMujer)).isChecked()) {
        return verifRadioGroup(R.string.falta_sexo, R.id.radioGrupo);
      }
    }

    if (fechaNac.isEmpty()) {
      return verifEditText(R.string.falta_fecha_nac, R.id.dtpFechaNac);
    }

    return true;
  }

  public void registrarse() {
    if (validar()) {
      Activity ac = getActivity();
      Context ctx = ac.getApplicationContext();
      String url = "http://35.188.191.232/tt-escom-diabetes/sesion/individuos/";
      try {
        String nombre, apPaterno, apMaterno, codigoMed, email, keyword, fechaNac, idSexo;
        nombre = ((EditText) ac.findViewById(R.id.editNombre)).getText().toString();
        apPaterno = ((EditText) ac.findViewById(R.id.editApPat)).getText().toString();
        apMaterno = ((EditText) ac.findViewById(R.id.editApMat)).getText().toString();
        // codigoMed = ((EditText) ac.findViewById(R.id.editTokenMed)).getText().toString();
        email = ((EditText) ac.findViewById(R.id.editEmail)).getText().toString();
        keyword = ((EditText) ac.findViewById(R.id.editKeyword)).getText().toString();
        fechaNac = ((EditText) ac.findViewById(R.id.dtpFechaNac)).getText().toString();

        if (((RadioButton) ac.findViewById(R.id.radioSexoHombre)).isChecked()) {
          idSexo = String.valueOf(SEXO_HOMBRE);
        } else idSexo = String.valueOf(SEXO_MUJER);


        StringBuilder data = new StringBuilder();
        data.append(URLEncoder.encode(NOMBRE, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(nombre, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(APELLIDO_PATERNO, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(apPaterno, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(APELLIDO_MATERNO, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(apMaterno, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(EMAIL, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(email, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(KEYWORD, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(keyword, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(ROL, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(String.valueOf(Vars.Ints.PACIENTE), "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(FECHA_NACIMIENTO, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(fechaNac, "UTF-8"));
        data.append("&");
        data.append(URLEncoder.encode(ID_SEXO, "UTF-8"));
        data.append("=");
        data.append(URLEncoder.encode(idSexo, "UTF-8"));

        String resultado = new PostReq().execute(url + '?' + data.toString(), data.toString()).get();
        JSONObject jsonObject = new JSONObject(resultado);

        if (jsonObject.has(CODIGO_ERROR)) {
          switch (jsonObject.getInt(CODIGO_ERROR)) {
            case 200:
              Toast.makeText(ctx, ERROR_CONEXION, Toast.LENGTH_SHORT).show();
              break;
            case 100:
              Toast.makeText(ctx, R.string.email_ya_registrado, Toast.LENGTH_SHORT).show();
              break;
            default:
              Toast.makeText(ctx, ERROR, Toast.LENGTH_SHORT).show();
          }
        } else if (jsonObject.has(MENSAJE)) {
          String mensaje = jsonObject.getString(MENSAJE);

          if (mensaje.equals(INFO_GUARDADA)) {
            Toast.makeText(ctx, INFO_GUARDADA, Toast.LENGTH_SHORT).show();

            mListener.registroExitoso(email, keyword);
          }
        } else {
          Toast.makeText(ctx, resultado, Toast.LENGTH_SHORT).show();
        }
      } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(ctx, ERROR, Toast.LENGTH_SHORT).show();
      }
    }
  }

  public interface CFPacienteRegistro {
    void registroExitoso(String email, String keyword);
  }
}
