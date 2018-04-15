package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ASSIGN_DATE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AVAILABLE_FOODS
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.DESCRIPTION
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DIET
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_DOCTOR
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_PATIENT
import org.json.JSONObject
import java.sql.Timestamp

class Diet() {
  var idDiet: Int = -1
  var idPatient: Int = -1
  var idDoctor: Int = -1
  var description: String = ""
  var availableFoods: String = ""
  var assignDate: Timestamp = Timestamp(0)

  constructor(
      idDiet: Int,
      description: String,
      assignDate: Timestamp,
      idPatient: Int,
      idDoctor: Int) : this() {
    this.idDiet = idDiet
    this.description = description
    this.assignDate = assignDate
    this.idPatient = idPatient
    this.idDoctor = idDoctor
  }

  constructor(patientJson: JSONObject) : this() {
    this.idDiet = if (patientJson.has(ID_DIET)) patientJson.getInt(ID_DIET) else -1
    this.idPatient = if (patientJson.has(ID_PATIENT)) patientJson.getInt(ID_PATIENT) else -1
    this.idDoctor = if (patientJson.has(ID_DOCTOR)) patientJson.getInt(ID_DOCTOR) else -1
    this.description = if (patientJson.has(DESCRIPTION)) patientJson.getString(DESCRIPTION) else ""
    this.availableFoods = if (patientJson.has(AVAILABLE_FOODS)) patientJson.getString(AVAILABLE_FOODS) else ""
    this.assignDate = if (patientJson.has(ASSIGN_DATE))
      Timestamp(patientJson.getLong(ASSIGN_DATE))
    else
      Timestamp(0)
  }
}