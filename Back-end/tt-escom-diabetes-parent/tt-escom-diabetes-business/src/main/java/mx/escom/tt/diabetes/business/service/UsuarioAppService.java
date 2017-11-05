package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * Proposito : Guardar la informacion de un usuario en la BD.
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
				pacienteAppService.guardarPaciente(pacienteDto);
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
