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
@RequestMapping(value = "/ceres/service")
public class UsuarioController {
	
	@Autowired UsuarioFacade usuarioFacade;
	@Autowired PacienteFacade pacienteFacade;

	/**
	 * 
	 * Proposito: Metodo para recuperar datos de un Usuario
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 12/10/2017
	 * @param idUsuario 					- Identificador del usuario
	 * @return ResponseEntity<UsuarioDto> 	- Objeto que contiene la informacion del usuario
	 */
	@RequestMapping(value = "/usuarios/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> buscarUsuarioPorId(@RequestParam (value="idUsuario", required=true) String idUsuario) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		UsuarioDto usuarioDto = null;
		try {
			usuarioDto = usuarioFacade.recuperarUsuarioPorIdFacade(idUsuario);
			result = new ResponseEntity<UsuarioDto>(usuarioDto, HttpStatus.OK);
			
		} catch (Exception ex) {
			log.error("ex.getMessage(): " + ex.getMessage());
			log.error(ex.getMessage());
			result = new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
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
		try {
			pacienteDto = pacienteFacade.recuperarPaciente(idPaciente);
			result = new ResponseEntity<PacienteDto>(pacienteDto, HttpStatus.OK);
			
		} catch (Exception ex) {
			log.error("ex.getMessage(): " + ex.getMessage());
			log.error(ex.getMessage());
			result = new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
}
