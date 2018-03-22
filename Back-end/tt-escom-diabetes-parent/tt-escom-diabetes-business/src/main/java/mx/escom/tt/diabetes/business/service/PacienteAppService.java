package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.PacienteDao;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

@CommonsLog
@Service
public class PacienteAppService {
	
	@Autowired PacienteDao pacienteDao;
	@Autowired TokenMedicoDao tokenMedicoDao;
	
	/**
	 * 
	 * Proposito : Metodo para recuperar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPaciente				- Identificador del paciente
	 * @return PacienteDto				- Objeto con la informacion del paciente	
	 * @throws RuntimeException			- Si ocurre un error durante la ejecucion 
	 */
	public PacienteDto recuperarPacientePorIdPacienteAppService(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		
		PacienteDto pacienteDto = null;
		
		if(idPaciente == null) {
			throw new RuntimeException();
		}

		pacienteDto = pacienteDao.recuperarPacientePorIdPaciente(idPaciente);
		
		
		log.debug("Fin - Service");
		return pacienteDto;
	}
	
	/**
	 * 
	 * Proposito : Metodo para guardar la informacion de un paciente
	 * @author Edgar, ESCOM
	 * @version 1,1,0. 03/11/2017
	 * @param pacienteDto			- Objeto con la informacion de un paciente 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion 
	 */
	public void guardarPacienteAppService(PacienteDto pacienteDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		if(pacienteDto == null) {
			throw new RuntimeException();
		}
		
		try {
			pacienteDao.guardarPaciente(pacienteDto);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar la información del paciente.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	/**
	 * 
	 * Proposito : Metodo para eliminar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPaciente			- Identificador del paciente 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion 
	 */
	public void eliminarPacienteAppService(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		
		if(idPaciente == null) {
			throw new RuntimeException();
		}
		pacienteDao.eliminarPacientePorIdPaciente(idPaciente);	
		log.debug("Fin - Service");
	}
	
	/**
	 * 
	 * Proposito : Metodo para actualizar la informacion de un paciente 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017	
	 * @param pacienteDto			- Informacion del paciente 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion
	 */
	public void cambiarMedicoPacienteAppService(Integer idPaciente, String codigoMedico) throws RuntimeException{
		log.debug("Inicio - Service");
		String msjError = null;
		TokenMedicoDto tokenMedicoDto = null;
		PacienteDto pacienteDto = null;
		if(idPaciente == null) {
			msjError="El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjError);
		}
	
		try {
			//Se recupera el objeto Token el cual tiene el id del medico
			tokenMedicoDto = tokenMedicoDao.recuperarToken(codigoMedico);
			
			if(tokenMedicoDto == null) {
				msjError="El código del médico no existe.";
				throw new RuntimeException(msjError);
			}
			
			pacienteDto = new PacienteDto();
			pacienteDto.setIdMedico(tokenMedicoDto.getIdMedico());
			pacienteDto.setIdUsuario(idPaciente);
		
			
			//Se elimina el token
			tokenMedicoDao.borrarToken(codigoMedico);
			pacienteDao.actualizarInformacionPaciente(pacienteDto);
		}
		catch(RuntimeException ex){
			throw new RuntimeException(msjError,ex.getCause());
		}
		catch(Exception ex){
			msjError = Constants.MSJ_EXCEPTION + "actualizar la información del paciente.";
			throw new RuntimeException(msjError,ex.getCause());
		}
		log.debug("Fin - Service");
	}

}
