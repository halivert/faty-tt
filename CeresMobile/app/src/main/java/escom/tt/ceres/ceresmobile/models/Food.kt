package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ENERGY
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FOOD
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FOOD_TYPE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_FOOD
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.QUANTITY
import org.json.JSONObject

class Food(private val food: JSONObject) {

  fun getFoodId(): Int {
    return if (food.has(ID_FOOD)) food.getInt(ID_FOOD) else -1
  }

  fun getEnergy(): Double {
    return if (food.has(ENERGY)) food.getDouble(ENERGY) else -1.0
  }

  fun getFoodType(): String {
    return if (food.has(FOOD_TYPE)) food.getString(FOOD_TYPE) else ""
  }

  var quantity: String = if (food.has(QUANTITY)) food.getString(QUANTITY) else ""
  var name: String = if (food.has(FOOD)) food.getString(FOOD) else ""

  override fun toString(): String {
    return "${if (food.has(FOOD)) food.getString(FOOD) else ""}"
  }
}