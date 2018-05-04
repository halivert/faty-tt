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
import escom.tt.ceres.ceresmobile.activities.PatientMainActivity
import escom.tt.ceres.ceresmobile.adapters.DietListAdapter
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.sql.Timestamp

class PatientDietFragment : Fragment(), DietListAdapter.DietItemInteraction {
  private var mListener: OnDietListener? = null
  private var dietItemInteractionListener: DietListAdapter.DietItemInteraction? = null

  override fun onDietItemClick(position: Int) {
    if (mListener != null)
      mListener!!.onSelectedDiet(position)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.patient_diet_fragment, container, false)

    dietItemInteractionListener = this@PatientDietFragment

    var recyclerView = view.findViewById<RecyclerView>(R.id.diet_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(activity)
      adapter = DietListAdapter(PatientMainActivity.diets, dietItemInteractionListener)
      addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    var swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.diet_refresh)
    swipeContainer.setOnRefreshListener({
      launch(UI) {
        getDiets()
        recyclerView.adapter.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
      }
    })

    if (PatientMainActivity.diets.isEmpty()) {
      Toast.makeText(
          activity.applicationContext,
          "No hay dietas para el paciente",
          Toast.LENGTH_LONG).show()
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDietListener) {
      mListener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnDietListener")
    }
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnDietListener) {
      mListener = activity
    } else {
      throw RuntimeException(activity.toString() + " must implement OnDietListener")
    }
  }

  private suspend fun getDiets() {
    PatientMainActivity.diets.clear()
    var urlDiets = "${Constants.Strings.URL_PATIENT}/${PatientMainActivity.idPatient}/dietas"
    var response = CeresRequestQueue.getInstance(activity).apiArrayRequest(
        Request.Method.GET,
        urlDiets,
        null).await()

    for (i in 0 until response.length()) {
      var responseObject = response.getJSONObject(i)

      var idDiet = if (responseObject.has("id_DIETA")) responseObject.getInt("id_DIETA") else -1
      var idPatient = if (responseObject.has("id_PACIENTE")) responseObject.getInt("id_PACIENTE") else -1
      var idDoctor = if (responseObject.has("id_MEDICO")) responseObject.getInt("id_MEDICO") else -1
      var description = if (responseObject.has("descripcion")) responseObject.getString("descripcion") else ""
      var assignDate = if (responseObject.has("fecha_ASIGNACION"))
        Timestamp(responseObject.getLong("fecha_ASIGNACION")) else Timestamp(0)

      var diet = Diet(idDiet, description, assignDate, idPatient, idDoctor)
      PatientMainActivity.diets.add(diet)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDietListener {
    fun onSelectedDiet(position: Int)
  }

  companion object {
    fun newInstance(): PatientDietFragment {
      return PatientDietFragment()
    }
  }
}
