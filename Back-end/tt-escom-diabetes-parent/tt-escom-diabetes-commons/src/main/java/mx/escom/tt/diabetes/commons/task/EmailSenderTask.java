package mx.escom.tt.diabetes.commons.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.InputStreamSource;

import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.EmailSenderHelper;

@CommonsLog
@Configurable
public class EmailSenderTask implements Runnable{
	
	public EmailSenderTask(EmailSenderHelper emailSenderHelper){
		this.emailSenderHelper = emailSenderHelper;
	}

	private EmailSenderHelper emailSenderHelper;
	
	@Setter private Map<String, Object> parametros;
	@Setter private String[] arrayPara;
	@Setter private String[] arrayConCopia;
	@Setter private String[] arrayConCopiaOculta;
	@Setter private String plantilla;
	@Setter private String asunto;
	@Setter private String correoRemitente;
	@Setter private Map<String, InputStreamSource> archivos;
	@Setter private Map<String, InputStreamSource> parametrosImagen;
	
	
	@Override
	public void run() {
		log.debug("Inicio");
		
		try{
			log.info("Inicio [Envio de correo]");
			log.debug("correoRemitente: " + correoRemitente);
			log.debug("arrayPara: " + arrayPara[0]);
			log.debug("plantilla: " + plantilla);
			
			emailSenderHelper.enviarMail(asunto, arrayPara, arrayConCopia, arrayConCopiaOculta, correoRemitente, parametros, parametrosImagen, plantilla, archivos);

			log.info("Fin [Envio de correo]");
		}catch(RuntimeException ex){
			log.info(ex.getMessage());
			throw ex;
		}catch(Exception ex){
			log.info(ex.getMessage());
			throw new RuntimeException(ex.getMessage(), ex);
		}
		
		log.debug("Fin");
	}

}