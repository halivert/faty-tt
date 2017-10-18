package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.PacienteDao;
import mx.escom.tt.diabetes.model.dto.PacienteDto;

@CommonsLog
@Service
public class PacienteAppService {
	
	@Autowired PacienteDao pacienteDao;
	
	/**
	 * 
	 * Proposito : Metodo para recuperar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPaciente				- Identificador del paciente
	 * @return PacienteDto				- Objeto con la informacion del paciente	
	 * @throws RuntimeException			- Si ocurre un error durante la ejecucion 
	 */
	public PacienteDto recuperarPaciente(Integer idPaciente) throws RuntimeException{
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
	 * @version 1,0,0. 16/10/2017
	 * @param pacienteDto			- Objeto con la informacion de un paciente 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion 
	 */
	public void guardarPaciente(PacienteDto pacienteDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		if(pacienteDto == null) {
			throw new RuntimeException();
		}
		
		pacienteDao.guardarPaciente(pacienteDto);
		
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
	public void eliminarPaciente(Integer idPaciente) throws RuntimeException{
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
	public void actualizarInformacionPaciente(PacienteDto pacienteDto) throws RuntimeException{
		log.debug("Inicio - Service");
		if(pacienteDto == null) {
			throw new RuntimeException();
		}
		
		pacienteDao.actualizarInformacionPaciente(pacienteDto);
		log.debug("Fin - Service");
	}

}
