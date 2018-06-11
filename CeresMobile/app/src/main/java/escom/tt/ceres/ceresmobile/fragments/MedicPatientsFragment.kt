package escom.tt.ceres.ceresmobile.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.android.volley.Request
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.MedicMainActivity
import escom.tt.ceres.ceresmobile.adapters.PatientListAdapter
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.UNSUCCESSFUL
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MedicPatientsFragment : Fragment(),
    PatientListAdapter.PatientItemInteraction,
    SearchView.OnQueryTextListener {
  override fun onQueryTextSubmit(query: String): Boolean {
    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.medic_search_menu, menu)

    if (activity != null) {
      val searchManager = activity.getSystemService(Context.SEARCH_SERVICE)
          as SearchManager
      val searchMenuItem = menu.findItem(R.id.app_bar_search)
      val searchView = searchMenuItem.actionView as SearchView

      searchView.isIconified = true
      searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
      searchMenuItem.setOnMenuItemClickListener {
        searchView.isIconified = false
        true
      }

      searchView.setOnQueryTextListener(this)
    }
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onQueryTextChange(newText: String): Boolean {
    if (activity != null) {
      val recyclerView = activity.findViewById<RecyclerView>(R.id.patients_list)
      val adapter = recyclerView.adapter as PatientListAdapter
      adapter.filter.filter(newText)
    }

    return true
  }

  private var patientItemListener: PatientListAdapter.PatientItemInteraction? = null
  private var mListener: OnDoctorPatientsInteraction? = null

  override fun onPatientItemClick(position: Int) {
    if (mListener != null)
      mListener!!.onSelectedPatient(position)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.doctor_patients_fragment, container, false)
    patientItemListener = this@MedicPatientsFragment

    val searchBar = view.findViewById<SearchView>(R.id.s_filter)
    searchBar.setOnQueryTextListener(this)
    searchBar.visibility = View.GONE

    val recyclerView = view.findViewById<RecyclerView>(R.id.patients_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(activity)
      adapter = PatientListAdapter(MedicMainActivity.patients, patientItemListener)
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

    if (MedicMainActivity.patients.isEmpty()) {
      launch(UI) {
        swipeContainer.isRefreshing = true
        getPatients()
        swipeContainer.isRefreshing = false
      }
    }

    return view
  }

  private suspend fun getPatients() {
    if (activity != null) {
      val urlPatients = "${Constants.Strings.URL_MEDICO}/${MedicMainActivity.idUser}/pacientes/"
      val queue = CeresRequestQueue.getInstance(activity)
      val response = queue.apiArrayRequest(Request.Method.GET, urlPatients, null).await()

      MedicMainActivity.patients.clear()
      for (i in 0 until response.length()) {
        val responseObject = response.getJSONObject(i)
        var patient: Patient?
        if (responseObject.has(UNSUCCESSFUL)) {
          Toast.makeText(
              activity, getString(R.string.no_patients_found), Toast.LENGTH_LONG).show()
          continue
        }

        patient = Patient(responseObject)
        MedicMainActivity.patients.add(patient)
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

  private fun refreshPatientsList() {
    MedicMainActivity.patients.clear()
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorPatientsInteraction {
    fun onSelectedPatient(position: Int)
  }

  companion object {
    const val TAG = "DOCTOR_PATIENTS_FRAGMENT_TAG"

    fun newInstance(refresh: Boolean = false) =
        MedicPatientsFragment().apply {
          if (refresh) refreshPatientsList()
        }
  }
}
