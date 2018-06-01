package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request.Method.POST
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AZUCAR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DATE_VALIDATION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FECHA_REGISTRO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.HOUR_VALIDATION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SUGAR_VALIDATION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import escom.tt.ceres.ceresmobile.tools.Functions
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PatientSugarRecordingFragment : Fragment() {
  private var urlSugarRecording = "$URL_PATIENT/"
  private var mListener: OnSugarRegisterListener? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    activity.findViewById<View>(R.id.sugar_item).isEnabled = false
    val view = inflater.inflate(R.layout.patient_sugar_recording_fragment, container, false)

    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val idPatient = preferences.getInt(ID_USUARIO, -1)
    if (idPatient >= 0)
      urlSugarRecording += idPatient.toString() + "/registroglucosa/"
    else
      urlSugarRecording = ""

    val save = view.findViewById<Button>(R.id.btnGuardarAzucar)
    save.setOnClickListener {
      val activity = activity
      val inputMethodManager = activity
          .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

      val focusedView = activity.currentFocus
      if (focusedView != null) {
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
      }
      launch(UI) {
        saveSugar()
      }
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

  private suspend fun saveSugar() {
    val context = activity.applicationContext
    val parameters = HashMap<String, String>()
    val sugar = activity.findViewById<EditText>(R.id.etAzucar)
    val date = activity.findViewById<EditText>(R.id.etFechaAzucar)
    val hour = activity.findViewById<EditText>(R.id.etHoraAzucar)
    val progressBar = activity.findViewById<ProgressBar>(R.id.progress_bar)

    if (sugar.text.toString().isNullOrEmpty()) {
      Toast.makeText(context, SUGAR_VALIDATION, Toast.LENGTH_LONG).show()
      return
    }
    if (date.text.toString().isNullOrEmpty()) {
      Toast.makeText(context, DATE_VALIDATION, Toast.LENGTH_LONG).show()
      return
    }
    if (hour.text.toString().isNullOrEmpty()) {
      Toast.makeText(context, HOUR_VALIDATION, Toast.LENGTH_LONG).show()
      return
    }

    parameters[AZUCAR] = sugar.text.toString()
    parameters[FECHA_REGISTRO] = date.text.toString() + " " + hour.text.toString()
    val jsonObject = JSONObject(parameters)

    if (progressBar != null) progressBar.visibility = View.VISIBLE
    var queue = CeresRequestQueue.getInstance(context)

    var response = queue.apiObjectRequest(POST, urlSugarRecording, jsonObject).await()
    if (response.has(RESPUESTA)) {
      val responseString = response.getString(RESPUESTA)
      if (response.has(MENSAJE)) {
        Toast.makeText(context, response.getString(MENSAJE), Toast.LENGTH_LONG).show()
        if (responseString == OK) {
          mListener?.endSugarRegister()
        }
      }
    }

    if (progressBar != null) progressBar.visibility = View.INVISIBLE
    /*
    val request = JsonObjectRequest(POST, urlSugarRecording, jsonObject,
        Response.Listener { response ->
          try {
            if (response.has(RESPUESTA)) {
              val respuesta = response.getString(RESPUESTA)
              if (respuesta == OK && response.has(MENSAJE)) {
                Toast.makeText(context, response.getString(MENSAJE), Toast.LENGTH_LONG).show()
              }
            }

            mListener?.endSugarRegister()
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
    */
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnSugarRegisterListener)
      mListener = context
    else
      throw RuntimeException(context.toString() + " must implement OnSugarRegisterListener")
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnSugarRegisterListener)
      mListener = activity
    else
      throw RuntimeException(activity.toString() + " must implement OnSugarRegisterListener")
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnSugarRegisterListener {
    fun endSugarRegister(): Int
  }

  companion object {
    fun newInstance(): PatientSugarRecordingFragment {
/*
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    */
      return PatientSugarRecordingFragment()
    }
  }
}
