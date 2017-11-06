package mx.escom.tt.diabetes.web.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.web.facade.MedicoFacade;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/medico/")
public class MedicoController {
	
	@Autowired MedicoFacade medicoFacade;

	@RequestMapping(value = "{idMedico}/pacientes/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> obtenerIndividuo(@PathVariable("idMedico") String idMedico) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		List<MedicoPacientesVo> medicoPacientes = null;
		RespuestaErrorVo respuestaError=null;
		
		log.debug("idMedico :" + idMedico);
		try {
			medicoPacientes =  medicoFacade.recuperarPacientesDeMedico(idMedico);
			
			result = new ResponseEntity<List<MedicoPacientesVo>>(medicoPacientes, HttpStatus.OK);
		} catch (Exception ex) {
			
			respuestaError = new RespuestaErrorVo();
			respuestaError.setRespuesta("ERROR");
			respuestaError.setMensaje(ex.getMessage());
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
}
