package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hali on 27/10/17.
 */

public class Functions {
  public static String qString(String s) {
    return "\"" + s + "\"";
  }

  @NonNull
  public static String urlTokenMedico(int idMedico) {
    return "http://35.188.191.232/tt-escom-diabetes/ceres/medico/" + String.valueOf(idMedico) + "/token/";
  }

  public static void showDatePicker(Activity activity, final EditText dtp, final String separator) {
    DatePickerFragment datePickerFragment;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy");
    if (!dtp.getText().toString().isEmpty()) {
      try {
        calendar.setTime(sdf.parse(dtp.getText().toString()));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    datePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
        // final String fecha = year + separator + (mes + 1) + separator + dia;
        final String fecha = dia + separator + (mes + 1) + separator + year;
        dtp.setText(fecha);
      }
    }, calendar);

    datePickerFragment.show(activity.getFragmentManager(), "datePicker");
  }

  public static void showDatePicker(Activity activity, final EditText dtp) {
    showDatePicker(activity, dtp, "/");
  }

  public static void showTimePicker(Activity activity, final EditText dtp) {
    TimePickerFragment timePickerFragment;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    if (!dtp.getText().toString().isEmpty()) {
      try {
        calendar.setTime(sdf.parse(dtp.getText().toString()));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    timePickerFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker timePicker, int i, int i1) {
        final String hora = i + ":" + i1;
        dtp.setText(hora);
      }
    }, calendar);

    timePickerFragment.show(activity.getFragmentManager(), "datePicker");
  }

  public static void selectElement(Activity activity, TextView text, ImageView originalImage, int resourceId) {
    ConstraintLayout.LayoutParams textViewMargin = (ConstraintLayout.LayoutParams) text.getLayoutParams();
    ConstraintLayout.LayoutParams imageMargin = (ConstraintLayout.LayoutParams) originalImage.getLayoutParams();

    int color = 0xFF000000;
    int marginSide = dpsToPixel(activity, 12);
    int marginBottom = dpsToPixel(activity, 10);
    int marginTop = dpsToPixel(activity, 6);

    textViewMargin.setMargins(marginSide, 0, marginSide, marginBottom);
    imageMargin.setMargins(0, marginTop, 0, 0);

    text.setLayoutParams(textViewMargin);
    originalImage.setLayoutParams(imageMargin);

    text.setTextColor(color);
    originalImage.setImageResource(resourceId);
  }

  public static void unselectElements(Activity activity, int[] texts, int[] imageViews, int[] resourceIds) {
    if (texts.length != imageViews.length || texts.length != resourceIds.length) {
      return;
    }

    ConstraintLayout.LayoutParams textViewMargin, imageMargin;
    final int color = 0xFF0D5748;
    final int marginSide = dpsToPixel(activity, 12);
    final int marginBottom = dpsToPixel(activity, 8);
    final int marginTop = dpsToPixel(activity, 8);

    int elements = texts.length;
    for (int i = 0; i < elements; ++i) {
      TextView text = activity.findViewById(texts[i]);
      ImageView originalImage = activity.findViewById(imageViews[i]);

      textViewMargin = (ConstraintLayout.LayoutParams) text.getLayoutParams();
      imageMargin = (ConstraintLayout.LayoutParams) originalImage.getLayoutParams();

      textViewMargin.setMargins(marginSide, 0, marginSide, marginBottom);
      imageMargin.setMargins(0, marginTop, 0, 0);

      text.setLayoutParams(textViewMargin);
      originalImage.setLayoutParams(imageMargin);

      text.setTextColor(color);
      originalImage.setImageResource(resourceIds[i]);
    }
  }

  public static int dpsToPixel(Activity activity, int sizeInDp) {
    float scale = activity.getResources().getDisplayMetrics().density;
    return (int) (sizeInDp * scale + 0.5f);
  }

  public static class Ints {
    public static final int MEDICO = 1, PACIENTE = 0, BORRAR = 2, NULL = -1;
    public static final int ERROR_CONEXION = 200;
    public static final int ERROR_GENERAL = 100;
    public static final int SEXO_MASCULINO = 1, SEXO_FEMENINO = 0;
    public static final int ERROR_IO = 300;
  }

  public static class Strings {
    public static final String AZUCAR = "azucar";
    public static final String DIETA = "Dieta";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String GLUCOSA = "Glucosa";
    public static final String INICIO = "Inicio";
    public static final String FECHA_REGISTRO = "fechaRegistro";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO_PATERNO = "apellidoPaterno";
    public static final String APELLIDO_MATERNO = "apellidoMaterno";
    public static final String EMAIL = "email";
    public static final String KEYWORD = "keyword";
    public static final String FECHA_NACIMIENTO = "fechaNacimiento";
    public static final String SEXO = "sexo";
    public static final String ID_ROL = "idRol";
    public static final String CEDULA_PROFESIONAL = "cedulaProfesional";
    public static final String CODIGO_MEDICO = "codigoMedico";
    public static final String ID_USUARIO = "idUsuario";
    public static final String RESPUESTA = "respuesta";
    public static final String MENSAJE = "mensaje";
    public static final String INDIVIDUO_ROL = "individuoRol";
    public static final String ERROR = "ERROR";
    public static final String OK = "OK";
    public static final String TOKEN = "TOKEN";
    public static final String VOID = "VOID";

    public static final String URL_SERVIDOR = "http://35.202.245.109";
    public static final String URL_REGISTRO = URL_SERVIDOR + "/tt-escom-diabetes/session/usuarios";
    public static final String URL_LOGIN = URL_SERVIDOR + "/tt-escom-diabetes/session/login";
    public static final String URL_DATOS = URL_SERVIDOR + "/tt-escom-diabetes/ceres/usuarios/";
    public static final String URL_PACIENTE = URL_SERVIDOR + "/tt-escom-diabetes/ceres/pacientes";

    public static final String USUARIO = "Usuario";
    public static final String LOGIN = "Login";
    public static final String ERROR_GENERAL = "Error general";
    public static final String ERROR_CONEXION = "Error de conexión";
    public static final String CODIGO_ERROR = "CodigoE";
    public static final String INFO_GUARDADA = "Información guardada.";
    public static final String NOMBRE_COMPLETO = "nombreCompleto";
    public static final String ERROR_IO = "Error IO";
  }
}
