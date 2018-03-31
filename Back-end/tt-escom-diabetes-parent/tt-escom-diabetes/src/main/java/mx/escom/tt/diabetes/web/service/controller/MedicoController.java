package mx.escom.tt.diabetes.web.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.web.facade.DietaFacade;
import mx.escom.tt.diabetes.web.facade.MedicoFacade;
import mx.escom.tt.diabetes.web.facade.TokenMedicoFacade;
import mx.escom.tt.diabetes.web.vo.CorreoTokenVo;
import mx.escom.tt.diabetes.web.vo.DietaVo;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/medico/")
public class MedicoController {
	
	@Autowired MedicoFacade medicoFacade;
	@Autowired TokenMedicoFacade tokenMedicoFacade;
	@Autowired DietaFacade dietaFacade;

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
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "{idMedico}/token/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> generarToken(@PathVariable("idMedico") String idMedico) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		String token = null;
		RespuestaErrorVo respuestaError=null;
		RespuestaErrorVo respuestaToken=null;
		
		log.debug("idMedico :" + idMedico);
		try {
			token = tokenMedicoFacade.generarToken(idMedico);
			
			//Se reutiliza la clase RespuestaErrorVo
			respuestaToken = new RespuestaErrorVo();
			
			respuestaToken.setRespuesta("TOKEN");
			respuestaToken.setMensaje(token);
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaToken, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaErrorVo();
			respuestaError.setRespuesta("ERROR");
			respuestaError.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "{idMedico}/correoToken", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> enviarToken(@PathVariable("idMedico") String idMedico, @RequestBody CorreoTokenVo correoTokenVo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaError=null;
		RespuestaErrorVo respuestaToken=null;
		
		log.debug("idMedico :" + idMedico);
		log.debug("nombre : "+ correoTokenVo.getNombreMedico());
		try {
			//String remitente = "edgar.hurtado@habilgroup.net";
			tokenMedicoFacade.enviarTokenEmail(correoTokenVo.getDestinatario(), correoTokenVo.getToken(), correoTokenVo.getNombreMedico(), correoTokenVo.getRemitente());
			
			//Se reutiliza la clase RespuestaErrorVo
			respuestaToken = new RespuestaErrorVo();
			
			respuestaToken.setRespuesta("OK");
			respuestaToken.setMensaje("Se envió el mensaje correctamente");
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaToken, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaErrorVo();
			respuestaError.setRespuesta("ERROR");
			respuestaError.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "{idMedico}/pacientes/{idPaciente}/dietas", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarDieta(@PathVariable("idMedico") String idMedico,@PathVariable("idPaciente") String idPaciente, @RequestBody DietaVo dietaVo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaError=null;
		
		try {

			dietaFacade.guardarDieta(idMedico, idPaciente, dietaVo.getDescripcion(), dietaVo.getAlimentosDisponibles());
			//Se reutiliza la clase RespuestaErrorVo
			respuestaError = new RespuestaErrorVo();
			respuestaError.setRespuesta("OK");
			respuestaError.setMensaje("La dieta se generó correctamente");
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaErrorVo();
			respuestaError.setRespuesta("ERROR");
			respuestaError.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
}
