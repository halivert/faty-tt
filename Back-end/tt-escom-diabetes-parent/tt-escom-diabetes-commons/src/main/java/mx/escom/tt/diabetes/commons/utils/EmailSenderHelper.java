package mx.escom.tt.diabetes.commons.utils;

import java.util.Map;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;


@CommonsLog
@Service
public class EmailSenderHelper {
	
	@Resource(name="Correo") private JavaMailSender javaMailSender;
	//@Autowired JavaMailSenderImpl javaMailSender;
	@Resource(name="VelocityEngine") private VelocityEngine velocityEngine;
	
		
	/**
	 * Metodo para enviar un correo electronico con archivos adjuntos
	 *
	 * @author edgar.hurtado
	 * @version 1.0, 04/03/2018
	 * @param asunto Asunto del correo electronico 
	 * @param arrayPara Arreglo de destinatarios
	 * @param arrayConCopia Arreglo de destinarios (copia) 
	 * @param arrayConCopiaOculta Arreglo de destinatrarios (copia oculta) 
	 * @param from Correo del remitente
	 * @param parametros Mapa de parametros del correo electronico
	 * @param parametrosImagen Mapa de parametros con las imagenes
	 * @param plantilla Plantilla del correo electronico
	 * @throws RuntimeException Si ocurre un error en tiempo de ejecucion
	 */
	public void enviarMail(final String asunto, final String [] arrayPara, final String [] arrayConCopia, final String [] arrayConCopiaOculta, final String from, 
							final Map<String, Object> parametros, final Map<String, InputStreamSource> parametrosImagen, final String plantilla) throws RuntimeException{
		log.debug("Inicio");
		
		String msjEx = null;
		
		try{
			
			this.enviarMail(asunto, arrayPara, arrayConCopia, arrayConCopiaOculta, from, parametros, parametrosImagen, plantilla, null);
			
		}catch(RuntimeException ex){
			throw ex;
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico.";
			throw new RuntimeException(msjEx, ex);
		}
		
		log.debug("Fin");
	}
	
	
	/**
	 * Metodo para enviar un correo electronico con archivos adjuntos
	 *
	 * @author edgar.hurtado
	 * @version 1.0, 04/03/2018
	 * @param asunto Asunto del correo electronico 
	 * @param arrayPara Arreglo de destinatarios
	 * @param arrayConCopia Arreglo de destinarios (copia) 
	 * @param arrayConCopiaOculta Arreglo de destinatrarios (copia oculta) 
	 * @param from Correo del remitente
	 * @param parametros Mapa de parametros del correo electronico
	 * @param parametrosImagen Mapa de parametros con las imagenes
	 * @param plantilla Plantilla del correo electronico
	 * @param archivos Mapa de archivos a adjuntar
	 * @throws RuntimeException Si ocurre un error en tiempo de ejecucion
	 */
	public void enviarMail(final String asunto, final String [] arrayPara, final String [] arrayConCopia, final String [] arrayConCopiaOculta, final String from, 
							final Map<String, Object> parametros, final Map<String, InputStreamSource > parametrosImagen, final String plantilla, 
							final Map<String, InputStreamSource> archivos) throws RuntimeException{
		log.debug("Inicio");
	
		String msjEx = "";
		
		{//Validaciones
			if(asunto == null || Constants.CADENA_VACIA.equals(asunto.trim())){
				msjEx = "El asunto del correo no debe ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
			if(arrayPara == null || arrayPara.length == 0){
				msjEx = "El arreglo con los correos de los destinatarios no debe ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
			if(from == null || Constants.CADENA_VACIA.equals(from.trim())){
				msjEx  = "El remitente no debe ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
			if(plantilla == null || Constants.CADENA_VACIA.equals(plantilla.trim())){
				msjEx = "La ruta de la plantilla no debe ser nula o vacía.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try{
			log.debug("before MimeMessagePreparator");
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				public void prepare(MimeMessage mimeMessage) throws Exception {
					log.debug("Inicio");
					
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
					log.debug("MimeMessageHelper");
					for(int c = 0; c < arrayPara.length; c++){
						
						if(arrayPara[c].contains(Constants.CADENA_BLANCO)){
							arrayPara[c] = "\"" + arrayPara[c] + "\"";
						}else{
							arrayPara[c] = arrayPara[c];
						}
						
					}
					
					for (int i = 0; i < arrayPara.length; i++) {
						log.debug("arrayPara:: "+arrayPara[i]);
					}
					
					log.debug("from:: "+from);
					log.debug("asunto:: "+asunto);
					
					message.setTo(arrayPara);
					message.setFrom(from);
					message.setSubject(asunto);
					
					if(arrayConCopia != null && arrayConCopia.length != 0){
						
						for(int c = 0; c < arrayConCopia.length; c++){

							if(arrayConCopia[c].contains(Constants.CADENA_BLANCO)){
								arrayConCopia[c] = "\"" + arrayConCopia[c] + "\"";
							}else{
								arrayConCopia[c] = arrayConCopia[c];
							}
							
						}
						
						message.setCc(arrayConCopia);
						
					}
					
					if(arrayConCopiaOculta != null && arrayConCopiaOculta.length != 0){
						
						for(int c = 0; c < arrayConCopiaOculta.length; c++){

							if(arrayConCopiaOculta[c].contains(Constants.CADENA_BLANCO)){
								arrayConCopiaOculta[c] = "\"" + arrayConCopiaOculta[c] + "\"";
							}else{
								arrayConCopiaOculta[c] = arrayConCopiaOculta[c];
							}
							
						}
						
						message.setBcc(arrayConCopiaOculta);
					}
				
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, plantilla, "UTF-8", parametros);
					//log.debug("Texto a enviar: " + text);
					
					message.setText(text, true);
					
					{//Se incluyen las imagenes
						if(parametrosImagen != null && !parametrosImagen.isEmpty()){
							for (Map.Entry<String, InputStreamSource> parametro : parametrosImagen.entrySet()){
								message.addInline(parametro.getKey(), parametro.getValue(), "image/png");
							}
						}
					}
					
					{//Se adjuntan los archivos
						
						if(archivos != null && !archivos.isEmpty()){
							
							for (Map.Entry<String, InputStreamSource> archivo : archivos.entrySet()){
								
								message.addAttachment(archivo.getKey(), archivo.getValue());
							    
							}
						}
					}
					log.debug("Fin");
				}
			};
			log.debug("javaMailSender.send(preparator);");
			javaMailSender.send(preparator);
			log.debug("javaMailSender.send(preparator);");
		}catch (MailException ex) {
			log.debug(ex.getMessage());
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico.";
			throw new RuntimeException(msjEx, ex);
		}catch(Exception ex){
			log.debug(ex.getMessage());
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico.";
			throw new RuntimeException(msjEx, ex);
		}
		
		log.debug("Fin");
	}
	
}
