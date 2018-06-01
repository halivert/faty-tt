package mx.escom.tt.diabetes.business.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.UltimoHistorialClinicoVo;
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
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico con id : " + idHistorialClinico + ex.getMessage();
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
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la información clínica del paciente con id : " + idPaciente + ex.getMessage();
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
			msjEx = ex.getMessage() + " El usuario con id : " + historialClinicoDto.getIdPaciente() + " no está registrado como paciente.";
			throw new RuntimeException(msjEx);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar la información del historial clínico. \n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	public UltimoHistorialClinicoVo recuperarUltimoHistorialClinicoPorIdPaciente(Integer idPaciente) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		UltimoHistorialClinicoVo ultimoHistorialClinicoVo = null; 
		if(idPaciente == null) {
			msjEx = "El identificador del paciente no puede ser nulo.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			ultimoHistorialClinicoVo = historialClinicoDao.recuperarUltimoHistorialClinicoPorIdPaciente(idPaciente);	
			
			if(ultimoHistorialClinicoVo == null) {
				msjEx = "No se encontró la información para el paciente con id: " + idPaciente + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException ex) {
			throw new RuntimeException(msjEx);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar el historial clinico.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		log.debug("Fin - Service");
		return ultimoHistorialClinicoVo;
	}
	
	/**
	 * Proposito :  Actualizar un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 05/02/2018
	 * @param historialClinicoDto		-	Informacion del historial que se quiere actualizar
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public void actualizarHistorialClinico(HistorialClinicoDto historialClinicoDto,String observaciones) throws RuntimeException{
		log.debug("Inicio - Service");
		
		String msjEx = null;
		HistorialClinicoDto aux = null;
		
		if(historialClinicoDto == null) {
			msjEx = "La información del historial no puede ser nula.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			aux = historialClinicoDao.recuperarHistorialClinicoPorId(historialClinicoDto.getIdHistorialClinico());
			
			if(aux == null) {
				msjEx = "No se encontró información del historial con id : " + historialClinicoDto.getIdHistorialClinico() + ".No se puede actualizar la información";
				throw new RuntimeException(msjEx);
			}
			if(aux.getIdPaciente() != historialClinicoDto.getIdPaciente()){
				msjEx = "El historial clinico no pertenece al paciente con id :" + historialClinicoDto.getIdPaciente();
				throw new RuntimeException(msjEx);
			}
			
			{//Se arma el DTO que se quiere actualizar
				aux.setAzucar(historialClinicoDto.getAzucar());
				aux.setCarbohidratos(historialClinicoDto.getCarbohidratos());
				aux.setEstatura(historialClinicoDto.getEstatura());
				aux.setImc(historialClinicoDto.getImc());
				aux.setLipidos(historialClinicoDto.getLipidos());
				aux.setPeso(historialClinicoDto.getPeso());
				aux.setProteinas(historialClinicoDto.getProteinas());
				aux.setTalla(historialClinicoDto.getTalla());
				aux.setActividadFisica(historialClinicoDto.getActividadFisica());
				if(!StringUtils.isEmpty(observaciones)) {
					aux.setObservaciones(observaciones);
				}else {
					aux.setObservaciones("");
				}


			}
			
			historialClinicoDao.actualizarHistorialClinico(aux);
			
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "actualizar la información del registro." + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Service");
	}
	
	
	public void guardarInformacionCSV(HistorialClinicoDto historialClinicoDto) throws RuntimeException{
		
	}

}
