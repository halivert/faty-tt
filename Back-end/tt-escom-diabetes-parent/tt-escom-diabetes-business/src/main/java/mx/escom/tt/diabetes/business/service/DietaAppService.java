package mx.escom.tt.diabetes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.DietaDao;
import mx.escom.tt.diabetes.model.dto.DietaDto;

@CommonsLog
@Service
public class DietaAppService {

	@Autowired DietaDao dietaDao;
	
	/**
	 * Proposito : Guardar la una nueva dieta en la base de datos
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param dietaDto					-	Informacion que se desea guardar
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void guardarDietaAppService(DietaDto dietaDto) throws RuntimeException{
		log.debug("Inicio - Service");
		String msjEx = null;
		
		{//Validaciones 
			if(dietaDto == null) {
				msjEx = "La información de la dieta no puede ser nula o vacía.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			dietaDao.guardarDieta(dietaDto);
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la dieta.";
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Recuperar una dieta por medio de su identificador
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param idDieta						-	Identificador de la dieta que se desea recuperar
	 * @return DietaDto						-	Dieta que se recupero de la base de datos
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public DietaDto recuperarDietaPorIdAppService(Integer idDieta) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		DietaDto dietaDto = null;
		{//Validaciones 
			if(idDieta == null) {
				msjEx = "El identificador de la dieta no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			dietaDto = dietaDao.recuperarDietaPorId(idDieta);
			if(dietaDto == null) {
				msjEx = "No se encontraron registros.";
				throw new RuntimeException(msjEx);
			}
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
		return dietaDto;
	}
	
	/**
	 * Proposito : Recuperar una lista de dietas asociadas a un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 * @param idPaciente				-	Identificador del paciente del que se quiere recuperar las dietas
	 * @return List<DietaDto>			-	Dietas recuperadas		
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public List<DietaDto> recuperarDietaPorIdPacienteAppService(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		List<DietaDto> dietaDtoList = null;
		
		{//Validaciones 
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			dietaDtoList = dietaDao.recuperarDietaPorIdPaciente(idPaciente);
			log.debug("dietaDtoList.size() : " + dietaDtoList.size());
			if(dietaDtoList == null || dietaDtoList.size() == 0) {
				msjEx = "No se encontraron dietas asignadas.";
				throw new RuntimeException(msjEx);
			}
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Inicio - Service");
		return dietaDtoList;
	}
}
