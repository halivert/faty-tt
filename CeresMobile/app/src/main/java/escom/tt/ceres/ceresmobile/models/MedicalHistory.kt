package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.CARBOHYDRATES
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.HEIGHT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.HISTORY_DATE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_MEDICAL_HISTORY
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_PATIENT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.IMC
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.LIPIDS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.PHYSICAL_ACTIVITY
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.PROTEINS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SEX
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SIZE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SUGAR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.WEIGHT
import org.json.JSONObject

class MedicalHistory(var historyJSON: JSONObject) {
  var idMedicalHistory = if (historyJSON.has(ID_MEDICAL_HISTORY)) historyJSON.getInt(ID_MEDICAL_HISTORY) else -1
  var idPatient = if (historyJSON.has(ID_PATIENT)) historyJSON.getInt(ID_PATIENT) else -1
  var weight = if (historyJSON.has(WEIGHT)) historyJSON.getDouble(WEIGHT) else -1.0
  var size = if (historyJSON.has(SIZE)) historyJSON.getDouble(SIZE) else -1.0
  var date: String = if (historyJSON.has(HISTORY_DATE)) historyJSON.getString(HISTORY_DATE) else "-"
  var sugar = if (historyJSON.has(SUGAR)) historyJSON.getDouble(SUGAR) else -1.0
  var proteins = if (historyJSON.has(PROTEINS)) historyJSON.getDouble(PROTEINS) else -1.0
  var height = if (historyJSON.has(HEIGHT)) historyJSON.getDouble(HEIGHT) else -1.0
  var sex: String = if (historyJSON.has(SEX)) historyJSON.getString(SEX) else "-"
  var imc = if (historyJSON.has(IMC)) historyJSON.getDouble(IMC) else -1.0
  var carbohydrates = if (historyJSON.has(CARBOHYDRATES)) historyJSON.getDouble(CARBOHYDRATES) else -1.0
  var lipids = if (historyJSON.has(LIPIDS)) historyJSON.getDouble(LIPIDS) else -1.0
  var email: String = if (historyJSON.has(EMAIL)) historyJSON.getString(EMAIL) else "-"
  var physicalActivity: String =
      if (historyJSON.has(PHYSICAL_ACTIVITY)) historyJSON.getString(PHYSICAL_ACTIVITY)
      else ""

  fun toJSONString(i: Int? = null): String =
      if (i != null)
        historyJSON.toString(i)
      else
        historyJSON.toString()

  override fun toString(): String {
    return "Nombre: ${this.idMedicalHistory}, Email: ${this.email}"
  }
}