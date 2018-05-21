package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.CARBOHYDRATES
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DATE
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

class MedicalHistory(patientJSON: JSONObject) {
  var idMedicalHistory = if (patientJSON.has(ID_MEDICAL_HISTORY)) patientJSON.getInt(ID_MEDICAL_HISTORY) else -1
  var idPatient = if (patientJSON.has(ID_PATIENT)) patientJSON.getInt(ID_PATIENT) else -1
  var weight = if (patientJSON.has(WEIGHT)) patientJSON.getDouble(WEIGHT) else -1.0
  var size = if (patientJSON.has(SIZE)) patientJSON.getDouble(SIZE) else -1.0
  var date: String = if (patientJSON.has(HISTORY_DATE)) patientJSON.getString(HISTORY_DATE) else ""
  var sugar = if (patientJSON.has(SUGAR)) patientJSON.getDouble(SUGAR) else -1.0
  var proteins = if (patientJSON.has(PROTEINS)) patientJSON.getDouble(PROTEINS) else -1.0
  var height = if (patientJSON.has(HEIGHT)) patientJSON.getDouble(HEIGHT) else -1.0
  var sex: String = if (patientJSON.has(SEX)) patientJSON.getString(SEX) else ""
  var imc = if (patientJSON.has(IMC)) patientJSON.getDouble(IMC) else -1.0
  var carbohydrates = if (patientJSON.has(CARBOHYDRATES)) patientJSON.getDouble(CARBOHYDRATES) else -1.0
  var lipids = if (patientJSON.has(LIPIDS)) patientJSON.getDouble(LIPIDS) else -1.0
  var email: String = if (patientJSON.has(EMAIL)) patientJSON.getString(EMAIL) else ""
  var physicalActivity: String =
      if (patientJSON.has(PHYSICAL_ACTIVITY)) patientJSON.getString(PHYSICAL_ACTIVITY)
      else ""

  override fun toString(): String {
    return "Nombre: ${this.idMedicalHistory}, Email: ${this.email}"
  }
}