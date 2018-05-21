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
import escom.tt.ceres.ceresmobile.activities.DoctorMainActivity.Companion.idUser
import escom.tt.ceres.ceresmobile.activities.DoctorMainActivity.Companion.medic
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MEDIC_NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MESSAGE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.OK
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RECEIVER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPONSE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SENDER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.TOKEN_LOWER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_MEDICO
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONArray
import org.json.JSONObject

class DoctorGenerateCodeFragment : Fragment() {
  private var mListener: OnDoctorGenerateCodeInteraction? = null

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.doctor_generate_code_fragment, container, false)
    val btnGenerateCode = view.findViewById<Button>(R.id.btn_generate_code)
    val btnSendCode = view.findViewById<Button>(R.id.btn_send_code)
    val etCode = view.findViewById<EditText>(R.id.et_code)

    if (etCode.text.isNullOrBlank()) {
      btnSendCode.isEnabled = false
    }

    btnGenerateCode.setOnClickListener {
      launch(UI) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
        val code = generateMedicalCode()
        etCode.setText(code)
        if (!etCode.text.isNullOrBlank())
          btnSendCode.isEnabled = true
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.INVISIBLE
      }
    }

    btnSendCode.setOnClickListener {
      val email = view.findViewById<EditText>(R.id.et_email).text.toString()
      val code = view.findViewById<EditText>(R.id.et_code).text.toString()
      if (!email.isBlank() && !code.isBlank()) {
        launch(UI) {
          view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
          sendMedicalCode(email, code)
          view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.INVISIBLE
        }
      } else if (email.isBlank()) {
        Toast.makeText(
            activity,
            getString(R.string.email_validation),
            Toast.LENGTH_LONG).show()
      }
    }
    return view
  }

  private suspend fun generateMedicalCode(): String {
    var url = "$URL_MEDICO/$idUser/token/"
    var response = CeresRequestQueue.getInstance(activity)
        .apiObjectRequest(GET, url, null).await()

    if (response.has(MESSAGE)) return response.getString(MESSAGE)
    else return ERROR
  }

  private suspend fun sendMedicalCode(email: String, code: String) {
    val url = "$URL_MEDICO/$idUser/correoToken"

    val arrayReceivers = JSONArray()

    arrayReceivers.put(email)

    val requestObject = JSONObject()

    requestObject.put(RECEIVER, arrayReceivers)
    requestObject.put(TOKEN_LOWER, code)
    requestObject.put(MEDIC_NAME, medic.name)
    requestObject.put(SENDER, medic.email)

    val response = CeresRequestQueue.getInstance(activity)
        .apiObjectRequest(POST, url, requestObject).await()

    if (response.has(RESPONSE)) {
      if (response.getString(RESPONSE) == OK) {
        if (response.has(MESSAGE) && activity != null) {
          Toast.makeText(
              activity,
              response.getString(MESSAGE),
              Toast.LENGTH_LONG).show()
          activity.findViewById<EditText>(R.id.et_email).setText("")
          activity.findViewById<EditText>(R.id.et_code).setText("")
          activity.findViewById<Button>(R.id.btn_send_code).isEnabled = false
        }
      }
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnDoctorGenerateCodeInteraction) {
      mListener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement OnDoctorGenerateCodeInteraction")
    }
  }

  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    if (activity is OnDoctorGenerateCodeInteraction) {
      mListener = activity
    } else {
      throw RuntimeException(activity!!.toString() + " must implement OnDoctorGenerateCodeInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorGenerateCodeInteraction

  companion object {
    fun newInstance(): DoctorGenerateCodeFragment {
      return DoctorGenerateCodeFragment()
    }
  }
}
