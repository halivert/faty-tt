package escom.tt.ceres.ceresmobile.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Functions
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.AZUCAR
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.FECHA_REGISTRO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.URL_PACIENTE
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SugarRegisterFragment : Fragment() {
  private var urlSugarRegister = "$URL_PACIENTE/"
  private var mListener: OnSugarRegisterListener? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_registrar_glucosa, container, false)

    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val idPatient = preferences.getInt(ID_USUARIO, -1)
    if (idPatient >= 0)
      urlSugarRegister += idPatient.toString() + "/registroglucosa"
    else
      urlSugarRegister = ""

    val save = view.findViewById<Button>(R.id.btnGuardarAzucar)
    save.setOnClickListener {
      val activity = activity
      val inputMethodManager = activity
          .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

      val focusedView = activity.currentFocus
      if (focusedView != null) {
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
      }
      saveSugar()
    }

    var dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = dateFormat.format(Calendar.getInstance().time)
    dateFormat = SimpleDateFormat("HH:mm")
    val currentTime = dateFormat.format(Calendar.getInstance().time)

    val sugarDate = view.findViewById<EditText>(R.id.etFechaAzucar)
    val sugarHour = view.findViewById<EditText>(R.id.etHoraAzucar)
    sugarDate.setText(currentDate)
    sugarHour.setText(currentTime)
    sugarDate.setOnClickListener { Functions.showDatePicker(activity, sugarDate) }
    sugarHour.setOnClickListener { Functions.showTimePicker(activity, sugarHour) }

    return view
  }

  private fun saveSugar() {
    val context = activity.applicationContext
    val parameters = HashMap<String, String>()
    val sugar = activity.findViewById<EditText>(R.id.etAzucar)
    val date = activity.findViewById<EditText>(R.id.etFechaAzucar)
    val hour = activity.findViewById<EditText>(R.id.etHoraAzucar)

    if (sugar.text.toString().isBlank()) {
      Toast.makeText(context, "Falta ázucar", Toast.LENGTH_LONG).show()
      return
    }
    if (date.text.toString().isBlank()) {
      Toast.makeText(context, "Falta fecha", Toast.LENGTH_LONG).show()
      return
    }
    if (hour.text.toString().isBlank()) {
      Toast.makeText(context, "Falta hora", Toast.LENGTH_LONG).show()
      return
    }

    parameters[AZUCAR] = sugar.text.toString()
    // parametros.put(FECHA_REGISTRO, fecha + " " + hora + ":00.0");
    parameters[FECHA_REGISTRO] = date.text.toString() + " " + hour.text.toString()
    val jsonObject = JSONObject(parameters)

    val request = JsonObjectRequest(POST, urlSugarRegister, jsonObject,
        Response.Listener { response ->
          try {
            if (response.has(RESPUESTA)) {
              val respuesta = response.getString(RESPUESTA)
              if (respuesta == OK && response.has(MENSAJE)) {
                Toast.makeText(context, response.getString(MENSAJE), Toast.LENGTH_LONG).show()
              }
            }

            mListener!!.endSugarRegister()
          } catch (e: Exception) {
            e.printStackTrace()
          }
        },
        Response.ErrorListener { error ->
          Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
          error.printStackTrace()
        }
    )

    CeresRequestQueue.getInstance(context).addToRequestQueue(request)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnSugarRegisterListener)
      mListener = context
    else
      throw RuntimeException(context.toString() + " must implement OnSugarRegisterListener")
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnSugarRegisterListener {
    fun endSugarRegister(): Int
  }

  companion object {
    fun newInstance(): SugarRegisterFragment {
/*
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    */
      return SugarRegisterFragment()
    }
  }
}
