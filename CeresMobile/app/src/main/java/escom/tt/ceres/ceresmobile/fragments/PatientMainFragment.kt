package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.MedicalHistory
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject

class PatientMainFragment : Fragment() {
  private var mListener: OnPatientMainInteraction? = null
  private var idUser: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      idUser = arguments.getInt(ARG1)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.patient_main_fragment, container, false)

    launch(UI) {
      val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
      val userName = preferences.getString(NAME, null)
      val lastName = preferences.getString(APELLIDO_PATERNO, null)
      val mothersLastName = preferences.getString(APELLIDO_MATERNO, null)

      val history = getPatientDetail(idUser)

      view.findViewById<TextView>(R.id.tv_sugar).text =
          getString(R.string.mg_dl, history.sugar)

      view.findViewById<TextView>(R.id.tv_lipids).text =
          getString(R.string.mg_dl, history.lipids)

      view.findViewById<TextView>(R.id.tv_carbohydrates).text =
          getString(R.string.mg_dl, history.carbohydrates)

      view.findViewById<TextView>(R.id.tv_proteins).text =
          getString(R.string.mg_dl, history.proteins)

      val textView = activity.findViewById<TextView>(R.id.userName)
      if (textView != null)
        textView.text = "$userName $lastName $mothersLastName"
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnPatientMainInteraction) {
      mListener = context
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private suspend fun getPatientDetail(id: Int): MedicalHistory {
    var urlMedicalHistory = "$URL_PATIENT/$id/ultimoHistorialclinico"
    var queue = CeresRequestQueue.getInstance(activity)
    var response = queue.apiObjectRequest(
        GET,
        urlMedicalHistory,
        null).await()

    if (response.length() >= 1) {
      return MedicalHistory(response)
    }

    return MedicalHistory(JSONObject())
  }

  interface OnPatientMainInteraction

  companion object {
    const val ARG1 = "ID_USER"

    fun newInstance(idUser: Int): PatientMainFragment {
      val fragment = PatientMainFragment()
      val args = Bundle()
      args.putInt(ARG1, idUser)
      fragment.arguments = args
      return fragment
    }
  }
}
