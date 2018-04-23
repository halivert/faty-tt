package escom.tt.ceres.ceresmobile.tools

object Constants {
  object Ints {
    const val MEDICO = 1
    const val PACIENTE = 0
    const val BORRAR = 2
    const val NULL = -1
    const val ERROR_CONEXION = 200
    const val ERROR_GENERAL = 100
    const val SEXO_MASCULINO = 1
    const val SEXO_FEMENINO = 0
    const val ERROR_IO = 300
  }

  object Strings {
    const val AZUCAR = "azucar"
    const val DIETA = "Dieta"
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val GLUCOSA = "Glucosa"
    const val INICIO = "Inicio"
    const val FECHA_REGISTRO = "fechaRegistro"
    const val NOMBRE = "nombre"
    const val APELLIDO_PATERNO = "apellidoPaterno"
    const val APELLIDO_MATERNO = "apellidoMaterno"
    const val EMAIL = "email"
    const val KEYWORD = "keyword"
    const val FECHA_NACIMIENTO = "fechaNacimiento"
    const val SEXO = "sexo"
    const val ID_ROL = "idRol"
    const val CEDULA_PROFESIONAL = "cedulaProfesional"
    const val CODIGO_MEDICO = "codigoMedico"
    const val ID_USUARIO = "idUsuario"
    const val RESPUESTA = "respuesta"
    const val MENSAJE = "mensaje"
    const val INDIVIDUO_ROL = "individuoRol"
    const val ERROR = "ERROR"
    const val OK = "OK"
    const val TOKEN = "TOKEN"
    const val VOID = "VOID"

    const val URL_SERVER = "http://35.202.245.109"
    const val URL_REGISTER = "$URL_SERVER/tt-escom-diabetes/session/usuarios"
    const val URL_LOGIN = "$URL_SERVER/tt-escom-diabetes/session/login"
    const val URL_DATOS = "$URL_SERVER/tt-escom-diabetes/ceres/usuarios/"
    const val URL_PATIENT = "$URL_SERVER/tt-escom-diabetes/ceres/pacientes"
    const val URL_MEDICO = "$URL_SERVER/tt-escom-diabetes/ceres/medico"

    const val USUARIO = "Usuario"
    const val LOGIN = "Login"
    const val ERROR_GENERAL = "Error general"
    const val ERROR_CONEXION = "Error de conexión"
    const val CODIGO_ERROR = "CodigoE"
    const val INFO_GUARDADA = "Información guardada."
    const val NOMBRE_COMPLETO = "nombreCompleto"
    const val ERROR_IO = "Error IO"
    const val PACIENTES = "Pacientes"
    const val GENERAR_CODIGO = "GenerarCodigo"

    const val AUTH_ERROR = "Error autenticación"
    const val SERVER_ERROR = "Error servidor"
    const val PARSE_ERROR = "Error de cadena"
    const val UNKNOWN_ERROR = "Error desconocido"

    // Diet
    const val ID_DIET = "idDieta"
    const val ID_PATIENT = "idPaciente"
    const val ID_DOCTOR = "idMedico"
    const val DESCRIPTION = "descripcion"
    const val AVAILABLE_FOODS = "alimentosDisponibles"
    const val ASSIGN_DATE = "fechaAsignacion"

    // Medical history
    const val ID_MEDICAL_HISTORY = "idHistorialClinico"
    const val WEIGHT = "peso"
    const val SIZE = "talla"
    const val DATE = "fecha"
    const val SUGAR = "azucar"

    // SugarRecording
    const val SUGAR_VALIDATION = "Falta azúcar"
    const val HOUR_VALIDATION = "Falta hora"
    const val DATE_VALIDATION = "Falta fecha"

    const val POSITION = "Position"

  }
}