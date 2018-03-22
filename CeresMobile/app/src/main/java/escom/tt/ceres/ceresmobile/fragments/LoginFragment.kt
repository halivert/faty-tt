package escom.tt.ceres.ceresmobile.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.Tools.Functions.Strings.*
import escom.tt.ceres.ceresmobile.Tools.GetRequest
import escom.tt.ceres.ceresmobile.Tools.JSONPostRequest
import org.json.JSONObject

class LoginFragment : Fragment() {
  private var mListener: OnLoginInteraction? = null
  private var email: String? = null
  private var keyword: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      email = arguments.getString(EMAIL)
      keyword = arguments.getString(KEYWORD)
      login(email, keyword)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_login, container, false)

    view.findViewById<EditText>(R.id.editEmail).setText("")
    view.findViewById<EditText>(R.id.editKeyword).setText("")
    val button = view.findViewById<Button>(R.id.btnEntrar)
    button.setOnClickListener { buttonLoginClick() }
    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnLoginInteraction) {
      mListener = context
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private fun buttonLoginClick() {
    val email = activity.findViewById<EditText>(R.id.editEmail).text.toString()
    val keyword = activity.findViewById<EditText>(R.id.editKeyword).text.toString()

    if (validate(R.id.editEmail, R.id.editKeyword))
      login(email, keyword)
  }

  private fun login(email: String?, key: String?) {
    val context = activity.applicationContext
    val dataUrl = URL_DATOS
    var result: String
    val response: String
    val message: String
    val name: String
    val lastName: String
    val mothersLastName: String
    val fullName: String
    try {
      val dataToSend = JSONObject()
      dataToSend.put(EMAIL, email)
      dataToSend.put(KEYWORD, key)

      result = JSONPostRequest().execute(URL_LOGIN, dataToSend.toString()).get()
      val jsonObject = JSONObject(result)

      message = if (jsonObject.has(MENSAJE)) jsonObject.getString(MENSAJE) else ERROR
      if (jsonObject.has(RESPUESTA)) {
        response = jsonObject.getString(RESPUESTA)
        if (response == OK) {
          val idUser = if (jsonObject.has(ID_USUARIO)) jsonObject.getInt(ID_USUARIO) else -1
          result = GetRequest().execute(dataUrl + idUser.toString()).get()

          val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
          val editor = preferences.edit()
          val save = JSONObject(result)
          val idRol = if (save.has(ID_ROL)) save.getInt(ID_ROL) else -1
          name = if (save.has(NOMBRE)) save.getString(NOMBRE) else ERROR
          lastName = if (save.has(APELLIDO_PATERNO)) save.getString(APELLIDO_PATERNO) else ERROR
          mothersLastName = if (save.has(APELLIDO_MATERNO)) save.getString(APELLIDO_MATERNO) else ERROR
          fullName = "$name $lastName $mothersLastName"

          editor.putInt(ID_USUARIO, idUser)
          editor.putString(NOMBRE_COMPLETO, fullName)
          editor.putString(NOMBRE, name)
          editor.putString(APELLIDO_PATERNO, lastName)
          editor.putString(APELLIDO_MATERNO, mothersLastName)
          editor.putInt(ID_ROL, idRol)
          editor.apply()

          mListener!!.successfulLogin(idUser, idRol)
        } else {
          Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
      } else {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      }
    } catch (e: Exception) {
      Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
    }
  }

  private fun validate(idEditEmail: Int, idEditKeyword: Int): Boolean {
    val editEmail = activity.findViewById<EditText>(idEditEmail)
    val editKeyword = activity.findViewById<EditText>(idEditKeyword)
    val email = editEmail.text.toString()
    val keyword = editKeyword.text.toString()
    val context = activity.applicationContext

    if (email.isEmpty()) {
      Toast.makeText(context, R.string.falta_email, Toast.LENGTH_SHORT).show()
      editEmail.requestFocus()
      return false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      Toast.makeText(context, R.string.formato_incorrecto, Toast.LENGTH_SHORT).show()
      editEmail.requestFocus()
      return false
    }

    if (keyword.isEmpty()) {
      Toast.makeText(context, R.string.falta_keyword, Toast.LENGTH_SHORT).show()
      editKeyword.requestFocus()
      return false
    }

    return true
  }

  interface OnLoginInteraction {
    fun successfulLogin(idUser: Int, rolUser: Int)
  }

  companion object {

    fun newInstance(email: String, keyword: String): LoginFragment {
      val fragment = LoginFragment()
      val args = Bundle()
      args.putString(EMAIL, email)
      args.putString(KEYWORD, keyword)
      fragment.arguments = args
      return fragment
    }
  }
}
