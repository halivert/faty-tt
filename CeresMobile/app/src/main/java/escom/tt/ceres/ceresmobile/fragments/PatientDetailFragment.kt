package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.MedicalHistory
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RESPONSE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import escom.tt.ceres.ceresmobile.tools.Functions.calculateAge
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PatientDetailFragment : Fragment() {
  private var listener: OnPatientDetailInteraction? = null
  private var patient: Patient? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.apply {
      patient = try {
        Patient(JSONObject(getString(PATIENT_STRING)))
      } catch (e: Exception) {
        Patient()
      }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_patient_detail, container, false)

    if (patient != null) {
      view.findViewById<TextView>(R.id.tv_name).text = patient!!.fullName
      view.findViewById<TextView>(R.id.tv_birthdate).text = patient!!.birthDate

      view.findViewById<Button>(R.id.btn_show_diets).setOnClickListener {
        listener!!.showDiets(patient!!.idPatient)
      }

      val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
      val birthdate = df.parse(patient!!.birthDate)
      view.findViewById<TextView>(R.id.tv_age).text = calculateAge(birthdate).toString()

      launch(UI) {
        if (isAdded) {
          if (history == null) {
            view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
            history = getPatientDetail(patient!!.idPatient)
          }

          var error = false
          if (history!!.historyJSON.has(RESPONSE)) {
            error = history!!.historyJSON.getString(RESPONSE) == ERROR
          }

          if (!error) {
            history!!.apply {
              var textView: TextView = view.findViewById<TextView>(R.id.tv_date)
              textView.text = date

              textView = view.findViewById<TextView>(R.id.tv_weight)
              textView.text = getString(R.string.kilograms_prefix, weight)

              textView = view.findViewById<TextView>(R.id.tv_size)
              textView.text = getString(R.string.centimeters_prefix, size)

              textView = view.findViewById<TextView>(R.id.tv_height)
              textView.text = getString(R.string.meters_prefix, height)

              view.findViewById<TextView>(R.id.tv_imc).text = "$imc"
              view.findViewById<TextView>(R.id.tv_carbohydrates).text = "$carbohydrates %"
              view.findViewById<TextView>(R.id.tv_lipids).text = "$lipids %"
              view.findViewById<TextView>(R.id.tv_proteins).text = "$proteins %"
              view.findViewById<TextView>(R.id.tv_sugar).text = "$sugar %"
            }
          } else {
            view.findViewById<TextView>(R.id.tv_date).text = "-"
            view.findViewById<TextView>(R.id.tv_weight).text = "-"
            view.findViewById<TextView>(R.id.tv_size).text = "-"
            view.findViewById<TextView>(R.id.tv_height).text = "-"
            view.findViewById<TextView>(R.id.tv_imc).text = "-"
            view.findViewById<TextView>(R.id.tv_carbohydrates).text = "-"
            view.findViewById<TextView>(R.id.tv_lipids).text = "-"
            view.findViewById<TextView>(R.id.tv_proteins).text = "-"
            view.findViewById<TextView>(R.id.tv_sugar).text = "-"
          }
          activity.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.INVISIBLE
        }
      }
    }

    return view
  }

  private suspend fun getPatientDetail(id: Int): MedicalHistory {
    val urlMedicalHistory = "$URL_PATIENT/$id/ultimoHistorialclinico"
    val queue = CeresRequestQueue.getInstance(activity)
    val response = queue.apiObjectRequest(
        GET,
        urlMedicalHistory,
        null).await()

    if (response.length() >= 1) {
      return MedicalHistory(response)
    }

    return MedicalHistory(JSONObject())
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnPatientDetailInteraction) {
      listener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnPatientDetailInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface OnPatientDetailInteraction {
    fun showDiets(idPatient: Int)
  }

  companion object {
    const val PATIENT_STRING = "patientString"

    @JvmStatic
    fun newInstance(patientString: String) =
        PatientDetailFragment().apply {
          arguments = Bundle().apply {
            putString(PATIENT_STRING, patientString)
            history = null
          }
        }

    var history: MedicalHistory? = null
    const val TAG = "PATIENT_DETAIL_FRAGMENT"
  }
}
