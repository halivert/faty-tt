package mx.escom.tt.diabetes.business.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.UsuarioDao;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@CommonsLog
@Service
public class UsuarioAppService {

	@Autowired UsuarioDao usuarioDao;
	@Autowired MedicoAppService medicoAppService;
	@Autowired PacienteAppService pacienteAppService;
	@Autowired private CustomSecurity customSecurity;
	
	@Qualifier("FormatoFechaNacimiento")
	@Autowired private SimpleDateFormat dmyDateFormat;
	
	/**
	 * Proposito : Recuperar la informacion de un Usuario por medio de su identificador.
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 08/10/2017
	 * @param idUsuario				- Identificador del usuario.
	 * @return	UsuarioDto			- Objeto con la informacion del usuario.	
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion.
	 */
	public UsuarioDto recuperarUsuarioPorId(Integer idUsuario) throws RuntimeException{
		log.debug("Inicio - Service");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(idUsuario == null) {
				msjEx = "El identificador del usuario no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			
			usuarioDto = usuarioDao.recuperarUsuarioPorId(idUsuario);
			
			if(usuarioDto == null) {
				msjEx = "No se encontraron registros para el usuario con Id : " + idUsuario + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return usuarioDto;
	}
	
	/**
	 * Proposito : Recuperar un usuario de la BD por medio de su emial y keyword 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param email					-	Correo electronico del usuario.
	 * @param keyword				-	Contraseña del usuario.
	 * @return	UsuarioDto			-	Informacion del usuario, si existe.
	 * @throws RuntimeException		.	Si ocurre un error durante la ejecucion.
	 */
	public UsuarioDto recuperarPorEmailYKeyword(String email, String keyword) throws RuntimeException{
		log.debug("Inicio - Service");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(email == null || email.trim().isEmpty()) {
				msjEx = "Verficar correo electrónico.";
				throw new RuntimeException(msjEx);
			}
			if(keyword == null || keyword.trim().isEmpty()) {
				msjEx = "Contraseña incorrecta.";
				throw new RuntimeException(msjEx);
			}
		}
		//-TODO Hacer el cifrado de la contraseña
		
		usuarioDto = usuarioDao.recuperarPorEmailYKeyword(email, keyword);
		
		log.debug("Fin - Service");
		return usuarioDto;
	}
	
	/**
	 * Proposito : Valida un token de entrada y verifica si aun es valido
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param token						-	Token a decodificar
	 * @return	String					-	Retorna el email del usuario que solicicta la peticion
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo
	 */
	public String validaTokenReestablecerPassword(String token) throws RuntimeException{
		log.debug("Inicio - Service");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		String response = null;
		String emailUsuario = null;
		String [] tokenSplited = null;
		{//Validaciones 
			if(StringUtils.isEmpty(token)) {
				msjEx = "Ocurrió un problema al ejecutar la última petición.";
				throw new RuntimeException(msjEx);
			}
			
		}
		
		try{
			response = customSecurity.decodeSecurityToken(token);
			log.debug("response : " + response);
			if(!StringUtils.isEmpty(response)) {
				
				try{
					tokenSplited = response.split("-");
				}catch(Exception ex) {
					msjEx = Constants.MSJ_EXCEPTION + "ejecutar la última petición. Favor de intentarlo nuevamente";
					throw new RuntimeException(msjEx,ex);
				}
				
				Date date  = new Date();

				if(!tokenSplited[1].equals(dmyDateFormat.format(date))) {
					msjEx = "La página a la que intenta acceder caducó.";
					throw new RuntimeException(msjEx);
				}else {
					log.debug("tokenSplited[1] : " + tokenSplited[1]);
					emailUsuario = tokenSplited[0];
				}
				usuarioDto = usuarioDao.recuperarPorEmail(emailUsuario);
				if(usuarioDto == null) {
					msjEx = "No se pudo recuperar tu información.";
					throw new RuntimeException(msjEx);
				}
				emailUsuario = emailUsuario + "-" + usuarioDto.getIdUsuario();
				
			}
		}catch(RuntimeException rtExc) { 
			throw new RuntimeException(rtExc.getMessage());
		}catch (Exception ex) {
			log.debug("error");
			msjEx = Constants.MSJ_EXCEPTION + "ejecutar la última petición. Favor de intentarlo nuevamente";
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return emailUsuario;
	}
	
	/**
	 * Proposito : Recuperar un usuario de la bd por medio de su email
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param email						-	Email del usuario que se quiere recuperar
	 * @return	UsuarioDto				-	Informacion del usuario, si existe.
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion.
	 */
	public UsuarioDto recuperarPorEmail(String email) throws RuntimeException{
		log.debug("Inicio - Service");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(StringUtils.isEmpty(email)) {
				msjEx = "Verficar correo electrónico.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try{
			usuarioDto = usuarioDao.recuperarPorEmail(email);
			
			if(usuarioDto == null) {
				msjEx = "Verficar correo electrónico.";
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException ex) {
			log.debug("ERRORR :::: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar información del usuario.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		log.debug("Fin - Service");
		return usuarioDto;
	}
	
	public void reestablecerPasswordAppService(Integer idUsuario, String newKeyword) throws RuntimeException{
		log.debug("Inicio - Service");
		String msjEx = null;
		UsuarioDto usuarioDto = null;
		{//Validaciones 
			if(StringUtils.isEmpty(newKeyword)) {
				msjEx = "Ocurrió un error al guardar la nueva contraseña.";
				throw new RuntimeException(msjEx);
			}
			if(idUsuario == null) {
				msjEx = "El identificador del usuario no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try{
			usuarioDto = usuarioDao.recuperarUsuarioPorId(idUsuario);
			
			if(usuarioDto == null) {
				msjEx = "Ocurrió un error al recuperar la información del usuario.";
				throw new RuntimeException(msjEx);
			}
			usuarioDto.setKeyword(newKeyword);
			usuarioDao.reestablecerPassword(usuarioDto);
			
		}catch(RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al reestablecer la contraseña.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Guardar la informacion de un usuario en la BD, adicionalmente se guarda la informacion si es un medico o un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param usuarioDto			-	Dto con la informacion de un usuario.
	 * @throws RuntimeException		-	Si ocurre un error durante la ejecucion.
	 */
	public Integer guardarUsuario(UsuarioDto usuarioDto, MedicoDto medicoDto, PacienteDto pacienteDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		//Integer idUsuario = null;
		UsuarioDto result = null;
		//PacienteDto pacienteDto = null;
		if(usuarioDto == null) {
			throw new RuntimeException();
		}
		
		try {
			
			usuarioDao.guardarUsuario(usuarioDto);
			
			result = usuarioDao.recuperarPorEmailYKeyword(usuarioDto.getEmail(), usuarioDto.getKeyword());
			
			log.debug("medicoDto : " + medicoDto);
			log.debug("pacienteDto : " + pacienteDto);
			if(medicoDto != null) {
				medicoDto.setIdUsuario(result.getIdUsuario());
				medicoAppService.guardarMedico(medicoDto);
			}
			else if(pacienteDto != null) {
				pacienteDto.setIdUsuario(result.getIdUsuario());
				pacienteAppService.guardarPacienteAppService(pacienteDto);
			}
			
		}catch(RuntimeException ex) {
			log.debug("ERRORR :::: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el usuario. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		
		log.debug("Fin - Service");
		return result.getIdUsuario();
	}
	
}
