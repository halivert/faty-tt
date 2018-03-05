package mx.escom.tt.diabetes.business.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.RegistroGlucosaDao;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;

@CommonsLog
@Service
public class RegistroGlucosaAppService {

	@Autowired RegistroGlucosaDao registroGlucosaDao;
	
	/**
	 * Proposito : Guardar la informacion de un nuevo registro en la base de datos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param registroGlucosaDto				-	Objeto con la informacion que se desea guardar.
	 * @throws RuntimeException					-	Si ocurre un error durante la ejecucion.
	 */
	public void guardarRegistroGlucosaAppService(RegistroGlucosaDto registroGlucosaDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		//Timestamp fechaRegistro = null;
		
		if(registroGlucosaDto == null) {
			msjEx = "La información del registro no puede ser nula.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			//fechaRegistro = new Timestamp(System.currentTimeMillis());
			
			//registroGlucosaDto.setFechaRegistro(fechaRegistro);
			registroGlucosaDao.guardaRegistroGlucosa(registroGlucosaDto);	

		}catch(RuntimeException ex) {
			log.debug("ERRORR :::: " + ex.getMessage());
			msjEx = "El usuario con id : " + registroGlucosaDto.getIdPaciente() + " no está registrado como paciente.";
			throw new RuntimeException(msjEx);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar la información del registro. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Actualizar un registro en la base de datos, si el registro ya fue actualizado una vez no se puede volver a actualizar
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param registroGlucosaDto			-	Objeto con la informacion del registro que se quiere actualizar
	 * @throws RuntimeException				-	Si no existe informacion del registro o si ya se actualizo una vez
	 */
	public void actualizaRegistroGlucosaAppService(RegistroGlucosaDto registroGlucosaDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		RegistroGlucosaDto aux = null;
		
		if(registroGlucosaDto == null) {
			msjEx = "La información del registro no puede ser nula.";
			throw new RuntimeException(msjEx);
		}
		if(registroGlucosaDto.getIdRegistroGlucosa() == null) {
			msjEx = "El id del registro no puede ser nulo.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			aux = registroGlucosaDao.recuperaRegistroGlucosaPorId(registroGlucosaDto.getIdRegistroGlucosa());
			
			if(aux == null) {
				msjEx = "No se encontró información del registro con id : " + registroGlucosaDto.getIdRegistroGlucosa() + "\n No se puede actualizar la información";
				throw new RuntimeException(msjEx);
			}
			if(aux.getFechaActualizacion() != null) {
				msjEx = "El registro ya fue actualizado, no se puede volver a actualizar.";
				throw new RuntimeException(msjEx);
			}
			
			aux.setAzucar(registroGlucosaDto.getAzucar());			
			aux.setFechaActualizacion(new Timestamp(System.currentTimeMillis()));

			registroGlucosaDao.actualizaRegistroGlucosa(aux);

		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al actualizar la información del registro. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	/**
	 * Proposito : Recuperar la informacion de un registro de glucosa por medio del id
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param idRegistroGlucosa						-	Identificador del registro que se quiere recuperar
	 * @return RegistroGlucosaDto					-	Objeto con la informacion del registro que se quiere recuperar
	 * @throws RuntimeException						-	Si existe un error durante la ejecucion o el registro no existe
	 */
	public RegistroGlucosaDto recuperaRegistroGlucosaPorIdAppService(Integer idRegistroGlucosa) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		RegistroGlucosaDto result = null;
		
		{//Validaciones 
			if(idRegistroGlucosa == null) {
				msjEx = "El identificador del registro no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}	
		
		try {
			
			result = registroGlucosaDao.recuperaRegistroGlucosaPorId(idRegistroGlucosa);
			
			if(result == null) {
				msjEx = "No se encontraron registros con id : " + idRegistroGlucosa + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la información del registro con id : " + idRegistroGlucosa + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}

		log.debug("Fin - Service");
		return result;
	}
	
	/**
	 * Proposito :  Recuperar una lista de registros asociados a un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param idPaciente					-	Identificador del paciente que se quieren recuperar los registros
	 * @return List<RegistroGlucosaDto>		-	Lista con la informacion asociada al paciente
	 * @throws RuntimeException				-	Si existe un error durante la ejecucion o no existen registros 
	 */
	public List<RegistroGlucosaDto> recuperaListaRegistroGlucosaPorIdPacienteAppService(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		List<RegistroGlucosaDto> result = null;
		String msjEx = null;
		
		{//Validaciones 
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}	
		
		try {
			
			result = registroGlucosaDao.recuperaListaRegistroGlucosaPorIdPaciente(idPaciente);
			
			if(result.isEmpty()) {
				msjEx = "No se encontraron registros para el paciente.";
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la información de los registros para el paciente con id: " + idPaciente + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return result;
	}
	
}
