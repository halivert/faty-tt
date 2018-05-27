package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AGE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AP_MAT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AP_PAT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FEC_NAC
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SEX
import org.json.JSONObject

class Patient(private val patientJSON: JSONObject = JSONObject()) {
  var idPatient: Int = if (patientJSON.has(ID_USER)) patientJSON.getInt(ID_USER) else -1
  var name: String = if (patientJSON.has(NAME)) patientJSON.getString(NAME) else ""
  var email: String = if (patientJSON.has(EMAIL)) patientJSON.getString(EMAIL) else ""
  var sex: String = if (patientJSON.has(SEX)) patientJSON.getString(SEX) else ""
  var lastName: String = if (patientJSON.has(AP_PAT)) patientJSON.getString(AP_PAT) else ""
  var mothersLastName = if (patientJSON.has(AP_MAT)) patientJSON.getString(AP_MAT) else ""
  var birthDate: String = if (patientJSON.has(FEC_NAC)) patientJSON.getString(FEC_NAC) else ""
  var age = if (patientJSON.has(AGE)) patientJSON.getInt(AGE) else -1
  val fullName = "$name $lastName $mothersLastName"

  fun toJSONString(i: Int? = null): String =
      if (i != null)
        patientJSON.toString(i)
      else
        patientJSON.toString()

  override fun toString(): String = "Nombre: ${this.name}, Email: ${this.email}"

}