package mx.escom.tt.diabetes.business.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.task.EmailSenderTask;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.utils.EmailSenderHelper;

@Service("EnvioCorreoAppService")
@CommonsLog
public class EnvioCorreoAppService {
	
	@Autowired private EmailSenderHelper emailSenderHelper;
	//Beans para envio de correo electronico 
	//@Resource(name="TaskExecutorEnvioCorreo") private TaskExecutor taskExecutorEnvioCorreo;
	
	@Value("${mail.from}") private String correoRemitente;
	
	
	/**
	 * Proposito : Enviar un correo electronico 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @param asunto					-	Asunto que debe tener el correo a enviar
	 * @param arrayPara					-	Destinatario del correo 
	 * @param mensaje					-	Cuerpo del correo 
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public void enviarCorreoElectronico(String asunto, String[] arrayPara, String mensaje) throws RuntimeException{
		log.debug("Inicio - Service");

		String msjError = null;
		
		//Variables para el envio de correo electronico
		EmailSenderTask emailSenderTask = null;
		Map<String, Object> parametros = null;
		String plantilla = null;
		
		{//Validaciones 
			if(asunto == null || asunto.trim().isEmpty()){
				msjError = "El asunto del correo no debe ser nulo o vacío.";
				throw new RuntimeException(msjError);
			}
			if(arrayPara == null || arrayPara.length == 0){
				msjError = "El listado de correos 'para' no debe ser nulo o vacío.";
				throw new RuntimeException(msjError);
			}
		}
		
		try {
			parametros = new HashMap<String, Object>();
			parametros.put(Constants.PARAMETRO_CORREO_MENSAJE, mensaje);
			
			plantilla = "mx/escom/tt/diabetes/commons/plantillaMail/plantilla_envio_correo.html";

			//TODO Implementar envio de correo con un task executor
//			{//Se arma la tarea de envio de correo electronico
//				emailSenderTask = new EmailSenderTask(emailSenderHelper);
//				emailSenderTask.setArrayPara(arrayPara);
//				//emailSenderTask.setArrayConCopia(arrayConCopia);
//				//emailSenderTask.setArrayConCopiaOculta(arrayConCopiaOculta);
//				emailSenderTask.setAsunto(asunto);
//				emailSenderTask.setPlantilla(plantilla);
//				emailSenderTask.setParametros(parametros);
//				emailSenderTask.setCorreoRemitente(correoRemitente);
//			}
			//taskExecutorEnvioCorreo.execute(emailSenderTask);
			
			emailSenderHelper.enviarMail(asunto, arrayPara, null, null, correoRemitente, parametros, null, plantilla, null);
			
		}catch(RuntimeException ex){
			throw ex;
		}catch(Exception ex){
			msjError = Constants.MSJ_EXCEPTION + " enviar el correo.";
			throw new RuntimeException(msjError);
		}
		log.debug("Fin - Service");
	}

}
