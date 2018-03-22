package escom.tt.ceres.ceresmobile.tools

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.constraint.ConstraintLayout
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

import escom.tt.ceres.ceresmobile.fragments.DatePickerFragment
import escom.tt.ceres.ceresmobile.fragments.TimePickerFragment

/**
 * Created by hali on 27/10/17.
 */

object Functions {
  fun qString(s: String): String {
    return "\"" + s + "\""
  }

  fun urlTokenMedico(idMedico: Int): String {
    return "http://35.188.191.232/tt-escom-diabetes/ceres/medico/" + idMedico.toString() + "/token/"
  }

  @JvmOverloads
  fun showDatePicker(activity: Activity, dtp: EditText, separator: String = "/") {
    val datePickerFragment: DatePickerFragment
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy")
    if (!dtp.text.toString().isEmpty()) {
      try {
        calendar.time = sdf.parse(dtp.text.toString())
      } catch (e: ParseException) {
        e.printStackTrace()
      }

    }
    datePickerFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { datePicker, year, mes, dia ->
      // final String fecha = year + separator + (mes + 1) + separator + dia;
      val fecha = dia.toString() + separator + (mes + 1) + separator + year
      dtp.setText(fecha)
    }, calendar)

    datePickerFragment.show(activity.fragmentManager, "datePicker")
  }

  fun showTimePicker(activity: Activity, dtp: EditText) {
    val timePickerFragment: TimePickerFragment
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("HH:mm")
    if (!dtp.text.toString().isEmpty()) {
      try {
        calendar.time = sdf.parse(dtp.text.toString())
      } catch (e: ParseException) {
        e.printStackTrace()
      }

    }
    timePickerFragment = TimePickerFragment.newInstance({ timePicker, i, i1 ->
      val hora = i.toString() + ":" + i1
      dtp.setText(hora)
    }, calendar)

    timePickerFragment.show(activity.fragmentManager, "datePicker")
  }

  fun selectElement(activity: Activity, text: TextView, originalImage: ImageView, resourceId: Int) {
    val textViewMargin = text.layoutParams as ConstraintLayout.LayoutParams
    val imageMargin = originalImage.layoutParams as ConstraintLayout.LayoutParams

    val color = -0x1000000
    val marginSide = dpsToPixel(activity, 12)
    val marginBottom = dpsToPixel(activity, 10)
    val marginTop = dpsToPixel(activity, 6)

    textViewMargin.setMargins(marginSide, 0, marginSide, marginBottom)
    imageMargin.setMargins(0, marginTop, 0, 0)

    text.layoutParams = textViewMargin
    originalImage.layoutParams = imageMargin

    text.setTextColor(color)
    originalImage.setImageResource(resourceId)
  }

  fun unselectElements(activity: Activity, texts: IntArray, imageViews: IntArray, resourceIds: IntArray) {
    if (texts.size != imageViews.size || texts.size != resourceIds.size) {
      return
    }

    var textViewMargin: ConstraintLayout.LayoutParams
    var imageMargin: ConstraintLayout.LayoutParams
    val color = -0xf2a8b8
    val marginSide = dpsToPixel(activity, 12)
    val marginBottom = dpsToPixel(activity, 8)
    val marginTop = dpsToPixel(activity, 8)

    val elements = texts.size
    for (i in 0 until elements) {
      val text = activity.findViewById<TextView>(texts[i])
      val originalImage = activity.findViewById<ImageView>(imageViews[i])

      textViewMargin = text.layoutParams as ConstraintLayout.LayoutParams
      imageMargin = originalImage.layoutParams as ConstraintLayout.LayoutParams

      textViewMargin.setMargins(marginSide, 0, marginSide, marginBottom)
      imageMargin.setMargins(0, marginTop, 0, 0)

      text.layoutParams = textViewMargin
      originalImage.layoutParams = imageMargin

      text.setTextColor(color)
      originalImage.setImageResource(resourceIds[i])
    }
  }

  fun dpsToPixel(activity: Activity, sizeInDp: Int): Int {
    val scale = activity.resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
  }

  object Ints {
    val MEDICO = 1
    val PACIENTE = 0
    val BORRAR = 2
    val NULL = -1
    val ERROR_CONEXION = 200
    val ERROR_GENERAL = 100
    val SEXO_MASCULINO = 1
    val SEXO_FEMENINO = 0
    val ERROR_IO = 300
  }

  object Strings {
    val AZUCAR = "azucar"
    val DIETA = "Dieta"
    val DATE_FORMAT = "dd/MM/yyyy"
    val GLUCOSA = "Glucosa"
    val INICIO = "Inicio"
    val FECHA_REGISTRO = "fechaRegistro"
    val NOMBRE = "nombre"
    val APELLIDO_PATERNO = "apellidoPaterno"
    val APELLIDO_MATERNO = "apellidoMaterno"
    val EMAIL = "email"
    val KEYWORD = "keyword"
    val FECHA_NACIMIENTO = "fechaNacimiento"
    val SEXO = "sexo"
    val ID_ROL = "idRol"
    val CEDULA_PROFESIONAL = "cedulaProfesional"
    val CODIGO_MEDICO = "codigoMedico"
    val ID_USUARIO = "idUsuario"
    val RESPUESTA = "respuesta"
    val MENSAJE = "mensaje"
    val INDIVIDUO_ROL = "individuoRol"
    val ERROR = "ERROR"
    val OK = "OK"
    val TOKEN = "TOKEN"
    val VOID = "VOID"

    val URL_SERVIDOR = "http://35.202.245.109"
    val URL_REGISTRO = "$URL_SERVIDOR/tt-escom-diabetes/session/usuarios"
    val URL_LOGIN = "$URL_SERVIDOR/tt-escom-diabetes/session/login"
    val URL_DATOS = "$URL_SERVIDOR/tt-escom-diabetes/ceres/usuarios/"
    val URL_PACIENTE = "$URL_SERVIDOR/tt-escom-diabetes/ceres/pacientes"

    val USUARIO = "Usuario"
    val LOGIN = "Login"
    val ERROR_GENERAL = "Error general"
    val ERROR_CONEXION = "Error de conexión"
    val CODIGO_ERROR = "CodigoE"
    val INFO_GUARDADA = "Información guardada."
    val NOMBRE_COMPLETO = "nombreCompleto"
    val ERROR_IO = "Error IO"
  }
}
