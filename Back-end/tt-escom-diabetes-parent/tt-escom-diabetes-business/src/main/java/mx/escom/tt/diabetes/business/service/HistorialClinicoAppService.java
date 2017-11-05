package mx.escom.tt.diabetes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.HistorialClinicoDao;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;

@CommonsLog
@Service
public class HistorialClinicoAppService {
	
	@Autowired HistorialClinicoDao historialClinicoDao;
	
	/**
	 * Proposito : Recuperar la informacion de un registro de historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idHistorialClinico	-	Identificador del historial clinico a buscar.
	 * @return HistorialClinicoDto	-	Información del registro buscado.	
	 * @throws RuntimeException		-	Si ocurre un error durante la ejecucion.
	 */
	public HistorialClinicoDto recuperarHistorialClinicoPorId(Integer idHistorialClinico) throws RuntimeException{
		log.debug("Inicio - Service");
		
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(idHistorialClinico == null) {
				msjEx = "El identificador del historial clínico no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			
			historialClinicoDto = historialClinicoDao.recuperarHistorialClinicoPorId(idHistorialClinico);
			
			if(historialClinicoDto == null) {
				msjEx = "No se encontró la información para el historial clínico con id : " + idHistorialClinico + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico con id : " + idHistorialClinico + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return historialClinicoDto;
	}
	
	/**
	 * Proposito : Recuperar el historial clinico de un paciente.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPaciente					-	Identificador del paciente.
	 * @return List<HistorialClinicoDto>	-	Lista con la informacion de historial clinico del paciente.	
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion.
	 */
	public List<HistorialClinicoDto> recuperarListaHistorialClinicoPorIdPaciente(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		
		List<HistorialClinicoDto> listHistorialClinicoDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}	
		try {
			
			listHistorialClinicoDto = historialClinicoDao.recuperarListaHistorialClinicoPorIdPaciente(idPaciente);
			
			if(listHistorialClinicoDto == null) {
				msjEx = "No se encontraron registros para el paciente con id : " + idPaciente + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la información clínica del paciente con id : " + idPaciente + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return listHistorialClinicoDto;
	}
	
	/**
	 * Proposito : Guardar la informacion de un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoDto		-	DTO con la informacion del historial clinico.
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion.
	 */	
	public void guardarHistorialClinico(HistorialClinicoDto historialClinicoDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;

		if(historialClinicoDto == null) {
			msjEx = "La información del historial no puede ser nula.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			historialClinicoDao.guardarHistorialClinico(historialClinicoDto);	
			
		}catch(RuntimeException ex) {
			log.debug("ERRORR :::: " + ex.getMessage());
			msjEx = ex.getMessage() + " El usuario con id : " + historialClinicoDto.getIdPaciente() + " no está registrado como paciente.";
			throw new RuntimeException(msjEx);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar la información del historial clínico. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}

}
