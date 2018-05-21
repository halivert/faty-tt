package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.R.id.editEmail
import escom.tt.ceres.ceresmobile.R.id.editKeyword
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_ROL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.KEYWORD
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NOMBRE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NOMBRE_COMPLETO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_DATOS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.USER_JSON
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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
      val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)

      launch(UI) {
        if (progressBar != null) progressBar.visibility = View.VISIBLE
        login(email, keyword)
        if (progressBar != null) progressBar.visibility = View.INVISIBLE
      }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.login_fragment, container, false)

    with(view) {
      findViewById<EditText>(editEmail).setText("")
      findViewById<EditText>(editKeyword).setText("")
    }
    val button = view.findViewById<Button>(R.id.login_button)
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

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnLoginInteraction) {
      mListener = activity
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private fun buttonLoginClick() {
    val email = activity.findViewById<EditText>(editEmail).text.toString()
    val keyword = activity.findViewById<EditText>(editKeyword).text.toString()
    val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)

    if (validate(editEmail, editKeyword)) {
      launch(UI) {
        if (progressBar != null) progressBar.visibility = View.VISIBLE
        login(email, keyword)
        if (progressBar != null) progressBar.visibility = View.INVISIBLE
      }
    }
  }

  private suspend fun login(email: String?, key: String?) {
    val loginButton = activity.findViewById<Button>(R.id.login_button)
    val context = activity.applicationContext
    val dataToSend = JSONObject()
    dataToSend.put(EMAIL, email)
    dataToSend.put(KEYWORD, key)

    loginButton.isEnabled = false
    val queue = CeresRequestQueue.getInstance(context)

    var it = queue.apiObjectRequest(POST, URL_LOGIN, dataToSend).await()

    var message = if (it.has(MENSAJE)) it.getString(MENSAJE) else ERROR
    if (it.has(RESPUESTA)) {
      var response = it.getString(RESPUESTA)
      if (response == OK) {
        val idUser = if (it.has(ID_USUARIO)) it.getInt(ID_USUARIO) else -1

        it = queue.apiObjectRequest(GET, URL_DATOS + idUser.toString(), null).await()

        val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val idRol = if (it.has(ID_ROL)) it.getInt(ID_ROL) else -1
        var name = if (it.has(NOMBRE)) it.getString(NOMBRE) else ERROR
        var lastName = if (it.has(APELLIDO_PATERNO)) it.getString(APELLIDO_PATERNO) else ERROR
        var mothersLastName = if (it.has(APELLIDO_MATERNO)) it.getString(APELLIDO_MATERNO) else ERROR
        var fullName = "$name $lastName $mothersLastName"

        editor.run {
          putString(USER_JSON, it.toString())
          putInt(ID_USUARIO, idUser)
          putString(NOMBRE_COMPLETO, fullName)
          putString(NOMBRE, name)
          putString(APELLIDO_PATERNO, lastName)
          putString(APELLIDO_MATERNO, mothersLastName)
          putInt(ID_ROL, idRol)
          apply()
        }

        mListener?.successfulLogin(idUser, idRol)
      } else {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    loginButton.isEnabled = true
  }

  /*
  override fun getUserData(idUser: Int) {
    val request = JsonObjectRequest(GET, URL_DATOS + idUser.toString(), null,
        Listener {
          val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
          val editor = preferences.edit()
          val idRol = if (it.has(ID_ROL)) it.getInt(ID_ROL) else -1
          var name = if (it.has(NOMBRE)) it.getString(NOMBRE) else ERROR
          var lastName = if (it.has(APELLIDO_PATERNO)) it.getString(APELLIDO_PATERNO) else ERROR
          var mothersLastName = if (it.has(APELLIDO_MATERNO)) it.getString(APELLIDO_MATERNO) else ERROR
          var fullName = "$name $lastName $mothersLastName"

          editor.run {
            putInt(ID_USUARIO, idUser)
            putString(NOMBRE_COMPLETO, fullName)
            putString(NOMBRE, name)
            putString(APELLIDO_PATERNO, lastName)
            putString(APELLIDO_MATERNO, mothersLastName)
            putInt(ID_ROL, idRol)
            apply()
          }

          mListener?.successfulLogin(idUser, idRol)
        },
        ErrorListener {
          Toast.makeText(activity.applicationContext, ERROR, Toast.LENGTH_SHORT).show()
        })

    CeresRequestQueue.getInstance(activity.applicationContext).addToRequestQueue(request)
  }
  */

  private fun validate(idEditEmail: Int, idEditKeyword: Int): Boolean {
    val editEmail = activity.findViewById<EditText>(idEditEmail)
    val editKeyword = activity.findViewById<EditText>(idEditKeyword)
    val email = editEmail.text.toString()
    val keyword = editKeyword.text.toString()
    val context = activity.applicationContext

    if (email.isBlank()) {
      Toast.makeText(context, R.string.email_validation, Toast.LENGTH_SHORT).show()
      editEmail.requestFocus()
      return false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      Toast.makeText(context, R.string.invalid_format, Toast.LENGTH_SHORT).show()
      editEmail.requestFocus()
      return false
    }

    if (keyword.isBlank()) {
      Toast.makeText(context, R.string.password_validation, Toast.LENGTH_SHORT).show()
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