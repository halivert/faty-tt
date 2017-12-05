package mx.escom.tt.diabetes.business.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

@CommonsLog
@Service
public class TokenMedicoAppService {

	
	@Autowired TokenMedicoDao tokenMedicoDao;
	
	/**
	 * Proposito : Generar un token y guardarlo en la BD
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param idMedico			-	Identificador del medico al que se le asociara el token.
	 * @return String			-	Token generado y guardado en la BD.
	 */
	public String generarToken(Integer idMedico) throws RuntimeException{
		log.debug("Inicio Service");
		
		String token = null;
		TokenMedicoDto tokenMedicoDto = null;
		
		if(idMedico == null) {
			throw new RuntimeException();
		}
		
		token = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		
		tokenMedicoDto = new TokenMedicoDto();
		
		tokenMedicoDto.setIdMedico(idMedico);
		tokenMedicoDto.setToken(token);
		
		tokenMedicoDao.guardarToken(tokenMedicoDto);
		
		log.debug("Fin - Service");
		return token;
	}
	
	public void borrarToken(String token) throws RuntimeException{
		log.debug("Inicio Service");
		
		
		if(token == null || token.isEmpty()) {
			throw new RuntimeException();
		}
		
		tokenMedicoDao.borrarToken(token);
		
		log.debug("Fin - Service");

	}
	
	public TokenMedicoDto recuperarToken(String token) throws RuntimeException{
		log.debug("Inicio Service");
		
		TokenMedicoDto result = null;
		String msjEx = null;
		
		if(token == null || token.isEmpty()) {
			throw new RuntimeException();
		}
		
		result = tokenMedicoDao.recuperarToken(token);
		
		if(result == null) {
			msjEx = "El código del médico es incorrecto.";
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Service");
		return result;
	}
}

