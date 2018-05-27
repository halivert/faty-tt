package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.adapters.MedicalHistoryAdapter
import escom.tt.ceres.ceresmobile.models.MedicalHistory
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_PATIENT
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MedicalHistoriesFragment : Fragment() {
  private var idPatient: Int? = null
  private var listener: OnFragmentInteractionListener? = null
  var medicalHistories: MutableList<MedicalHistory> = mutableListOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      idPatient = it.getInt(ID_PATIENT)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    var view = inflater.inflate(R.layout.fragment_medical_histories, container, false)
    var recyclerView = view.findViewById<RecyclerView>(R.id.rvMedicalHistories)
    launch(UI) {
      getMedicalHistories()
      var adapter = MedicalHistoryAdapter(activity, medicalHistories)
      recyclerView.adapter = adapter
    }
    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnFragmentInteractionListener) {
      listener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnDietDetailInteraction")
    }
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnFragmentInteractionListener) {
      listener = activity
    } else {
      throw RuntimeException(activity.toString() + " must implement OnDietDetailInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  suspend fun getMedicalHistories() {
    var urlPatients = "${Constants.Strings.URL_PATIENT}/${this.idPatient}/historialclinico"
    var queue = CeresRequestQueue.getInstance(activity)
    var response = queue.apiArrayRequest(Request.Method.GET, urlPatients, null).await()

    this.medicalHistories.clear()
    for (i in 0 until response.length()) {
      var responseObject = response.getJSONObject(i)
      var history: MedicalHistory?
      if (responseObject.has(Constants.Strings.ERROR)) {
        Toast.makeText(activity, responseObject.getString(Constants.Strings.ERROR), Toast.LENGTH_LONG).show()
        break
      } else {
        history = MedicalHistory(responseObject)
        this.medicalHistories.add(history)
      }
    }
  }

  interface OnFragmentInteractionListener

  companion object {
    @JvmStatic
    fun newInstance(idPatient: Int) =
        MedicalHistoriesFragment().apply {
          arguments = Bundle().apply {
            putInt(ID_PATIENT, idPatient)
          }
        }
  }
}
