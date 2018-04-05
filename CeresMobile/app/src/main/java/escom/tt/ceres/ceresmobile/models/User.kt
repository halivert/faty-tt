package escom.tt.ceres.ceresmobile.models

import org.json.JSONObject

class User() {
  var name: String? = ""
  var email: String? = ""
  private var sex: String? = ""
  var idDoctor: Int? = -1
  var idUser: Int? = -1
  var lastName: String? = ""
  var mothersLastName: String? = ""
  var birthDate: String? = ""

  constructor(
      name: String,
      email: String,
      sex: String?,
      idDoctor: Int?,
      idUser: Int?,
      lastName: String?,
      mothersLastName: String?,
      birthDate: String?) : this() {
    this.name = name
    this.email = email
    this.sex = sex
    this.idDoctor = idDoctor
    this.idUser = idUser
    this.lastName = lastName
    this.mothersLastName = mothersLastName
    this.birthDate = birthDate
  }

  constructor(patientJson: JSONObject) : this() {
    this.name = if (patientJson.has("nombre")) patientJson.getString("nombre") else ""
    this.email = if (patientJson.has("email")) patientJson.getString("email") else ""
    this.sex = if (patientJson.has("sexo")) patientJson.getString("sexo") else ""
    this.idDoctor = if (patientJson.has("id_MEDICO")) patientJson.getInt("id_MEDICO") else -1
    this.idUser = if (patientJson.has("id_USUARIO")) patientJson.getInt("id_USUARIO") else -1
    this.lastName = if (patientJson.has("ap_PAT")) patientJson.getString("ap_PAT") else ""
    this.mothersLastName = if (patientJson.has("ap_MAT")) patientJson.getString("ap_MAT") else ""
    this.birthDate = if (patientJson.has("fec_NAC")) patientJson.getString("fec_NAC") else ""
  }
}