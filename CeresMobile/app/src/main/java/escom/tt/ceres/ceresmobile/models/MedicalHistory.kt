package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DATE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_MEDICAL_HISTORY
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_PATIENT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SIZE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SUGAR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.WEIGHT
import org.json.JSONObject

class MedicalHistory() {
  var idMedicalHistory: Int = -1
  var idPatient = -1
  var weight: Double = -1.0
  var size: Double = -1.0
  var date: String = ""
  var sugar: Double = -1.0

  constructor(patientJson: JSONObject) : this() {
    this.idMedicalHistory = if (patientJson.has(ID_MEDICAL_HISTORY)) patientJson.getInt(ID_MEDICAL_HISTORY) else -1
    this.idPatient = if (patientJson.has(ID_PATIENT)) patientJson.getInt(ID_PATIENT) else -1
    this.weight = if (patientJson.has(WEIGHT)) patientJson.getDouble(WEIGHT) else -1.0
    this.size = if (patientJson.has(SIZE)) patientJson.getDouble(SIZE) else -1.0
    this.date = if (patientJson.has(DATE)) patientJson.getString(DATE) else ""
    this.sugar = if (patientJson.has(SUGAR)) patientJson.getDouble(SUGAR) else -1.0
  }

  override fun toString(): String {
    return "Nombre: ${this.idMedicalHistory}, Email: ${this.idPatient}"
  }
}