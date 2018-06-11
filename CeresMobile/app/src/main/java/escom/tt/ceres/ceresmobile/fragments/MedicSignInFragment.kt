package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.MEDICO
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.SEXO_FEMENINO
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.SEXO_MASCULINO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.CEDULA_PROFESIONAL
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
import escom.tt.ceres.ceresmobile.tools.Functions
import org.json.JSONObject


class MedicSignInFragment : Fragment() {
  private var mListener: OnDoctorSignInInteraction? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.medic_sign_in_fragment, container, false)

    view.findViewById<View>(R.id.btn_register).setOnClickListener { signIn() }

    val birthDate = view.findViewById<EditText>(R.id.dtpFechaNac)

    val picker = Functions.createDatePickerDialog(
        activity = activity,
        dtp = birthDate,
        yearsMin = 130,
        yearsMax = -18)

    birthDate.setOnClickListener {
      picker.show()
    }

    return view
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
    val context = activity.applicationContext
    val radioGroup = activity.findViewById<RadioGroup>(idElem)
    Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
    radioGroup.requestFocus()
    return false
  }

  private fun validate(): Boolean {
    val name = activity.findViewById<EditText>(R.id.editNombre).text.toString()
    val lastName = activity.findViewById<EditText>(R.id.editApPat).text.toString()
    val mothersLastName = activity.findViewById<EditText>(R.id.editApMat).text.toString()
    val professionalLicense = activity.findViewById<EditText>(R.id.editCedulaProf).text.toString()
    val email = activity.findViewById<EditText>(R.id.editEmail).text.toString()
    val keyword = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
    val keywordConfirmation = activity.findViewById<EditText>(R.id.editKeywordConf).text.toString()
    val birthDate = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

    if (name.isBlank())
      return verifyEditText(R.string.name_validation, R.id.editNombre)

    if (lastName.isBlank())
      return verifyEditText(R.string.last_name_validation, R.id.editApPat)

    if (mothersLastName.isBlank())
      return verifyEditText(R.string.mothers_last_name_validation, R.id.editApMat)

    if (professionalLicense.isBlank())
      return verifyEditText(R.string.professional_license_validation, R.id.editCedulaProf)

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
        if (!(activity.findViewById<View>(R.id.radioSexoMujer) as RadioButton).isChecked) {
          return verifyRadioGroup(R.string.sex_validation, R.id.radioGrupo)
        }
    }

    return if (birthDate.isBlank()) {
      verifyEditText(R.string.birth_date_validation, R.id.dtpFechaNac)
    } else true
  }

  private fun signIn() {
    if (validate()) {
      val context = activity.applicationContext
      val sex: String =
          if (activity.findViewById<RadioButton>(R.id.radioSexoHombre).isChecked)
            SEXO_MASCULINO.toString()
          else
            SEXO_FEMENINO.toString()

      val name = activity.findViewById<EditText>(R.id.editNombre).text.toString()
      val lastName = activity.findViewById<EditText>(R.id.editApPat).text.toString()
      val mothersLastName = activity.findViewById<EditText>(R.id.editApMat).text.toString()
      val professionalLicense =
          activity.findViewById<EditText>(R.id.editCedulaProf).text.toString()
      val email = activity.findViewById<EditText>(R.id.editEmail).text.toString()
      val keyword = activity.findViewById<EditText>(R.id.editKeyword).text.toString()
      val birthDate = activity.findViewById<EditText>(R.id.dtpFechaNac).text.toString()

      val dataToSend = JSONObject().apply {
        put(NAME, name)
        put(APELLIDO_PATERNO, lastName)
        put(APELLIDO_MATERNO, mothersLastName)
        put(EMAIL, email)
        put(KEYWORD, keyword)
        put(FECHA_NACIMIENTO, birthDate)
        put(SEX, sex)
        put(ID_ROL, MEDICO)
        put(CEDULA_PROFESIONAL, professionalLicense)
        put(CODIGO_MEDICO, null)
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
                mListener!!.successfulDoctorSignIn(email, keyword)
              }
            } else
              Toast.makeText(context, it.toString(2), Toast.LENGTH_SHORT).show()
          },
          Response.ErrorListener {
            Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
          })

      CeresRequestQueue.getInstance(context).addToRequestQueue(request)
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDoctorSignInInteraction)
      mListener = context
    else
      throw RuntimeException(ERROR)
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnDoctorSignInInteraction)
      mListener = activity
    else
      throw RuntimeException(ERROR)
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorSignInInteraction {
    fun successfulDoctorSignIn(email: String, keyword: String)
  }
}