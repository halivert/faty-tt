package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.DoctorMainActivity
import escom.tt.ceres.ceresmobile.adapters.PatientListAdapter
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.UNSUCCESSFUL
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class DoctorPatientsFragment : Fragment(), PatientListAdapter.PatientItemInteraction {
  private var patientItemListener: PatientListAdapter.PatientItemInteraction? = null
  private var mListener: OnDoctorPatientsInteraction? = null

  override fun onPatientItemClick(position: Int) {
    if (mListener != null)
      mListener!!.onSelectedPatient(position)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.doctor_patients_fragment, container, false)
    val toolbar = view.findViewById<Toolbar>(R.id.app_bar)
    patientItemListener = this@DoctorPatientsFragment

    (activity as DoctorMainActivity).setSupportActionBar(toolbar)

    val recyclerView = view.findViewById<RecyclerView>(R.id.patients_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(activity)
      adapter = PatientListAdapter(DoctorMainActivity.patients, patientItemListener)
      addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    val swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.patients_refresh)
    swipeContainer.setOnRefreshListener {
      launch(UI) {
        getPatients()
        recyclerView.adapter.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
      }
    }

    if (DoctorMainActivity.patients.isEmpty()) {
      launch(UI) {
        swipeContainer.isRefreshing = true
        getPatients()
        swipeContainer.isRefreshing = false
      }
    }

    return view
  }

  private suspend fun getPatients() {
    val urlPatients = "${Constants.Strings.URL_MEDICO}/${DoctorMainActivity.idUser}/pacientes/"
    val queue = CeresRequestQueue.getInstance(activity)
    val response = queue.apiArrayRequest(Request.Method.GET, urlPatients, null).await()

    DoctorMainActivity.patients.clear()
    for (i in 0 until response.length()) {
      val responseObject = response.getJSONObject(i)
      var patient: Patient?
      if (responseObject.has(UNSUCCESSFUL)) {
        Toast.makeText(
            activity, getString(R.string.no_patients_found), Toast.LENGTH_LONG).show()
        continue
      }

      patient = Patient(responseObject)
      DoctorMainActivity.patients.add(patient)
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnDoctorPatientsInteraction) {
      mListener = context
    } else {
      throw RuntimeException("Must implement OnDoctorPatientsInteraction")
    }
  }

  private fun refreshPatientsList() {
    DoctorMainActivity.patients.clear()
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorPatientsInteraction {
    fun onSelectedPatient(position: Int)
  }

  companion object {
    fun newInstance(refresh: Boolean = false) =
        DoctorPatientsFragment().apply {
          if (refresh) refreshPatientsList()
        }
  }
}
