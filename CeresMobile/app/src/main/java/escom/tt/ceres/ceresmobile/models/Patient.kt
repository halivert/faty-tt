package escom.tt.ceres.ceresmobile.models

import org.json.JSONObject

class Patient() {
  var patientJson: JSONObject = JSONObject()
  var name: String? = ""
  var email: String? = ""
  var sex: String? = ""
  var idUser: Int = -1
  var lastName: String? = ""
  var mothersLastName: String? = ""
  var birthDate: String? = ""

  constructor(patientJson: JSONObject) : this() {
    this.patientJson = patientJson
    this.name = if (patientJson.has("nombre")) patientJson.getString("nombre") else ""
    this.email = if (patientJson.has("email")) patientJson.getString("email") else ""
    this.sex = if (patientJson.has("sexo")) patientJson.getString("sexo") else ""
    this.idUser = if (patientJson.has("id_USUARIO")) patientJson.getInt("id_USUARIO") else -1
    this.lastName = if (patientJson.has("ap_PAT")) patientJson.getString("ap_PAT") else ""
    this.mothersLastName = if (patientJson.has("ap_MAT")) patientJson.getString("ap_MAT") else ""
    this.birthDate = if (patientJson.has("fec_NAC")) patientJson.getString("fec_NAC") else ""
  }

  fun getId(): Int? = this.idUser

  fun toJSONString(i: Int? = null): String =
      if (i != null)
        patientJson.toString(i)
      else
        patientJson.toString()

  override fun toString(): String = "Nombre: ${this.name}, Email: ${this.email}"

}