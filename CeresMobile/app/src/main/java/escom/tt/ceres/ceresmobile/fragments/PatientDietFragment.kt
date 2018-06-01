package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.adapters.DietListAdapter
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DESCRIPTION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DIET_2
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_PATIENT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.sql.Timestamp

class PatientDietFragment : Fragment(), DietListAdapter.DietItemInteraction {
  private var idPatient: Int = 0
  private var mListener: OnDietListener? = null
  private var dietItemInteractionListener: DietListAdapter.DietItemInteraction? = null

  override fun onDietItemClick(idDiet: Int) {
    if (mListener != null)
      mListener!!.onSelectedDiet(idDiet)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      idPatient = it.getInt(ID_PATIENT)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    activity.findViewById<View>(R.id.diet_item)?.isEnabled = false
    val view = inflater.inflate(R.layout.patient_diet_fragment, container, false)

    dietItemInteractionListener = this@PatientDietFragment

    val recyclerView = view.findViewById<RecyclerView>(R.id.diet_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(activity)
      adapter = DietListAdapter(diets, dietItemInteractionListener)
      addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    val swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.diet_refresh)
    swipeContainer.setOnRefreshListener {
      launch(UI) {
        if (activity != null) {
          getDiets()
          recyclerView.adapter.notifyDataSetChanged()
          swipeContainer.isRefreshing = false
          if (diets.isEmpty()) {
            Toast.makeText(
                activity,
                getString(R.string.no_diets_found),
                Toast.LENGTH_LONG
            ).show()
          }
        }
      }
    }

    if (diets.isEmpty()) {
      launch(UI) {
        if (activity != null) {
          swipeContainer.isRefreshing = true
          getDiets()
          swipeContainer.isRefreshing = false
          recyclerView.adapter.notifyDataSetChanged()
          if (diets.isEmpty()) {
            Toast.makeText(
                activity,
                getString(R.string.no_diets_found),
                Toast.LENGTH_LONG
            ).show()
          }
        }
      }
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

  private suspend fun getDiets() {
    diets.clear()
    val urlDiets = "$URL_PATIENT/$idPatient/dietas"
    val response = CeresRequestQueue.getInstance(activity).apiArrayRequest(
        GET,
        urlDiets,
        null).await()

    for (i in 0 until response.length()) {
      val responseObject = response.getJSONObject(i)

      if (responseObject.has(ID_DIET_2)) {
        val idDiet = responseObject.getInt(ID_DIET_2)

        val idPatient =
            if (responseObject.has("id_PACIENTE"))
              responseObject.getInt("id_PACIENTE")
            else -1

        val idDoctor =
            if (responseObject.has("id_MEDICO"))
              responseObject.getInt("id_MEDICO")
            else -1

        val description =
            if (responseObject.has(DESCRIPTION))
              responseObject.getString(DESCRIPTION)
            else ""

        val assignDate =
            if (responseObject.has("fecha_ASIGNACION"))
              Timestamp(responseObject.getLong("fecha_ASIGNACION"))
            else
              Timestamp(0)

        val diet = Diet(idDiet, description, assignDate, idPatient, idDoctor)
        diets.add(diet)
      }
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDietListener {
    fun onSelectedDiet(idDiet: Int)
  }

  companion object {
    fun newInstance(idPatient: Int, reload: Boolean = false): PatientDietFragment =
        PatientDietFragment().apply {
          arguments = Bundle().apply {
            putInt(ID_PATIENT, idPatient)
          }
          if (reload) diets.clear()
        }

    var diets = mutableListOf<Diet>()
    const val TAG = "PATIENT_DIET_FRAGMENT"
  }
}
