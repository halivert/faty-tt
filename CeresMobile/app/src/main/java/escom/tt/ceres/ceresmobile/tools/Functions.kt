package escom.tt.ceres.ceresmobile.tools

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.constraint.ConstraintLayout
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import escom.tt.ceres.ceresmobile.fragments.pickers.DatePickerFragment
import escom.tt.ceres.ceresmobile.fragments.pickers.TimePickerFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
    if (!dtp.text.toString().isBlank()) {
      try {
        calendar.time = sdf.parse(dtp.text.toString())
      } catch (e: ParseException) {
        e.printStackTrace()
      }

    }
    datePickerFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, mes, dia ->
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
    if (!dtp.text.toString().isBlank()) {
      try {
        calendar.time = sdf.parse(dtp.text.toString())
      } catch (e: ParseException) {
        e.printStackTrace()
      }
    }

    timePickerFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, i, i1 ->
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

  fun deselectElement(activity: Activity, texts: IntArray, imageViews: IntArray, resourceIds: IntArray) {
    if (texts.size != imageViews.size || texts.size != resourceIds.size)
      return

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

  private fun dpsToPixel(activity: Activity, sizeInDp: Int): Int {
    val scale = activity.resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
  }

  object Ints {
    const val MEDICO = 1
    const val PACIENTE = 0
    const val BORRAR = 2
    const val NULL = -1
    const val ERROR_CONEXION = 200
    const val ERROR_GENERAL = 100
    const val SEXO_MASCULINO = 1
    const val SEXO_FEMENINO = 0
    const val ERROR_IO = 300
  }

  object Strings {
    const val AZUCAR = "azucar"
    const val DIETA = "Dieta"
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val GLUCOSA = "Glucosa"
    const val INICIO = "Inicio"
    const val FECHA_REGISTRO = "fechaRegistro"
    const val NOMBRE = "nombre"
    const val APELLIDO_PATERNO = "apellidoPaterno"
    const val APELLIDO_MATERNO = "apellidoMaterno"
    const val EMAIL = "email"
    const val KEYWORD = "keyword"
    const val FECHA_NACIMIENTO = "fechaNacimiento"
    const val SEXO = "sexo"
    const val ID_ROL = "idRol"
    const val CEDULA_PROFESIONAL = "cedulaProfesional"
    const val CODIGO_MEDICO = "codigoMedico"
    const val ID_USUARIO = "idUsuario"
    const val RESPUESTA = "respuesta"
    const val MENSAJE = "mensaje"
    const val INDIVIDUO_ROL = "individuoRol"
    const val ERROR = "ERROR"
    const val OK = "OK"
    const val TOKEN = "TOKEN"
    const val VOID = "VOID"

    const val URL_SERVIDOR = "http://35.202.245.109"
    const val URL_REGISTRO = "$URL_SERVIDOR/tt-escom-diabetes/session/usuarios"
    const val URL_LOGIN = "$URL_SERVIDOR/tt-escom-diabetes/session/login"
    const val URL_DATOS = "$URL_SERVIDOR/tt-escom-diabetes/ceres/usuarios/"
    const val URL_PACIENTE = "$URL_SERVIDOR/tt-escom-diabetes/ceres/pacientes"

    const val USUARIO = "Usuario"
    const val LOGIN = "Login"
    const val ERROR_GENERAL = "Error general"
    const val ERROR_CONEXION = "Error de conexión"
    const val CODIGO_ERROR = "CodigoE"
    const val INFO_GUARDADA = "Información guardada."
    const val NOMBRE_COMPLETO = "nombreCompleto"
    const val ERROR_IO = "Error IO"
  }
}
