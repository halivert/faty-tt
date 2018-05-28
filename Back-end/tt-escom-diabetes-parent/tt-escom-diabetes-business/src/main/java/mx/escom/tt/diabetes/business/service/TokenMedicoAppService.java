package mx.escom.tt.diabetes.business.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dao.UsuarioDao;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@CommonsLog
@Service
public class TokenMedicoAppService {

	
	@Autowired TokenMedicoDao tokenMedicoDao;
	@Autowired EnvioCorreoAppService envioCorreoAppService; 
	@Autowired UsuarioAppService usuarioAppService; 
	@Autowired UsuarioDao usuarioDao;
	@Autowired private CustomSecurity customSecurity;
	
	@Autowired
	@Value("${url.token}") 
	private String host;
	
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
	public void enviarTokenEmailAppService(String[]  email, String token, String nombreMedico,  String remitente) throws RuntimeException{
		log.debug("Inicio - Service");
		Map<String, Object> parametros = null;
		String msjEx = null;
		
		parametros = new HashMap<String, Object>();
		parametros.put(Constants.PARAMETRO_CORREO_TOKEN, token);
		parametros.put(Constants.PARAMETRO_CORREO_NOMBRE_MEDICO, nombreMedico);
		UsuarioDto usuarioDto = null;
		
		try {
			usuarioDto = usuarioDao.recuperarPorEmail(email[0]);
			if(usuarioDto == null) {
			envioCorreoAppService.enviarCorreoElectronico(Constants.PARAMETRO_CORREO_ENVIO_TOKEN_ASUNTO, email, parametros, remitente, Constants.PLANTILLA_ENVIO_TOKEN);			
			}else {
				UsuarioDto medicoDto = null;
				TokenMedicoDto tokenMedicoDto = tokenMedicoDao.recuperarToken(token);
				
				
				medicoDto = usuarioAppService.recuperarUsuarioPorId(tokenMedicoDto.getIdMedico());
				parametros.put(Constants.PARAMETRO_CORREO_NOMBRE_MEDICO, medicoDto.getNombre() + " " + medicoDto.getApellidoPaterno());
				parametros.put(Constants.PARAMETRO_CORREO_NOMBRE_USUARIO, usuarioDto.getNombre());
				
				envioCorreoAppService.enviarCorreoElectronico(Constants.PARAMETRO_CAMBIAR_MEDICO, email, parametros, remitente, Constants.PLANTILLA_ENVIO_CAMBIAR_MEDICO);
			}

		
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
	}
	
	/**
	 * 
	 * Proposito : Crear los datos necesarios que seran enviados en la plantilla de correo para reestablecer un password
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param email							-	Email del usario que quiere reestablecer su password 
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void prepareDataForToken(String[] email) throws RuntimeException{
		log.debug("Inicio - Service");
		String msjEx = null;
		UsuarioDto usuarioDto = null;
		//String urlToken = "http://35.202.245.109/ceres/#/session/reestablecePassword/";
		String urlToken = host;
		String nombreUsuario = "";
		String remitente = "ceres.ttdietas@gmail.com";
		try {
			usuarioDto = usuarioAppService.recuperarPorEmail(email[0]);
			nombreUsuario = usuarioDto.getNombre();
			urlToken = urlToken + customSecurity.createSecurityToken(email[0]);
			this.enviarCorreoReestablecerPassword(email, urlToken, nombreUsuario, remitente);
		
		}catch(RuntimeException rtExc) { 
			throw new RuntimeException(rtExc.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Enviar un correo para reestablecer un password 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param email							-	Email del usario que quiere reestablecer su password 
	 * @param urlToken						-	URL con el token codificado 
	 * @param nombreUsuario					-	Nombre del usuario, se agrega como parametro 
	 * @param remitente						-	Remitente que envia el correo
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void enviarCorreoReestablecerPassword(String[]  email, String urlToken, String nombreUsuario,  String remitente) throws RuntimeException{
		log.debug("Inicio - Service");
		Map<String, Object> parametros = null;
		String msjEx = null;
		
		parametros = new HashMap<String, Object>();
		parametros.put(Constants.PARAMETRO_URL_TOKEN, urlToken);
		parametros.put(Constants.PARAMETRO_CORREO_NOMBRE_USUARIO, nombreUsuario);
		
		try {
			envioCorreoAppService.enviarCorreoElectronico(Constants.PARAMETRO_RECUPERAR_PASSWORD_ASUNTO, email, parametros, remitente, Constants.PLANTILLA_RECUPERAR_PASSWORD);			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "enviar el correo electrónico" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
	}
}

