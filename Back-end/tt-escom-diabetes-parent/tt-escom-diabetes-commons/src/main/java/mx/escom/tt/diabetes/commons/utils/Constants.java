package mx.escom.tt.diabetes.commons.utils;

public interface Constants {
	
	//GENERAL
	public static final String MSJ_EXCEPTION = "Ocurrió un error inesperado al: ";

	
	//SALTO DE LINEA
    public static final String SALTO_LINEA = " \n";
    public static final String CADENA_BLANCO = " ";
    
    public static final String CADENA_VACIA = "";
    
    public static final String ID_ROL_MEDICO = "1";
    public static final String ID_ROL_PACIENTE = "0";
    
    //MENSAJES PARA CORREO ELECTRONICO
    public static final String PARAMETRO_CORREO_MENSAJE = "MENSAJE";
    public static final String PARAMETRO_CORREO_TOKEN = "CODIGO";
    public static final String PARAMETRO_CORREO_NOMBRE_MEDICO = "NOMBRE_MEDICO";
    public static final String PARAMETRO_CORREO_ENVIO_TOKEN_ASUNTO = "Código de registro CERES";
    public static final String PARAMETRO_CORREO_NOMBRE_USUARIO = "NOMBRE_USUARIO";
    public static final String PARAMETRO_URL_TOKEN = "URL_HOST_TOKEN";
    public static final String PARAMETRO_RECUPERAR_PASSWORD_ASUNTO = "Reestablecer contraseña";
    public static final String PARAMETRO_CAMBIAR_MEDICO = "Cambiar médico";
    
    //PLANTILLAS HTML
    public static final String PLANTILLA_ENVIO_TOKEN = "mx/escom/tt/diabetes/commons/plantillaMail/plantilla_envio_codigo.html";
    public static final String PLANTILLA_ENVIO_CAMBIAR_MEDICO = "mx/escom/tt/diabetes/commons/plantillaMail/plantilla_cambiar_medico.html";
    public static final String PLANTILLA_RECUPERAR_PASSWORD = "mx/escom/tt/diabetes/commons/plantillaMail/plantilla_recuperar_password.html";

    //PLANTILLAS JASPER
    	public static final String PLANTILLA_JASPER_DIETA = "caratula_dieta.jasper";
    	
    	//MENSAJE ERROR FORMATO
    	public static final String ERROR_FORMATO_NUMERICO = " no puede tener letras o carácteres especiales";
    	public static final String ERROR_FORMATO_ALFANUMERICO = " no puede ser alfanumérico.";
    	public static final String ERROR_FORMATO_NULO_VACIO = " no puede se nulo o vacío.";
    
}
