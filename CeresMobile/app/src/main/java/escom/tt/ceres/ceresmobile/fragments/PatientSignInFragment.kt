package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

import org.json.JSONObject

import escom.tt.ceres.ceresmobile.tools.JSONPostRequest
import escom.tt.ceres.ceresmobile.R

import escom.tt.ceres.ceresmobile.tools.Functions.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.SEXO_FEMENINO
import escom.tt.ceres.ceresmobile.tools.Functions.Ints.SEXO_MASCULINO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.CODIGO_MEDICO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.FECHA_NACIMIENTO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_ROL
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.KEYWORD
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.NOMBRE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.SEXO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.URL_REGISTRO

class PatientSignInFragment : Fragment() {
  private var mListener: CFPacienteRegistro? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle): View? {
    val view = inflater.inflate(R.layout.fragmento_paciente_registro, container, false)

    view.findViewById<View>(R.id.btnRegistro).setOnClickListener { registrarse() }

    view.findViewById<View>(R.id.dtpFechaNac).setOnClickListener { showDatePicker() }

    return view
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is CFPacienteRegistro) {
      mListener = activity
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private fun verifEditText(stringId: Int, idElem: Int): Boolean {
    val ac = activity
    val ctx = ac.applicationContext
    val editText = ac.findViewById<EditText>(idElem)
    Toast.makeText(ctx, stringId, Toast.LENGTH_SHORT).show()
    editText.requestFocus()
    return false
  }

  private fun verifRadioGroup(stringId: Int, idElem: Int): Boolean {
    val ac = activity
    val ctx = ac.applicationContext
    val radioGroup = ac.findViewById<RadioGroup>(idElem)
    Toast.makeText(ctx, stringId, Toast.LENGTH_SHORT).show()
    radioGroup.requestFocus()
    return false
  }

  fun showDatePicker() {
    val datePickerFragment: DatePickerFragment
    val ac = activity
    val fechaNac = ac.findViewById<EditText>(R.id.dtpFechaNac)
    datePickerFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { datePicker, year, mes, dia ->
      val fecha = dia.toString() + "/" + (mes + 1) + "/" + year
      fechaNac.setText(fecha)
    })

    datePickerFragment.show(ac.fragmentManager, "datePicker")
  }

  private fun validar(): Boolean {
    val ac = activity
    val nombre: String
    val apPaterno: String
    val apMaterno: String
    val codigoMed: String
    val email: String
    val keyword: String
    val keywordRep: String
    val fechaNac: String
    nombre = (ac.findViewById<View>(R.id.editNombre) as EditText).text.toString()
    apPaterno = (ac.findViewById<View>(R.id.editApPat) as EditText).text.toString()
    apMaterno = (ac.findViewById<View>(R.id.editApMat) as EditText).text.toString()
    codigoMed = (ac.findViewById<View>(R.id.editTokenMed) as EditText).text.toString()
    email = (ac.findViewById<View>(R.id.editEmail) as EditText).text.toString()
    keyword = (ac.findViewById<View>(R.id.editKeyword) as EditText).text.toString()
    keywordRep = (ac.findViewById<View>(R.id.editKeywordConf) as EditText).text.toString()
    fechaNac = (ac.findViewById<View>(R.id.dtpFechaNac) as EditText).text.toString()

    if (nombre.isEmpty())
      return verifEditText(R.string.falta_nombre, R.id.editNombre)

    if (apPaterno.isEmpty())
      return verifEditText(R.string.falta_ap_paterno, R.id.editApPat)

    if (apMaterno.isEmpty())
      return verifEditText(R.string.falta_ap_materno, R.id.editApMat)

    if (codigoMed.isEmpty())
      return verifEditText(R.string.falta_codigo_medico, R.id.editTokenMed)

    if (email.isEmpty())
      return verifEditText(R.string.falta_email, R.id.editEmail)
    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
      return verifEditText(R.string.formato_incorrecto, R.id.editEmail)

    if (keyword.isEmpty())
      return verifEditText(R.string.falta_keyword, R.id.editKeyword)
    else if (keywordRep.isEmpty())
      return verifEditText(R.string.falta_confirmacion, R.id.editKeywordConf)
    else if (keyword != keywordRep)
      return verifEditText(R.string.keyword_no_coincide, R.id.editKeyword)

    if (!(ac.findViewById<View>(R.id.radioSexoHombre) as RadioButton).isChecked) {
      if (!(ac.findViewById<View>(R.id.radioSexoMujer) as RadioButton).isChecked) {
        return verifRadioGroup(R.string.falta_sexo, R.id.radioGrupo)
      }
    }

    return if (fechaNac.isEmpty()) verifEditText(R.string.falta_fecha_nac, R.id.dtpFechaNac) else true

  }

  fun registrarse() {
    if (validar()) {
      val ac = activity
      val ctx = ac.applicationContext
      try {
        val nombre: String
        val apPaterno: String
        val apMaterno: String
        val codigoMed: String
        val email: String
        val keyword: String
        val fechaNac: String
        val sexo: String
        nombre = (ac.findViewById<View>(R.id.editNombre) as EditText).text.toString()
        apPaterno = (ac.findViewById<View>(R.id.editApPat) as EditText).text.toString()
        apMaterno = (ac.findViewById<View>(R.id.editApMat) as EditText).text.toString()
        codigoMed = (ac.findViewById<View>(R.id.editTokenMed) as EditText).text.toString()
        email = (ac.findViewById<View>(R.id.editEmail) as EditText).text.toString()
        keyword = (ac.findViewById<View>(R.id.editKeyword) as EditText).text.toString()
        fechaNac = (ac.findViewById<View>(R.id.dtpFechaNac) as EditText).text.toString()

        if ((ac.findViewById<View>(R.id.radioSexoHombre) as RadioButton).isChecked) {
          sexo = SEXO_MASCULINO.toString()
        } else
          sexo = SEXO_FEMENINO.toString()

        val request = JSONObject()
        request.put(NOMBRE, nombre)
        request.put(APELLIDO_PATERNO, apPaterno)
        request.put(APELLIDO_MATERNO, apMaterno)
        request.put(EMAIL, email)
        request.put(KEYWORD, keyword)
        request.put(FECHA_NACIMIENTO, fechaNac)
        request.put(SEXO, sexo)
        request.put(ID_ROL, PACIENTE)
        request.put(CODIGO_MEDICO, codigoMed)

        val send = request.toString()

        Log.e(ERROR, send)
        val resultado = JSONPostRequest().execute(URL_REGISTRO, send).get()
        Log.e(ERROR, resultado)
        val jsonObject = JSONObject(resultado)
        var mensaje = ERROR

        if (jsonObject.has(RESPUESTA)) {
          val respuesta = jsonObject.getString(RESPUESTA)
          if (jsonObject.has(MENSAJE)) {
            mensaje = jsonObject.getString(MENSAJE)
          }
          if (respuesta == ERROR) {
            Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show()
          } else if (respuesta == OK) {
            Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show()
            mListener!!.registroExitoso(email, keyword)
          }
        } else {
          Toast.makeText(ctx, resultado, Toast.LENGTH_SHORT).show()
        }
      } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(ctx, ERROR, Toast.LENGTH_SHORT).show()
      }

    }
  }

  interface CFPacienteRegistro {
    fun registroExitoso(email: String, keyword: String)
  }
}
