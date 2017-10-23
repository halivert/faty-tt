package mx.escom.tt.diabetes.web.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;
import mx.escom.tt.diabetes.web.facade.IndividuoFacade;
import mx.escom.tt.diabetes.web.vo.IndividuoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@CommonsLog
@RestController
@RequestMapping(value = "/sesion/")
public class IndividuoController {
	
	@Autowired IndividuoFacade individuoFacade;
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @return
	 */
	@RequestMapping(value = "/individuos/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> obtenerIndividuo(@RequestParam (value="idIndividuo", required=true) String idIndividuo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		IndividuoDto individuoDto = null;
		RespuestaVo respuestaError=null;
		
		try {
			individuoDto = individuoFacade.obtenerIndividuo(idIndividuo);
			result = new ResponseEntity<IndividuoDto>(individuoDto, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError=new RespuestaVo();
			respuestaError.setMensaje(ex.getMessage());
			log.debug("respuestaError.getMensaje() : " + respuestaError.getMensaje());
			result=new ResponseEntity<RespuestaVo>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 22/10/2017
	 * @param individuoVo
	 * @return
	 */
	@RequestMapping(value = "/login/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> autenticar(@RequestBody IndividuoVo individuoVo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
	
		RespuestaVo respuestaVo = null;
		
		try {
			respuestaVo = individuoFacade.login(individuoVo);
			
			result = new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaVo = new RespuestaVo();
			respuestaVo.setMensaje(ex.getMessage());
			
			result=new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
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
	@RequestMapping(value = "/individuos/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarPaciente(@RequestParam(value="nombre", required=true) String nombre,
											 @RequestParam(value="apellidoPaterno", required=false) String apellidoPaterno,
											 @RequestParam(value="apellidoMaterno", required=false) String apellidoMaterno,
											 @RequestParam(value="email", required=false) String email,
											 @RequestParam(value="keyword", required=false) String keyword,
											 @RequestParam(value="fechaNacimiento", required=false) String fechaNacimiento,
											 @RequestParam(value="idSexo", required=false) String idSexo,
											 @RequestParam(value="rol", required=false) String rol) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaVo respuesta=null;

		try {
			
			respuesta = individuoFacade.guardarIndividuo(nombre, apellidoPaterno, apellidoMaterno, email, keyword, fechaNacimiento, idSexo, rol);
			result=new ResponseEntity<RespuestaVo>(respuesta,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuesta=new RespuestaVo();
			respuesta.setMensaje(ex.getMessage());
			log.debug("respuestaError.getMensaje() : " + respuesta.getMensaje());
			result=new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @return
	 */
	@RequestMapping(value = "/individuos/", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> borrarIndividuo(@RequestParam (value="idIndividuo", required=true) String idIndividuo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaVo respuesta=null;
		
		try {
			respuesta = individuoFacade.borrarIndividuo(idIndividuo);
			result = new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.OK);
		} catch (Exception ex) {
			respuesta=new RespuestaVo();
			respuesta.setMensaje(ex.getMessage());
			log.debug("respuestaError.getMensaje() : " + respuesta.getMensaje());
			result=new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 22/10/2017
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
	@RequestMapping(value = "/individuos/", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> actualizarIndividuo(@RequestParam(value="idIndividuo", required=true) String idIndividuo,
											 @RequestParam(value="nombre", required=true) String nombre,
											 @RequestParam(value="apellidoPaterno", required=false) String apellidoPaterno,
											 @RequestParam(value="apellidoMaterno", required=false) String apellidoMaterno,
											 @RequestParam(value="email", required=false) String email,
											 @RequestParam(value="keyword", required=false) String keyword,
											 @RequestParam(value="fechaNacimiento", required=false) String fechaNacimiento,
											 @RequestParam(value="idSexo", required=false) String idSexo,
											 @RequestParam(value="rol", required=false) String rol) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaVo respuesta=null;

		try {
			respuesta = individuoFacade.actualizarIndividuo(idIndividuo, nombre, apellidoPaterno, apellidoMaterno, email, keyword, fechaNacimiento, idSexo, rol);
			result=new ResponseEntity<RespuestaVo>(respuesta,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuesta=new RespuestaVo();
			respuesta.setMensaje(ex.getMessage());
			log.debug("respuestaError.getMensaje() : " + respuesta.getMensaje());
			result=new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}

}
