package escom.tt.ceres.ceresmobile.models

import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AGE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AP_MAT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.AP_PAT
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.EMAIL
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.FEC_NAC
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_ROLE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ID_USER
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.MEDIC_CODE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.PROFESSIONAL_LICENSE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.SEX
import org.json.JSONObject

class User(private val userJSON: JSONObject = JSONObject()) {
  var idUser: Int = if (userJSON.has(ID_USER)) userJSON.getInt(ID_USER) else -1
  var name: String = if (userJSON.has(NAME)) userJSON.getString(NAME) else ""
  var lastName: String = if (userJSON.has(AP_PAT)) userJSON.getString(AP_PAT) else ""
  var mothersLastName = if (userJSON.has(AP_MAT)) userJSON.getString(AP_MAT) else ""
  var email: String = if (userJSON.has(EMAIL)) userJSON.getString(EMAIL) else ""
  var birthDate: String = if (userJSON.has(FEC_NAC)) userJSON.getString(FEC_NAC) else ""
  var sex: String = if (userJSON.has(SEX)) userJSON.getString(SEX) else ""

  var idRole = if (userJSON.has(ID_ROLE)) userJSON.getInt(ID_ROLE) else -1
  var medicCode: String =
      if (userJSON.has(MEDIC_CODE)) userJSON.getString(MEDIC_CODE)
      else ""

  var professionalLicense: String =
      if (userJSON.has(PROFESSIONAL_LICENSE)) userJSON.getString(PROFESSIONAL_LICENSE)
      else ""

  var age = if (userJSON.has(AGE)) userJSON.getInt(AGE) else -1

  fun toJSONString(i: Int? = null): String =
      if (i != null)
        userJSON.toString(i)
      else
        userJSON.toString()

  override fun toString(): String = "Nombre: ${this.name}, Email: ${this.email}"

}