package mx.escom.tt.diabetes.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class PacienteFacade {

	@Autowired PacienteAppService pacienteAppService;
		
	/**
	 * 
	 * Proposito : Eliminar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 */
	public RespuestaVo eliminarPaciente(String idPacienteStr) {
		log.debug("Inicio - Facade");
		
		RespuestaVo result = null;
		Integer idPaciente = null;
		String msjError = null;
		
		if(idPacienteStr == null || !idPacienteStr.trim().equals("")) {
			idPaciente = new Integer(idPacienteStr);
		}
		
		
		try{
			pacienteAppService.eliminarPacienteAppService(idPaciente);
			result=new RespuestaVo();
			result.setMensaje("Se borró el paciente.");
			
		}catch (Exception ex) {
			msjError="Ocurrió un error al borrar el paciente.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : Recuperar un paciente de la BD
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 * @return	PacienteDto			- Objeto con la informacion del paciente
	 */
	public PacienteDto recuperarPacientePorId(String idPacienteStr) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		PacienteDto pacienteDto = null;

		try{
			
			if(idPacienteStr == null || idPacienteStr.trim().equals("")) 
				throw new RuntimeException("El identificador del paciente no puede ser nulo.");
			
			idPaciente = new Integer(idPacienteStr);			
			pacienteDto = pacienteAppService.recuperarPacientePorIdPacienteAppService(idPaciente);
			
			if(pacienteDto == null) 
				throw new RuntimeException("No se encontró información del paciente.");
			
		}catch (Exception ex) {
			log.error("ex.getMessage() : " + ex.getMessage(),ex);
			throw new RuntimeException(ex.getMessage());
		}
			
		log.debug("Fin - Facade");
		return pacienteDto;
	}
	
	/**
	 * 
	 * Proposito :	Actualizar la informacion de un paciente 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/10/2017
	 * @param idPacienteStr
	 * @param idIndividuoStr
	 * @param idMedicoStr
	 * @param pesoStr
	 * @param tallaStr
	 * @param estaturaStr
	 * @param imcStr
	 * @param lipidosStr
	 * @param carbohidratosStr
	 * @param proteinasStr
	 */
	//-TODO Pasar este metodo da la clase de UsuarioFacade
	public RespuestaVo actualizarInformacionPaciente(String idPacienteStr, String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		String msjError = null;
		RespuestaVo result = null;
		if(idPacienteStr == null || idPacienteStr.trim().equals("")) {
			throw new RuntimeException();
		}
		
		
		//idPaciente = new Integer(idPacienteStr);
		//pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
		
		try {
			//pacienteAppService.actualizarInformacionPacienteAppService(pacienteDto);
			
			result = new RespuestaVo();
			
			result.setRespuesta("OK");
			result.setMensaje("Se actualizó correctamente el paciente.");
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al actualizar la información.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * Proposito : Cambiar el medico asociado a un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/03/2018
	 * @param idPacienteStr				-	Identificador del paciente
	 * @param token						-	Codigo del medico al cual se quiere cambiar el paciente
	 * @return	RespuestaVo				-	Respuesta informando el exito del cambio, nulo en caso contrario
	 */
	public RespuestaVo cambiarMedicoPaciente(String idPacienteStr, String token) {
		log.debug("Inicio - Facade");
		
		RespuestaVo result = null;
		Integer idPaciente = null;
		String msjError = null;
		
		if(idPacienteStr == null || idPacienteStr.trim().equals("")) {
			msjError="El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjError);
		}
		if(token == null || token.trim().equals("")) {
			msjError="El código del médico no puede ser vacío.";
			throw new RuntimeException(msjError);
		}
		
		
		try{
			idPaciente = new Integer(idPacienteStr);
			pacienteAppService.cambiarMedicoPacienteAppService(idPaciente,token);
			
			result=new RespuestaVo();
			
			result.setRespuesta("OK");
			result.setMensaje("Se cambió de médico.");
			
		}catch (RuntimeException runTEx) {
			throw new RuntimeException(runTEx.getMessage());
		}catch (Exception ex) {
			msjError="Ocurrió un error al borrar el paciente.";
			throw new RuntimeException(msjError);
		}
		log.debug("Fin - Facade");
		return result;
	}

}
