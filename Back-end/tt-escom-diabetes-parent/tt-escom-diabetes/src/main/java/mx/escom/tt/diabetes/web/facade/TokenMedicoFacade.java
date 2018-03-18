package mx.escom.tt.diabetes.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.TokenMedicoAppService;

@Service
@CommonsLog
public class TokenMedicoFacade {
	
	@Autowired TokenMedicoAppService tokenMedicoAppService;
	
	/**
	 * Proposito : Generar un token y almacenarlo en la base de datos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param idMedicoStr				-	Identificador del medico	
	 * @return String					-	Token que se genero 
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public String generarToken(String idMedicoStr) throws RuntimeException{
		log.debug("Inicio - Facade");
	
		Integer idMedico = null;
		String token = null;
		
		if(idMedicoStr != null && !idMedicoStr.trim().equals("")) {
			idMedico = new Integer(idMedicoStr);
		}
	
		try {
			token = tokenMedicoAppService.generarTokenAppService(idMedico);
			if(token == null || token.isEmpty()) {
				throw new RuntimeException("No se pudo generar el código, intente nuevamente.");
			}
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		log.debug("Fin - Facade");
		return token;
	}
	
	/**
	 * Proposito : Enviar por coreo electronico el token de un medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/03/2018
	 * @param destinatario						-	Destinatario del correo electronico
	 * @param token								-	Token que se desea enviar
	 * @param nombreMedico						-	Nombre del medico
	 * @throws RuntimeException					-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void enviarTokenEmail(String[] destinatario, String token, String nombreMedico) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		if(destinatario == null || destinatario.length == 0) {
			throw new RuntimeException("El destinatario no puede ser nulo o vacío.");
		}
		if(destinatario == null || token.isEmpty()) {
			throw new RuntimeException("El código del médico no puede ser nulo o vacío.");
		}
		if(nombreMedico == null || nombreMedico.isEmpty()) {
			throw new RuntimeException("El nombre del médico no puede ser nulo o vacío.");
		}
		
		try {
			tokenMedicoAppService.enviarTokenEmailAppService(destinatario, token, nombreMedico);
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		
		log.debug("Fin - Facade");
	}
	
	

}
