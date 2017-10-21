package mx.escom.tt.diabetes.web.service.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import mx.escom.tt.diabetes.web.facade.PacienteFacade;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
//import mx.escom.tt.diabetes.model.dto.UsuarioDto;
//import mx.escom.tt.diabetes.web.facade.UsuarioFacade;


/* REVISAR LAS ANOTACIONES
 * @RequestBody
 * @PathVariable
 * @RequestParam(
 * 
 */
@CommonsLog
@RestController
@RequestMapping(value = "/ceres/")
public class PacienteController {
	
	@Autowired UsuarioFacade usuarioFacade;
	@Autowired PacienteFacade pacienteFacade;

	
	/**
	 * 
	 * Proposito: Metodo para recuperar datos de un Paciente
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 17/10/2017
	 * @param idPaciente 					- Identificador del paciente
	 * @return ResponseEntity<UsuarioDto> 	- Objeto que contiene la informacion del usuario
	 */
	@RequestMapping(value = "/pacientes/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> buscarPacientePorId(@RequestParam (value="idPaciente", required=true) String idPaciente) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		PacienteDto pacienteDto = null;
		RespuestaVo respuestaError=null;
		
		try {
			pacienteDto = pacienteFacade.recuperarPaciente(idPaciente);	
			result = new ResponseEntity<PacienteDto>(pacienteDto, HttpStatus.OK);
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
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @param idMedico
	 * @param peso
	 * @param talla
	 * @param estatura
	 * @param imc
	 * @param lipidos
	 * @param carbohidratos
	 * @param proteinas
	 * @return
	 */
	@RequestMapping(value = "/pacientes/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarPaciente(@RequestParam(value="idIndividuo", required=true) String idIndividuo,
											 @RequestParam(value="idMedico", required=false) String idMedico,
											 @RequestParam(value="peso", required=false) String peso,
											 @RequestParam(value="talla", required=false) String talla,
											 @RequestParam(value="estatura", required=false) String estatura,
											 @RequestParam(value="imc", required=false) String imc,
											 @RequestParam(value="lipidos", required=false) String lipidos,
											 @RequestParam(value="carbohidratos", required=false) String carbohidratos,
											 @RequestParam(value="proteinas", required=false) String proteinas) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaVo respuesta=null;

		try {
			log.debug("idMedico : " + idMedico);
			respuesta = pacienteFacade.guardarPaciente(idIndividuo, idMedico, peso, talla, estatura, imc, lipidos, carbohidratos, proteinas);
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
	 * @param idPaciente
	 * @return
	 */
	@RequestMapping(value = "/pacientes/", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> eliminarPaciente(@RequestParam (value="idPaciente", required=true) String idPaciente) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		PacienteDto pacienteDto = null;
		RespuestaVo respuesta=null;
		
		try {
			respuesta = pacienteFacade.eliminarPaciente(idPaciente);	
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
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @param idMedico
	 * @param peso
	 * @param talla
	 * @param estatura
	 * @param imc
	 * @param lipidos
	 * @param carbohidratos
	 * @param proteinas
	 * @return
	 */
	@RequestMapping(value = "/pacientes/", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> actualizarPaciente(@RequestParam(value="idPaciente", required=true) String idPaciente,
											 @RequestParam(value="idIndividuo", required=true) String idIndividuo,
											 @RequestParam(value="idMedico", required=false) String idMedico,
											 @RequestParam(value="peso", required=false) String peso,
											 @RequestParam(value="talla", required=false) String talla,
											 @RequestParam(value="estatura", required=false) String estatura,
											 @RequestParam(value="imc", required=false) String imc,
											 @RequestParam(value="lipidos", required=false) String lipidos,
											 @RequestParam(value="carbohidratos", required=false) String carbohidratos,
											 @RequestParam(value="proteinas", required=false) String proteinas) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaVo respuesta=null;

		try {
			log.debug("idMedico : " + idMedico);
			respuesta = pacienteFacade.actualizarInformacionPaciente(idPaciente, idIndividuo, idMedico, peso, talla, estatura, imc, lipidos, carbohidratos, proteinas);
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
