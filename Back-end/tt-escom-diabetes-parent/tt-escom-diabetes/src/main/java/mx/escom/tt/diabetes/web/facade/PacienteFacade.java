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
	 * Proposito : Guardar un paciente en la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
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
	//TODO- Hacer el metodo correctamente
	public RespuestaVo guardarPaciente(String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = new PacienteDto();
		//Integer idMedico = null;
		String msjError = null;
		RespuestaVo result = null;
		
		try {

			pacienteAppService.guardarPaciente(pacienteDto);
			
			result=new RespuestaVo();
			
			result.setMensaje("El paciente se ha guardado correctamente.");
			
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al guardar la informaci�n.";
			throw new RuntimeException(msjError);
		}
		

		log.debug("Fin - Facade");
		return result;
	}
	
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
			pacienteAppService.eliminarPaciente(idPaciente);
			result=new RespuestaVo();
			result.setMensaje("Se borr� el paciente.");
			
		}catch (Exception ex) {
			msjError="Ocurri� un error al borrar el paciente.";
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
	public PacienteDto recuperarPaciente(String idPacienteStr) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		PacienteDto pacienteDto = null;

		try{
			
			if(idPacienteStr == null || idPacienteStr.trim().equals("")) 
				throw new RuntimeException("El identificador del paciente no puede ser nulo.");
			
			idPaciente = new Integer(idPacienteStr);			
			pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
			
			if(pacienteDto == null) 
				throw new RuntimeException("No se encontr� informaci�n del paciente.");
			
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
	//-TODO Terminar el metodo
	public RespuestaVo actualizarInformacionPaciente(String idPacienteStr, String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = null;
		String msjError = null;
		RespuestaVo result = null;
		if(idPacienteStr == null || idPacienteStr.trim().equals("")) {
			throw new RuntimeException();
		}
		
		
		//idPaciente = new Integer(idPacienteStr);
		//pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
		
		try {
			pacienteAppService.actualizarInformacionPaciente(pacienteDto);
			
			result = new RespuestaVo();
			
			result.setMensaje("Se actualiz� correctamente el paciente.");
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al actualizar la informaci�n.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}

}
