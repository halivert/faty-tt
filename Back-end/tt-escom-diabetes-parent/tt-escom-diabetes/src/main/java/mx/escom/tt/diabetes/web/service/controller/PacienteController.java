package mx.escom.tt.diabetes.web.service.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.web.facade.HistorialClinicoFacade;
import mx.escom.tt.diabetes.web.facade.PacienteFacade;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoListVo;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/pacientes")
public class PacienteController {
	
	@Autowired PacienteFacade pacienteFacade;
	@Autowired HistorialClinicoFacade historialClinicoFacade;

	
	/**
	 * 
	 * Proposito: Metodo para recuperar datos de un Paciente
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 17/10/2017
	 * @param idPaciente 					- Identificador del paciente
	 * @return ResponseEntity<UsuarioDto> 	- Objeto que contiene la informacion del usuario
	 */
	@RequestMapping(value = "/{idPaciente}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> buscarPacientePorId(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		PacienteDto pacienteDto = null;
		RespuestaVo respuestaError=null;
		
		try {
			pacienteDto = pacienteFacade.recuperarPaciente(idPaciente);	
			result = new ResponseEntity<PacienteDto>(pacienteDto, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaVo();
			respuestaError.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito :  Recuperar lista de historial clinico de un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPaciente
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/historialclinico", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarPaciente(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<HistorialClinicoListVo> listHistorialClinicoListVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			
			listHistorialClinicoListVo = historialClinicoFacade.recuperarListaHistorialClinicoPorPaciente(idPaciente);
			result = new ResponseEntity <List<HistorialClinicoListVo>>(listHistorialClinicoListVo,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito :  Recuperar un historial clinico por medio de su Identificador.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPaciente			-	Identificador del paciente.
	 * @param idHistorialClinico	-	Identificador del historial clinico a buscar.
	 * @return ResponseEntity		-	Respuesta de la solicitud
	 */
	@RequestMapping(value = "/{idPaciente}/historialclinico/{idHistorialClinico}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarHistorialClinicoPorId(@PathVariable("idPaciente") String idPaciente, @PathVariable("idHistorialClinico") String idHistorialClinico) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		HistorialClinicoDto historialClinicoDto = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			
			historialClinicoDto = historialClinicoFacade.recuperarHistorialClinicoPorId(idHistorialClinico);
			result = new ResponseEntity <HistorialClinicoDto>(historialClinicoDto,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Guardar un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoVo		-	Informacion del historial clinico que se quiere guardar.
	 * @return ResponseEntity<?>		-	Respuesta de la solicitud.
	 */
	@RequestMapping(value = "/historialclinico", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarHistorialClinico(@RequestBody HistorialClinicoVo historialClinicoVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaVo respuesta = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			log.debug("historialClinicoVo : " + historialClinicoVo);
			respuesta = historialClinicoFacade.guardarHistorialClinico(historialClinicoVo);
			result = new ResponseEntity <RespuestaVo>(respuesta,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}	
	
}
