package mx.escom.tt.diabetes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.model.dao.MedicoDao;
import mx.escom.tt.diabetes.model.dto.MedicoDto;

@CommonsLog
@Service
public class MedicoAppService {

	@Autowired MedicoDao medicoDao;
	
	/**
	 * 
	 * Proposito : Guardar la informacion de un medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 * @param medicoDto
	 * @throws RuntimeException
	 */
	public void guardarMedico(MedicoDto medicoDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		
		if(medicoDto == null) {
			throw new RuntimeException();
		}
		
		
		try{
			medicoDao.guardarMedico(medicoDto);
		}catch(RuntimeException ex) {
			log.debug("ERRORR :::: " + ex.getMessage());
			msjEx = "La cédula : " + medicoDto.getCedulaProfesional() + " ya está en los registros del sistema.";
			throw new RuntimeException(msjEx);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el médico. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Recuperar la informacion de un medico de la base de datos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 * @param idMedico
	 * @return MedicoDto		-	Objeto con la informacion del medico
	 * @throws RuntimeException	-	Si ocurre un error durante la ejecucion
	 */
	public MedicoDto recuperarMedico(Integer idMedico) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		MedicoDto medicoDto = null;
		
		if(idMedico == null) {
			msjEx = "El identificador del medico no puede ser nulo";
			throw new RuntimeException(msjEx);
		}
		
		medicoDto = medicoDao.recuperarMedico(idMedico);
		
		log.debug("Fin - Service");
		return medicoDto;
	}
	
	/**
	 * 
	 * Proposito : Recuperar el identificador del individuo por su IdMedico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param idIndividuo			-	id Individuo del medico
	 * @return Integer				-	id Medico
	 * @throws RuntimeException		-	Si ocurre un error durante la ejecucion
	 */
	public Integer recuperarIdMedicoPorIdIndividuo(Integer idIndividuo) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		Integer idMedico = null;
		
		if(idIndividuo == null) {
			msjEx = "El identificador del medico no puede ser nulo";
			throw new RuntimeException(msjEx);
		}
		
		idMedico = medicoDao.recuperarIdMedicoPorIdIndividuo(idIndividuo);
		
		log.debug("Fin - Service");
		return idMedico;
	}
	
	/**
	 * 
	 * Proposito : Recuperar los pacientes asociados a un medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param idMedico					-	Identificador del medico
	 * @return List<MedicoPacientesVo>	-	Lista con los pacientes del medico	 	
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion 
	 */
	public List<MedicoPacientesVo> recuperarPacientesDeMedico(Integer idMedico) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		List<MedicoPacientesVo> result = null;
		
		if(idMedico == null) {
			msjEx = "El identificador del medico no puede ser nulo";
			throw new RuntimeException(msjEx);
		}
		
		result = medicoDao.recuperarPacientesDeMedico(idMedico);
		
		log.debug("Fin - Service");
		return result;
	}
}
