package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Fraction
import org.json.JSONArray

class Meal(private val meal: JSONArray) {
  private var foodList: MutableList<Food> = mutableListOf()
  private var foodQuantity: MutableList<Int> = mutableListOf()
  private var foodListUpdated = false

  constructor(mealString: String) : this(JSONArray(mealString))

  fun getFoodList(): MutableList<Food> {
    if (foodListUpdated)
      return foodList

    foodListUpdated = true
    foodList = mutableListOf()
    for (i in 0 until meal.length()) {
      val food = Food(meal.getJSONObject(i))

      val foodIndex = foodInList(food)
      if (foodIndex < 0) {
        foodList.add(food)
        foodQuantity.add(1)
      } else {
        foodQuantity[foodIndex]++
      }
    }
    return foodList
  }

  private fun foodInList(food: Food): Int {
    for (i in 0 until foodList.size) {
      if (food.getFoodId() == foodList[i].getFoodId())
        return i
    }

    return -1
  }

  override fun toString(): String {
    if (!foodListUpdated) getFoodList()

    var result = ""
    for (i in 0 until foodList.size) {
      result += "${foodList[i]}" + if (i != foodList.size - 1) ", " else ""
    }
    return result
  }

  fun showMeal(): String {
    if (!foodListUpdated) getFoodList()

    if (foodQuantity.size != foodList.size)
      return ERROR

    var result = ""
    for (i in 0 until foodList.size) {
      result +=
          "${(Fraction("${foodQuantity[i]}") * Fraction(foodList[i].quantity))
              .toString(true)} "
      result += "${foodList[i]}\n"
    }
    return result
  }
}