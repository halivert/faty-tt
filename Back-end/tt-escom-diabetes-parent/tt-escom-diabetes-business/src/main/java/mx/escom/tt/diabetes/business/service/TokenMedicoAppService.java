package mx.escom.tt.diabetes.business.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

@CommonsLog
@Service
public class TokenMedicoAppService {

	
	@Autowired TokenMedicoDao tokenMedicoDao;
	@Autowired EnvioCorreoAppService envioCorreoAppService; 
	
	/**
	 * Proposito : Generar un token 
	 * @author Edgar, ESCOM
	 * @version 1,1,0. 07/11/2017
	 * @param idMedico			-	Identificador del medico al que se le asociara el token.
	 * @return String			-	Token generado y guardado en la BD.
	 */
	public String generarTokenAppService(Integer idMedico) throws RuntimeException{
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
	
	/**
	 * Proposito : Eliminar un token de la base de datos 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param token							-	Token a eliminar
	 * @throws RuntimeException				- 	Si ocurre un error durante la ejecucion
	 */
	public void borrarTokenAppService(String token) throws RuntimeException{
		log.debug("Inicio Service");
		
		
		if(token == null || token.isEmpty()) {
			throw new RuntimeException();
		}
		
		tokenMedicoDao.borrarToken(token);
		
		log.debug("Fin - Service");

	}
	
	/**
	 * Proposito : Recuperar un objeto TokenMedicoDto de la base de datos, a partir del token 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param token							-	Token del que se quiere recuperar la informacion
	 * @return	TokenMedicoDto				-	Objeto con la informacion que se recupero
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion
	 */
	public TokenMedicoDto recuperarTokenAppService(String token) throws RuntimeException{
		log.debug("Inicio - Service");
		
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
	
	/**
	 * Proposito : Enviar un corre electronico con el token del medico 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/03/2018
	 * @param email							-	Lista de destinatarios que se les enviara el correo 
	 * @param token							-	Token del medico
	 * @param nombreMedico					-	Nombre del medico que envia el token 
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void enviarTokenEmailAppService(String[]  email, String token, String nombreMedico) throws RuntimeException{
		log.debug("Inicio - Service");
		Map<String, Object> parametros = null;
		String msjEx = null;
		
		parametros = new HashMap<String, Object>();
		parametros.put(Constants.PARAMETRO_CORREO_TOKEN, token);
		parametros.put(Constants.PARAMETRO_CORREO_NOMBRE_MEDICO, nombreMedico);
		
		try {
			envioCorreoAppService.enviarCorreoElectronico(Constants.PARAMETRO_CORREO_ENVIO_TOKEN_ASUNTO, email, null, null, Constants.PLANTILLA_ENVIO_TOKEN);			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
	}
}

