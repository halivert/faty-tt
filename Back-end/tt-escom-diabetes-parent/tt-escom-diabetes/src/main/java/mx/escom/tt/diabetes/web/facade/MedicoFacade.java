package mx.escom.tt.diabetes.web.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.MedicoAppService;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;

@Service
@CommonsLog
public class MedicoFacade {
	
	@Autowired MedicoAppService medicoAppService;
	
	/**
	 * Proposito : Recuperar una lista de pacientes segun el medico tratane
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param idMedico					-	Identificador del medico
	 * @return 	List<MedicoPacientesVo>	-	Lista de pacientes	
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public List<MedicoPacientesVo> recuperarPacientesDeMedico(String idMedico) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		List<MedicoPacientesVo> result = null;
		Integer idMedicoInt = null;
		
		if(idMedico != null && !idMedico.trim().equals("")) {
			idMedicoInt = new Integer(idMedico);
		}
	
		try {
			result = medicoAppService.recuperarPacientesDeMedico(idMedicoInt);
			
			if(result == null || result.isEmpty()) {
				throw new RuntimeException("No se encontraron pacientes.");
			}
			
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		log.debug("Fin - Facade");
		return result;
	}

}
