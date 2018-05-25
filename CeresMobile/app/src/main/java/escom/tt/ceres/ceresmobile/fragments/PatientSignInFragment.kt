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
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.SEXO_FEMENINO
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.SEXO_MASCULINO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.CODIGO_MEDICO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FECHA_NACIMIENTO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_ROL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.KEYWORD
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SEX
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_REGISTER
import escom.tt.ceres.ceresmobile.tools.Functions.createDatePickerDialog
import org.json.JSONObject

class PatientSignInFragment : Fragment() {
  private var mListener: OnPatientSignInInteraction? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.patient_sign_in_fragment, container, false)
    val birthDateEdit = view.findViewById<EditText>(R.id.dtpFechaNac)

    val picker = createDatePickerDialog(
        activity = activity,
        dtp = birthDateEdit,
        yearsMin = 130,
        yearsMax = -18)

    view.findViewById<View>(R.id.btnRegistro).setOnClickListener {
      signIn()
    }

    birthDateEdit.setOnClickListener {
      picker.show()
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

  private fun verifyEditText(
      stringId: Int, idElem: Int, duration: Int = Toast.LENGTH_SHORT): Boolean {
    val context = activity.applicationContext
    val editText = activity.findViewById<EditText>(idElem)
    Toast.makeText(context, stringId, duration).show()
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
    val name = activity.findViewById<EditText>(R.id.editNombre).text.toString()
    val lastName = activity.findViewById<EditText>(R.id.editApPat).text.toString()
    val mothersLastName = activity.findViewById<EditText>(R.id.editApMat).text.toString()
    val doctorCode = activity.findViewById<EditText>(R.id.editTokenMed).text.toString()
    val email = activity.findViewById<EditText>(R.id.editEmail).text.toString()
    val keyword = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
    val keywordConfirmation =
        activity.findViewById<EditText>(R.id.editKeywordConf).text.toString()
    val birthDate = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

    if (name.isBlank())
      return verifyEditText(R.string.name_validation, R.id.editNombre)

    if (lastName.isBlank())
      return verifyEditText(R.string.last_name_validation, R.id.editApPat)

    if (mothersLastName.isBlank())
      return verifyEditText(R.string.mothers_last_name_validation, R.id.editApMat)

    if (doctorCode.isBlank())
      return verifyEditText(R.string.doctor_code_validation, R.id.editTokenMed)

    if (email.isBlank())
      return verifyEditText(R.string.email_validation, R.id.editEmail)
    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
      return verifyEditText(
          R.string.invalid_email_format, R.id.editEmail, Toast.LENGTH_LONG)

    when {
      keyword.isBlank() ->
        return verifyEditText(R.string.password_validation, R.id.editKeyword)
      keywordConfirmation.isBlank() ->
        return verifyEditText(R.string.falta_confirmacion, R.id.editKeywordConf)
      keyword != keywordConfirmation ->
        return verifyEditText(R.string.passwords_do_not_match, R.id.editKeyword)
      !activity.findViewById<RadioButton>(R.id.radioSexoHombre).isChecked ->
        if (!activity.findViewById<RadioButton>(R.id.radioSexoMujer).isChecked) {
          return verifyRadioGroup(R.string.sex_validation, R.id.radioGrupo)
        }
    }

    return if (birthDate.isBlank())
      verifyEditText(R.string.birth_date_validation, R.id.dtpFechaNac)
    else true
  }

  private fun signIn() {
    if (validate()) {
      val context = activity.applicationContext
      val name = activity.findViewById<EditText>(R.id.editNombre).text.toString()
      val lastName = activity.findViewById<EditText>(R.id.editApPat).text.toString()
      val mothersLastName = activity.findViewById<EditText>(R.id.editApMat).text.toString()
      val doctorCode = activity.findViewById<EditText>(R.id.editTokenMed).text.toString()
      val email = activity.findViewById<EditText>(R.id.editEmail).text.toString()
      val keyword = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
      val birthDate = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

      val sex =
          if (activity.findViewById<RadioButton>(R.id.radioSexoHombre).isChecked)
            SEXO_MASCULINO.toString()
          else
            SEXO_FEMENINO.toString()

      val dataToSend = JSONObject().apply {
        put(NAME, name)
        put(APELLIDO_PATERNO, lastName)
        put(APELLIDO_MATERNO, mothersLastName)
        put(EMAIL, email)
        put(KEYWORD, keyword)
        put(FECHA_NACIMIENTO, birthDate)
        put(SEX, sex)
        put(ID_ROL, PACIENTE)
        put(CODIGO_MEDICO, doctorCode)
      }

      val request = JsonObjectRequest(POST, URL_REGISTER, dataToSend,
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
