package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.PatientMainActivity
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DIET
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.POSITION
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject

class DietDetailFragment : Fragment() {
  private var idDiet: Int = -1
  private var position: Int = -1
  private var listener: OnDietDetailInteraction? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      idDiet = it.getInt(ID_DIET)
      position = it.getInt(POSITION)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    var view = inflater.inflate(R.layout.fragment_diet_detail, container, false)

    val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

    if (idDiet > -1 && position > -1) {
      launch(UI) {
        progressBar.visibility = VISIBLE
        var diet: Diet = getDietDetail(idDiet, position)
        progressBar.visibility = INVISIBLE
        view.findViewById<TextView>(R.id.tv_description).text = diet.description
        view.findViewById<TextView>(R.id.tv_date).text = diet.assignDate.toString()
        try {
          var jsonFoods = JSONObject(diet.availableFoods)
          view.findViewById<TextView>(R.id.tv_available_foods).text = jsonFoods.toString(2)
        } catch (e: Exception) {
          view.findViewById<TextView>(R.id.tv_available_foods).text = diet.availableFoods
        }
      }
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDietDetailInteraction) {
      listener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnDietDetailInteraction")
    }
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnDietDetailInteraction) {
      listener = activity
    } else {
      throw RuntimeException(activity.toString() + " must implement OnDietDetailInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface OnDietDetailInteraction

  private suspend fun getDietDetail(idDiet: Int, position: Int): Diet {
    if (PatientMainActivity.diets[position].availableFoods.isNullOrEmpty()) {
      var urlDiets = "${Constants.Strings.URL_PATIENT}/${PatientMainActivity.idPatient}/dietas/${idDiet}"
      var response = CeresRequestQueue.getInstance(activity).apiObjectRequest(
          Request.Method.GET,
          urlDiets,
          null).await()

      PatientMainActivity.diets[position] = Diet(response)
    }

    return PatientMainActivity.diets[position]
  }


  companion object {
    @JvmStatic
    fun newInstance(idDiet: Int, position: Int) =
        DietDetailFragment().apply {
          arguments = Bundle().apply {
            putInt(ID_DIET, idDiet)
            putInt(POSITION, position)
          }
        }
  }
}
