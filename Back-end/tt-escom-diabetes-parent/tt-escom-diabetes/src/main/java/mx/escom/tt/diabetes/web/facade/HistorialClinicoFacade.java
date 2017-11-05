package mx.escom.tt.diabetes.web.facade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.HistorialClinicoAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoListVo;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class HistorialClinicoFacade {

	@Autowired HistorialClinicoAppService historialClinicoAppService;
	
	/**
	 * Proposito : Guardar la informacion de un historial clinico.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoVo		-	Informacion de historial clinico.
	 * @return RespuestaVo				-	Respuesta luego de la ejecucion del metodo.
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion.
	 */
	public RespuestaVo guardarHistorialClinico(HistorialClinicoVo historialClinicoVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		RespuestaVo respuestaVo = null;
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;
		
		if(historialClinicoVo.getIdPaciente() == null) {
			msjEx = "No se tiene información del paciente.";
			throw new RuntimeException(msjEx);
		}
		
		try {		
			{//SE HACE LA TRANSFORMACION DEL VO AL DTO	
				historialClinicoDto = new HistorialClinicoDto();
				
				historialClinicoDto.setIdPaciente(Integer.valueOf(historialClinicoVo.getIdPaciente()));
				historialClinicoDto.setFecha(Timestamp.valueOf(historialClinicoVo.getFecha()));
				historialClinicoDto.setPeso(Double.parseDouble(historialClinicoVo.getPeso()));
				historialClinicoDto.setTalla(Double.parseDouble(historialClinicoVo.getTalla()));
				historialClinicoDto.setEstatura(Double.parseDouble(historialClinicoVo.getEstatura()));
				historialClinicoDto.setImc(Double.parseDouble(historialClinicoVo.getImc()));
				historialClinicoDto.setLipidos(Double.parseDouble(historialClinicoVo.getLipidos()));
				historialClinicoDto.setCarbohidratos(Double.parseDouble(historialClinicoVo.getCarbohidratos()));
				historialClinicoDto.setProteinas(Double.parseDouble(historialClinicoVo.getProteinas()));
				historialClinicoDto.setAzucar(Double.parseDouble(historialClinicoVo.getAzucar()));			
			}
			
			historialClinicoAppService.guardarHistorialClinico(historialClinicoDto);
			
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIdUsuario(historialClinicoVo.getIdPaciente());
			respuestaVo.setIndividuoRol(Constants.ID_ROL_PACIENTE);
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La información se guardó correctamente.");
		
		}
		catch (RuntimeException ex) {
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	/**
	 * Proposito : Recuperar la lista de historial clinico asociada a un paciente.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPacienteStr					-	Identificador del paciente.
	 * @return List<HistorialClinicoListVo>	-	Informacion del historial clinico del paciente.	
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion.
	 */
	public List<HistorialClinicoListVo> recuperarListaHistorialClinicoPorPaciente(String idPacienteStr) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		String msjEx = null;
		List<HistorialClinicoDto> listHistorialClinicoDto = null;
		HistorialClinicoListVo historialClinicoVo = null;
		ArrayList<HistorialClinicoListVo> lisHistorialClinicoVo = null;
		
		try {		
			
			if(idPacienteStr != null && !idPacienteStr.trim().equals("")) {
				idPaciente = new Integer(idPacienteStr);
			}
			
			listHistorialClinicoDto = historialClinicoAppService.recuperarListaHistorialClinicoPorIdPaciente(idPaciente);
			
			

			lisHistorialClinicoVo = new ArrayList<HistorialClinicoListVo>();
			
			for (HistorialClinicoDto historialClinicoDto : listHistorialClinicoDto) {
				historialClinicoVo = new HistorialClinicoListVo();
				
				historialClinicoVo.setIdHistorialClinico(historialClinicoDto.getIdHistorialClinico().toString());
				historialClinicoVo.setIdPaciente(historialClinicoDto.getIdPaciente().toString());
				historialClinicoVo.setPeso(String.valueOf(historialClinicoDto.getPeso()));
				historialClinicoVo.setAzucar(String.valueOf(historialClinicoDto.getAzucar()));
				historialClinicoVo.setTalla(String.valueOf(historialClinicoDto.getTalla()));
				historialClinicoVo.setFecha(historialClinicoDto.getFecha().toString());
				
				lisHistorialClinicoVo.add(historialClinicoVo);
			}
			
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return lisHistorialClinicoVo;
	}
	
	/**
	 * Proposito : Recuperar la informacion de un historial clinico 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idHistorialClinicoStr		-	Identificador del historial al buscar.
	 * @return HistorialClinicoDto		-	Informacion del historial clinico.
	 */
	public HistorialClinicoDto recuperarHistorialClinicoPorId(String idHistorialClinicoStr) {
		log.debug("Inicio - Facade");
		
		HistorialClinicoDto historialClinicoDto = null;
		Integer idHistorialClinico = null;
		String msjEx = null;
		
		try{
			
			if(idHistorialClinicoStr != null && !idHistorialClinicoStr.trim().equals("")) {	
				idHistorialClinico = new Integer(idHistorialClinicoStr);
			}
			
			historialClinicoDto = historialClinicoAppService.recuperarHistorialClinicoPorId(idHistorialClinico);
			
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return historialClinicoDto;
		
	}
}
