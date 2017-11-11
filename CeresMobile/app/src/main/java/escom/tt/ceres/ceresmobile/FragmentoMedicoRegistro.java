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

import static escom.tt.ceres.ceresmobile.Vars.Ints.MEDICO;
import static escom.tt.ceres.ceresmobile.Vars.Ints.PACIENTE;
import static escom.tt.ceres.ceresmobile.Vars.Ints.SEXO_FEMENINO;
import static escom.tt.ceres.ceresmobile.Vars.Ints.SEXO_MASCULINO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_MATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.APELLIDO_PATERNO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.CEDULA_PROFESIONAL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_MEDICO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.EMAIL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_CONEXION;
import static escom.tt.ceres.ceresmobile.Vars.Strings.FECHA_NACIMIENTO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_ROL;
import static escom.tt.ceres.ceresmobile.Vars.Strings.INFO_GUARDADA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.KEYWORD;
import static escom.tt.ceres.ceresmobile.Vars.Strings.MENSAJE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.NOMBRE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.OK;
import static escom.tt.ceres.ceresmobile.Vars.Strings.RESPUESTA;
import static escom.tt.ceres.ceresmobile.Vars.Strings.SEXO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.URL_REGISTRO;


public class FragmentoMedicoRegistro extends Fragment {
  private CFMedicoRegistro mListener;

  public FragmentoMedicoRegistro() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View main = inflater.inflate(R.layout.fragmento_medico_registro, container, false);

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

  private boolean validar() {
    Activity ac = getActivity();
    String nombre, apPaterno, apMaterno, cedulaProf, email, keyword, keywordRep, fechaNac;
    nombre = ((EditText) ac.findViewById(R.id.editNombre)).getText().toString();
    apPaterno = ((EditText) ac.findViewById(R.id.editApPat)).getText().toString();
    apMaterno = ((EditText) ac.findViewById(R.id.editApMat)).getText().toString();
    cedulaProf = ((EditText) ac.findViewById(R.id.editCedulaProf)).getText().toString();
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

    if (cedulaProf.isEmpty())
      return verifEditText(R.string.falta_cedula_prof, R.id.editCedulaProf);

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
      try {
        String nombre, apPaterno, apMaterno, cedulaProf, email, keyword, fechaNac, sexo;
        nombre = ((EditText) ac.findViewById(R.id.editNombre)).getText().toString();
        apPaterno = ((EditText) ac.findViewById(R.id.editApPat)).getText().toString();
        apMaterno = ((EditText) ac.findViewById(R.id.editApMat)).getText().toString();
        cedulaProf = ((EditText) ac.findViewById(R.id.editCedulaProf)).getText().toString();
        email = ((EditText) ac.findViewById(R.id.editEmail)).getText().toString();
        keyword = ((EditText) ac.findViewById(R.id.editKeyword)).getText().toString();
        fechaNac = ((EditText) ac.findViewById(R.id.dtpFechaNac)).getText().toString();

        if (((RadioButton) ac.findViewById(R.id.radioSexoHombre)).isChecked()) {
          sexo = String.valueOf(SEXO_MASCULINO);
        } else sexo = String.valueOf(SEXO_FEMENINO);

        JSONObject request = new JSONObject();
        request.put(NOMBRE, nombre);
        request.put(APELLIDO_PATERNO, apPaterno);
        request.put(APELLIDO_MATERNO, apMaterno);
        request.put(EMAIL, email);
        request.put(KEYWORD, keyword);
        request.put(FECHA_NACIMIENTO, fechaNac);
        request.put(SEXO, sexo);
        request.put(ID_ROL, MEDICO);
        request.put(CEDULA_PROFESIONAL, cedulaProf);
        request.put(CODIGO_MEDICO, null);

        String resultado = new PostJSONReq().execute(URL_REGISTRO, request.toString()).get();
        JSONObject jsonObject = new JSONObject(resultado);
        String mensaje = ERROR;

        if (jsonObject.has(RESPUESTA)) {
          String respuesta = jsonObject.getString(RESPUESTA);
          if (jsonObject.has(MENSAJE)) {
            mensaje = jsonObject.getString(MENSAJE);
          }
          if (respuesta.equals(ERROR)) {
            Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show();
          } else if (respuesta.equals(OK)) {
            Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show();
            mListener.registroExitoso(email, keyword);
          }
        }
        else {
          Toast.makeText(ctx, resultado, Toast.LENGTH_SHORT).show();
        }
      } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(ctx, ERROR, Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof CFMedicoRegistro) {
      mListener = (CFMedicoRegistro) activity;
    } else {
      throw new RuntimeException(ERROR);
    }
  }

  public void showDatePicker() {
    DatePickerFragment datePickerFragment;
    Activity ac = getActivity();
    final EditText fechaNac = ac.findViewById(R.id.dtpFechaNac);
    datePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
        final String fecha = dia + "/" + (mes + 1) + "/" + year;
        fechaNac.setText(fecha);
      }
    });

    datePickerFragment.show(ac.getFragmentManager(), "datePicker");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface CFMedicoRegistro {
    void registroExitoso(String email, String keyword);
  }
}