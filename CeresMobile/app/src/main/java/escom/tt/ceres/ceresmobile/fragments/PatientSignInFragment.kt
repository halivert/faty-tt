package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
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
import escom.tt.ceres.ceresmobile.tools.Functions.showDatePicker
import org.json.JSONObject

class PatientSignInFragment : Fragment() {
  private var mListener: OnPatientSignInInteraction? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragmento_paciente_registro, container, false)

    view.findViewById<View>(R.id.btnRegistro).setOnClickListener { signIn() }
    var birthDateEdit = view.findViewById<EditText>(R.id.dtpFechaNac)
    birthDateEdit.setOnClickListener {
      showDatePicker(activity, birthDateEdit)
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnPatientSignInInteraction)
      mListener = context
    else
      throw RuntimeException(ERROR)
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnPatientSignInInteraction)
      mListener = activity
    else
      throw RuntimeException(ERROR)
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private fun verifyEditText(stringId: Int, idElem: Int): Boolean {
    val context = activity.applicationContext
    val editText = activity.findViewById<EditText>(idElem)
    Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
    editText.requestFocus()
    return false
  }

  private fun verifyRadioGroup(stringId: Int, idElem: Int): Boolean {
    val ctx = activity.applicationContext
    val radioGroup = activity.findViewById<RadioGroup>(idElem)
    Toast.makeText(ctx, stringId, Toast.LENGTH_SHORT).show()
    radioGroup.requestFocus()
    return false
  }

  private fun validate(): Boolean {
    val name: String = activity.findViewById<EditText>(R.id.editNombre).text.toString()
    val lastName: String = activity.findViewById<EditText>(R.id.editApPat).text.toString()
    val mothersLastName: String = activity.findViewById<EditText>(R.id.editApMat).text.toString()
    val doctorCode: String = activity.findViewById<EditText>(R.id.editTokenMed).text.toString()
    val email: String = activity.findViewById<EditText>(R.id.editEmail).text.toString()
    val keyword: String = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
    val keywordConfirmation: String =
        activity.findViewById<EditText>(R.id.editKeywordConf).text.toString()
    val birthDate: String = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

    if (name.isEmpty())
      return verifyEditText(R.string.falta_nombre, R.id.editNombre)

    if (lastName.isEmpty())
      return verifyEditText(R.string.falta_ap_paterno, R.id.editApPat)

    if (mothersLastName.isEmpty())
      return verifyEditText(R.string.falta_ap_materno, R.id.editApMat)

    if (doctorCode.isEmpty())
      return verifyEditText(R.string.falta_codigo_medico, R.id.editTokenMed)

    if (email.isEmpty())
      return verifyEditText(R.string.falta_email, R.id.editEmail)
    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
      return verifyEditText(R.string.formato_incorrecto, R.id.editEmail)

    when {
      keyword.isEmpty() ->
        return verifyEditText(R.string.falta_keyword, R.id.editKeyword)
      keywordConfirmation.isEmpty() ->
        return verifyEditText(R.string.falta_confirmacion, R.id.editKeywordConf)
      keyword != keywordConfirmation ->
        return verifyEditText(R.string.keyword_no_coincide, R.id.editKeyword)
      !activity.findViewById<RadioButton>(R.id.radioSexoHombre).isChecked ->
        if (!activity.findViewById<RadioButton>(R.id.radioSexoMujer).isChecked) {
          return verifyRadioGroup(R.string.falta_sexo, R.id.radioGrupo)
        }
    }

    return if (birthDate.isEmpty()) verifyEditText(R.string.falta_fecha_nac, R.id.dtpFechaNac) else true

  }

  private fun signIn() {
    if (validate()) {
      val context = activity.applicationContext
      val name: String = activity.findViewById<EditText>(R.id.editNombre).text.toString()
      val lastName: String = activity.findViewById<EditText>(R.id.editApPat).text.toString()
      val mothersLastName: String = activity.findViewById<EditText>(R.id.editApMat).text.toString()
      val doctorCode: String = activity.findViewById<EditText>(R.id.editTokenMed).text.toString()
      val email: String = activity.findViewById<EditText>(R.id.editEmail).text.toString()
      val keyword: String = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
      val birthDate: String = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

      val sex: String =
          if (activity.findViewById<RadioButton>(R.id.radioSexoHombre).isChecked)
            SEXO_MASCULINO.toString()
          else
            SEXO_FEMENINO.toString()

      val dataToSend = JSONObject()
      dataToSend.put(NOMBRE, name)
      dataToSend.put(APELLIDO_PATERNO, lastName)
      dataToSend.put(APELLIDO_MATERNO, mothersLastName)
      dataToSend.put(EMAIL, email)
      dataToSend.put(KEYWORD, keyword)
      dataToSend.put(FECHA_NACIMIENTO, birthDate)
      dataToSend.put(SEXO, sex)
      dataToSend.put(ID_ROL, PACIENTE)
      dataToSend.put(CODIGO_MEDICO, doctorCode)

      var request = JsonObjectRequest(POST, URL_REGISTRO, dataToSend,
          Response.Listener {
            var message = ERROR

            if (it.has(RESPUESTA)) {
              val response = it.getString(RESPUESTA)
              if (it.has(MENSAJE))
                message = it.getString(MENSAJE)
              if (response == ERROR)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
              else if (response == OK) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                mListener!!.successfulPatientSignIn(email, keyword)
              }
            } else
              Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
          },
          Response.ErrorListener {
            Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
          })

      CeresRequestQueue.getInstance(context).addToRequestQueue(request)
    }
  }

  interface OnPatientSignInInteraction {
    fun successfulPatientSignIn(email: String, keyword: String)
  }
}
