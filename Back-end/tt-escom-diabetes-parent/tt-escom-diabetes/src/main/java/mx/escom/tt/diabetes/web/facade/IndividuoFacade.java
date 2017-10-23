package mx.escom.tt.diabetes.web.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.IndividuoAppService;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;
import mx.escom.tt.diabetes.web.vo.IndividuoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class IndividuoFacade {
	
	@Autowired IndividuoAppService individuoAppService;
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param email
	 * @param keyword
	 * @param fechaNacimiento
	 * @param idSexo
	 * @param rol
	 * @return
	 */
	public RespuestaVo guardarIndividuo(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String keyword, String fechaNacimiento, String idSexo, String rol) {	
		log.debug("Inicio - Facade");
		RespuestaVo result = null;
		IndividuoDto individuoDto = null;
		Date fechaNacimientoDate = null;
		Integer idSexoInteger = null;
		Integer rolInteger = null;
		String msjError = null;
		individuoDto = new IndividuoDto();
		
		//- TODO Validar que atributos deben ser obligatorios, y verificar que los campos cuales no deben ser alfanumericos
		if(nombre != null && !nombre.trim().equals("")) {
			individuoDto.setNombre(nombre.toUpperCase());
		}
		
		if(apellidoPaterno != null && !apellidoPaterno.trim().equals("")) {
			individuoDto.setApellidoPaterno(apellidoPaterno.toUpperCase());
		}
		if(apellidoMaterno != null && !apellidoMaterno.trim().equals("")) {
			individuoDto.setApellidoMaterno(apellidoMaterno.toUpperCase());
		}
		if(email != null && !email.trim().equals("")) {
			individuoDto.setEmail(email);
		}
		if(keyword != null && !keyword.trim().equals("")) {
			individuoDto.setKeyword(keyword);
		}
		//-TODO Dar formato a la fecha de nacimiento
		if(fechaNacimiento != null && !fechaNacimiento.trim().equals("")) {
		
			individuoDto.setFechaNacimiento(fechaNacimientoDate);
		}
		if(idSexo != null && !idSexo.trim().equals("")) {
			idSexoInteger = new Integer(idSexo);
			individuoDto.setIdSexo(idSexoInteger);
		}
		if(rol != null && !rol.trim().equals("")) {
			rolInteger = new Integer(rol);
			individuoDto.setRol(rolInteger);
		}
		try {
			individuoAppService.guardarInvididuo(individuoDto);
			
			result=new RespuestaVo();
			
			result.setMensaje("Información guardada.");
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al guardar la información.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuoStr
	 * @return
	 */
	public RespuestaVo borrarIndividuo(String idIndividuoStr) {
		log.debug("Inicio - Facade");
		
		RespuestaVo result = null;
		Integer idIndividuo = null;
		String msjError = null;
		
		if(idIndividuoStr == null || !idIndividuoStr.trim().equals("")) {
			idIndividuo = new Integer(idIndividuoStr);
		}
		
		
		try{
			individuoAppService.borrarIndividuo(idIndividuo);
			result=new RespuestaVo();
			result.setMensaje("Se borró el individuo.");
			
		}catch (Exception ex) {
			msjError="Ocurrió un error al borrar el individuo.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuoStr
	 * @return
	 * @throws RuntimeException
	 */
	public IndividuoDto obtenerIndividuo(String idIndividuoStr) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		Integer idIndividuo = null;
		IndividuoDto individuoDto = null;

		try{
			
			if(idIndividuoStr == null || idIndividuoStr.trim().equals("")) 
				throw new RuntimeException("El identificador no puede ser nulo.");
			
			idIndividuo = new Integer(idIndividuoStr);			
			individuoDto = individuoAppService.obtenerIndividuo(idIndividuo);
			
			if(individuoDto == null) 
				throw new RuntimeException("No se encontró información del paciente.");
			
		}catch (Exception ex) {
			log.error("ex.getMessage() : " + ex.getMessage(),ex);
			throw new RuntimeException(ex.getMessage());
		}
			
		log.debug("Fin - Facade");
		return individuoDto;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param email
	 * @param keyword
	 * @param fechaNacimiento
	 * @param idSexo
	 * @param rol
	 * @return
	 */
	public RespuestaVo actualizarIndividuo(String idIndividuo, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String keyword, String fechaNacimiento, String idSexo, String rol) {	
		log.debug("Inicio - Facade");
		RespuestaVo result = null;
		IndividuoDto individuoDto = null;
		Date fechaNacimientoDate = null;
		Integer idSexoInteger = null;
		Integer rolInteger = null;
		String msjError = null;
		individuoDto = new IndividuoDto();
		Integer idIndividuoInt = null;
		
		if(idIndividuo == null || idIndividuo.trim().equals("")) {
			msjError = "No se puede actualizar la información.";
			throw new RuntimeException(msjError);
		}
		
		idIndividuoInt = new Integer(idIndividuo);
		individuoDto = individuoAppService.obtenerIndividuo(idIndividuoInt);
		
		//- TODO Validar que atributos deben ser obligatorios, y verificar que los campos cuales no deben ser alfanumericos
		if(nombre != null && !nombre.trim().equals("")) {
			individuoDto.setNombre(nombre.toUpperCase());
		}
		
		if(apellidoPaterno != null && !apellidoPaterno.trim().equals("")) {
			individuoDto.setApellidoPaterno(apellidoPaterno.toUpperCase());
		}
		if(apellidoMaterno != null && !apellidoMaterno.trim().equals("")) {
			individuoDto.setApellidoMaterno(apellidoMaterno.toUpperCase());
		}
		if(email != null && !email.trim().equals("")) {
			individuoDto.setEmail(email);
		}
		if(keyword != null && !keyword.trim().equals("")) {
			individuoDto.setKeyword(keyword);
		}
		//-TODO Dar formato a la fecha de nacimiento
		if(fechaNacimiento != null && !fechaNacimiento.trim().equals("")) {
		
			individuoDto.setFechaNacimiento(fechaNacimientoDate);
		}
		if(idSexo != null && !idSexo.trim().equals("")) {
			idSexoInteger = new Integer(idSexo);
			individuoDto.setIdSexo(idSexoInteger);
		}
		if(rol != null && !rol.trim().equals("")) {
			rolInteger = new Integer(rol);
			individuoDto.setRol(rolInteger);
		}
		try {
			individuoAppService.actualizarIndividuo(individuoDto);
			
			result=new RespuestaVo();
			
			result.setMensaje("Información actualizada.");
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al guardar la información.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : Recuperar un Individuo con base en su email y contraseña
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 22/10/2017
	 * @param individuoVo
	 * @return RespuestaVo			- Respuesta si se encontro el individuo.	
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion.
	 */
	public RespuestaVo login(IndividuoVo individuoVo) throws RuntimeException{
		log.debug("Incio - Facade");
		RespuestaVo respuestaVo = null;
		IndividuoDto individuoDto = new IndividuoDto();
		IndividuoDto result = null;
		
		
		
		//Se fijan los parametros provenientes de la vista, en el IndividuoDto 
		individuoDto.setEmail(individuoVo.getEmail());
		individuoDto.setKeyword(individuoVo.getKeyword());
	
		try{
			result = individuoAppService.recuperarPorEmailYKeyword(individuoDto);
		
			if(result == null) {
				throw new RuntimeException("Email o contraseña incorrectos");
			}
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIndividuoRol(result.getRol().toString());
			respuestaVo.setMensaje("Inicio de sesión correcto");
		
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	
	

}
