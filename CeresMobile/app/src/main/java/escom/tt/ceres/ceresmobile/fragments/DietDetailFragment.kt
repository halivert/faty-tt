package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.PatientMainActivity
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.models.Meal
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.BREAKFAST
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.COLLATION_1
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.COLLATION_2
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DINNER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DIET
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MEAL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.POSITION
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import java.util.*

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

    if (idDiet > -1 && position > -1) {
      launch(UI) {
        var diet: Diet = getDietDetail(idDiet, position)
        view.findViewById<TextView>(R.id.tv_description).text = diet.description
        val dietDate = Calendar.getInstance()
        dietDate.timeInMillis = diet.assignDate.time
        view.findViewById<TextView>(R.id.tv_date).text =
            "${dietDate.get(Calendar.DAY_OF_MONTH)}/" +
            "${dietDate.get(Calendar.MONTH) + 1}/" +
            "${dietDate.get(Calendar.YEAR)}"
        var jsonFoods: JSONObject? = null
        try {
          jsonFoods = JSONObject(diet.availableFoods)
        } catch (e: Exception) {
          Log.e(ERROR, "Json parse error")
        }

        if (jsonFoods != null) {
          view.findViewById<TextView>(R.id.tv_breakfast).text = Meal(jsonFoods.getString(BREAKFAST)).showMeal()
          view.findViewById<TextView>(R.id.tv_collation_1).text = Meal(jsonFoods.getString(COLLATION_1)).showMeal()
          view.findViewById<TextView>(R.id.tv_meal).text = Meal(jsonFoods.getString(MEAL)).showMeal()
          view.findViewById<TextView>(R.id.tv_collation_2).text = Meal(jsonFoods.getString(COLLATION_2)).showMeal()
          view.findViewById<TextView>(R.id.tv_dinner).text = Meal(jsonFoods.getString(DINNER)).showMeal()
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
