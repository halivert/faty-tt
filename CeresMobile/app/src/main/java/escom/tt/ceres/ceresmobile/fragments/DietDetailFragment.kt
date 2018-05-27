package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request.Method.GET
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.PatientMainActivity.Companion.idPatient
import escom.tt.ceres.ceresmobile.models.Diet
import escom.tt.ceres.ceresmobile.models.Meal
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.BREAKFAST
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.COLLATION_1
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.COLLATION_2
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DINNER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DIET
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MEAL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.URL_PATIENT
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import java.util.*

class DietDetailFragment : Fragment() {
  private var idDiet: Int = -1
  private var listener: OnDietDetailInteraction? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      idDiet = it.getInt(ID_DIET)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_diet_detail, container, false)

    if (idDiet > -1) {
      launch(UI) {
        val diet: Diet = getDietDetail(idDiet)
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
          // Log.e("Log", jsonFoods.toString(2))
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

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface OnDietDetailInteraction

  private suspend fun getDietDetail(idDiet: Int): Diet {
    val urlDiets = "$URL_PATIENT/$idPatient/dietas/$idDiet"
    val response = CeresRequestQueue.getInstance(activity).apiObjectRequest(
        GET,
        urlDiets,
        null).await()

    return Diet(response)
  }

  companion object {
    @JvmStatic
    fun newInstance(idDiet: Int) =
        DietDetailFragment().apply {
          arguments = Bundle().apply {
            putInt(ID_DIET, idDiet)
          }
        }
  }
}
