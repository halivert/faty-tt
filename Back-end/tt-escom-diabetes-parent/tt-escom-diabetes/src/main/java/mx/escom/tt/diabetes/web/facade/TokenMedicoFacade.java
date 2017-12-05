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
	 * @param idMedicoStr
	 * @return
	 * @throws RuntimeException
	 */
	public String generarToken(String idMedicoStr) throws RuntimeException{
		log.debug("Inicio - Facade");
	
		Integer idMedico = null;
		String token = null;
		
		if(idMedicoStr != null && !idMedicoStr.trim().equals("")) {
			idMedico = new Integer(idMedicoStr);
		}
	
		try {
			token = tokenMedicoAppService.generarToken(idMedico);
			
			if(token == null || token.isEmpty()) {
				throw new RuntimeException("No se pudo generar el código, intente nuevamente.");
			}
			
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		log.debug("Fin - Facade");
		return token;
		
	}

}
