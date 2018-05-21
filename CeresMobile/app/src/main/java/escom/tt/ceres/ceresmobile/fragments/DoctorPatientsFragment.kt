package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    var view = inflater!!.inflate(R.layout.doctor_patients_fragment, container, false)

    patientItemListener = this@DoctorPatientsFragment

    var recyclerView = view.findViewById<RecyclerView>(R.id.patients_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(activity)
      adapter = PatientListAdapter(DoctorMainActivity.patients, patientItemListener)
      addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    var swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.patients_refresh)
    swipeContainer.setOnRefreshListener {
      launch(UI) {
        getPatients()
        recyclerView.adapter.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
      }
    }

    /*
    var swipeLogin = view.findViewById<SwipeRefreshLayout>(R.id.swipe_list_view)

    swipeLogin.setOnRefreshListener {
      launch(UI) {
        getPatients()

        var patients = DoctorMainActivity.patients
        var patientsListView = view.findViewById<ListView>(R.id.patients_list_view)
        var adapter = PatientListAdapter(activity, patients)

        patientsListView.adapter = adapter
        adapter.notifyDataSetChanged()

        swipeLogin.isRefreshing = false
      }
    }

    var patients = DoctorMainActivity.patients
    var patientsListView = view.findViewById<ListView>(R.id.patients_list_view)
    var adapter = PatientListAdapter(activity, patients)

    patientsListView.adapter = adapter
    patientsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
      var patient = adapterView.getItemAtPosition(i)
      Toast.makeText(activity, patient.toString(), Toast.LENGTH_LONG).show()
    }

    adapter.notifyDataSetChanged()
    */

    return view
  }

  private suspend fun getPatients() {
    var urlPatients = "${Constants.Strings.URL_MEDICO}/${DoctorMainActivity.idUser}/pacientes/"
    var queue = CeresRequestQueue.getInstance(activity)
    var response = queue.apiArrayRequest(Request.Method.GET, urlPatients, null).await()

    DoctorMainActivity.patients.clear()
    for (i in 0 until response.length()) {
      var responseObject = response.getJSONObject(i)
      var patient: Patient?
      if (responseObject.has(Constants.Strings.ERROR)) {
        Toast.makeText(activity, responseObject.getString(Constants.Strings.ERROR), Toast.LENGTH_LONG).show()
        break
      } else {
        patient = Patient(responseObject)
        DoctorMainActivity.patients.add(patient)
      }
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

  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    if (activity is OnDoctorPatientsInteraction) {
      mListener = activity
    } else {
      throw RuntimeException("Must implement OnDoctorPatientsInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorPatientsInteraction {
    fun onSelectedPatient(position: Int)
  }

  companion object {
    fun newInstance(): DoctorPatientsFragment {
      return DoctorPatientsFragment()
    }
  }
}
