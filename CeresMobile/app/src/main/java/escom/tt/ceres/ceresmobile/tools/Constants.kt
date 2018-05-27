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
    const val CHANNEL_ID = "Ceres_notification_channel"
    const val AZUCAR = "azucar"
    const val DIETA = "Dieta"
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val GLUCOSA = "Glucosa"
    const val INICIO = "Inicio"
    const val FECHA_REGISTRO = "fechaRegistro"
    const val APELLIDO_PATERNO = "apellidoPaterno"
    const val APELLIDO_MATERNO = "apellidoMaterno"
    const val EMAIL = "email"
    const val KEYWORD = "keyword"
    const val FECHA_NACIMIENTO = "fechaNacimiento"
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
    const val TOKEN_LOWER = "token"
    const val VOID = "VOID"

    private const val URL_SERVER = "http://35.202.245.109"
    const val URL_REGISTER = "$URL_SERVER/tt-escom-diabetes/session/usuarios"
    const val URL_LOGIN = "$URL_SERVER/tt-escom-diabetes/session/login"
    const val URL_DATOS = "$URL_SERVER/tt-escom-diabetes/ceres/usuarios/"
    const val URL_PATIENT = "$URL_SERVER/tt-escom-diabetes/ceres/pacientes"
    const val URL_MEDICO = "$URL_SERVER/tt-escom-diabetes/ceres/medico"

    const val USUARIO = "Usuario"
    const val USER_JSON = "userJSON"
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
    const val ID_DIET_2 = "id_DIETA"
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
    const val PROTEINS = "proteinas"
    const val HEIGHT = "estatura"
    const val IMC = "imc"
    const val FULL_NAME = "nombreComppleto"
    const val PHYSICAL_ACTIVITY = "actividadFisica"
    const val HISTORY_DATE = "fechaHistorial"

    // SugarRecording
    const val SUGAR_VALIDATION = "Falta azúcar"
    const val HOUR_VALIDATION = "Falta hora"
    const val DATE_VALIDATION = "Falta fecha"
    const val POSITION = "Position"

    // Diet detail
    const val BREAKFAST = "Desayuno"
    const val COLLATION_1 = "C1"
    const val MEAL = "Comida"
    const val COLLATION_2 = "C2"
    const val DINNER = "Cena"

    // Food
    const val ID_FOOD = "idAlimento"
    const val FOOD_TYPE = "tipoAlimento"
    const val FOOD = "alimento"
    const val QUANTITY = "cantidad"
    const val UNIT = "unidad"
    const val WEIGHT_BRG = "pesoBgr"
    const val WEIGHT_NGR = "pesoNgr"
    const val ENERGY = "energia"
    const val PROTEIN = "proteina"
    const val LIPIDS = "lipidos"
    const val CARBOHYDRATES = "carbohidratos"
    const val FIBER_GR = "fibraGr"
    const val VITAMIN_A = "vitaminaA"
    const val ASCORBIC_ACID = "acidoAscorbico"
    const val FOLIC_ACID = "acidoFolico"
    const val IRON_NO = "hierroNo"
    const val POTASSIUM = "potasio"
    const val SUGAR_GR = "azucarGr"
    const val CHARGE_GL = "cargaGl"
    const val SUGAR_PE = "azucarPe"
    const val CHOLESTEROL = "colesterol"
    const val AG_SATURATED = "agSaturados"
    const val AG_M_SATURATED = "agMsaturados"
    const val AG_P_SATURATED = "agPsaturados"
    const val CALCIUM = "calcio"
    const val SELENIUM = "selenio"
    const val PHOSPHOR = "fosforo"
    const val POTASSIUM_P = "potasioP"
    const val IRON = "hierro"
    const val SODIUM = "sodio"

    // Patient
    const val ID_USER = "id_USUARIO"
    const val NAME = "nombre"
    const val SEX = "sexo"
    const val AGE = "edad"
    const val AP_PAT = "ap_PAT"
    const val AP_MAT = "ap_MAT"
    const val FEC_NAC = "fec_NAC"

    // Medic code
    const val RECEIVER = "destinatario"
    const val MEDIC_NAME = "nombreMedico"
    const val MEDIC_CODE = "codigoMedico"
    const val SENDER = "remitente"

    // Medic
    const val PROFESSIONAL_LICENSE = "cedulaProfesional"

    // User
    const val ID_ROLE = "idRol"

    // Response
    const val MESSAGE = "mensaje"
    const val RESPONSE = "respuesta"

    const val FRAGMENT = "fragment"
    const val DIETS = "diets"
    const val SUCCESSFUL = "successful"
    const val UNSUCCESSFUL = "unsuccessful"

    const val RETAIN = "retain"
  }
}