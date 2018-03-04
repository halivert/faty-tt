package mx.escom.tt.diabetes.web.facade;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.RegistroGlucosaAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.utils.NumberHelper;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;
import mx.escom.tt.diabetes.web.vo.RegistroGlucosaVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class RegistroGlucosaFacade extends NumberHelper{

	@Autowired RegistroGlucosaAppService registroGlucosaAppService;
	
	@Qualifier("FormatoTimpeStamp")
	@Autowired SimpleDateFormat formatoFecha;
	
	/**
	 * Proposito : Guardar la informacion de un registro que haga el paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param registroGlucosaVo					-	Informacion que se desea guardar
	 * @return RespuestaVo						-	Objeto con mensaje si la informacion se guardo correctamente
	 * @throws RuntimeException					-	Si ocurre un error durante la ejecucion
	 */
	public RespuestaVo guardaRegistroGlucosa(RegistroGlucosaVo registroGlucosaVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		RespuestaVo respuestaVo = null;
		RegistroGlucosaDto registroGlucosaDto = null;
		String msjEx = null;
		//TODO edgar.hurtado Hacer un bean para este formato
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		
		if(registroGlucosaVo == null) {
			msjEx = "La información no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(registroGlucosaVo.getFechaRegistro() == null || registroGlucosaVo.getFechaRegistro().isEmpty()) {
			msjEx = "La fecha de registro no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			if(!isNumeric(registroGlucosaVo.getAzucar())){
				msjEx = "El registro de la glucosa sólo debe contener números.";
				throw  new RuntimeException(msjEx);
			}
			
			{//Se genera el formato de la fecha
				Date date = sourceFormat.parse(registroGlucosaVo.getFechaRegistro());
				registroGlucosaVo.setFechaRegistro(formatoFecha.format(date));	
			}

			registroGlucosaDto = new RegistroGlucosaDto();
			registroGlucosaDto.setIdPaciente(Integer.valueOf(registroGlucosaVo.getIdPaciente()));
			registroGlucosaDto.setFechaRegistro(Timestamp.valueOf(registroGlucosaVo.getFechaRegistro()));
			registroGlucosaDto.setAzucar(Double.parseDouble(registroGlucosaVo.getAzucar()));
			
			registroGlucosaAppService.guardarRegistroGlucosaAppService(registroGlucosaDto);
			
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIdUsuario(registroGlucosaVo.getIdPaciente());
			respuestaVo.setIndividuoRol(Constants.ID_ROL_PACIENTE);
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La información se guardó correctamente.");
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	/**
	 * Proposito : Actualizar la informacion de un registro que haga el paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param registroGlucosaVo				-	Informacion que se desea actualizar
	 * @returnRespuestaVo					-	Objeto con mensaje si la informacion se guardo correctamente
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion
	 */
	public RespuestaVo actualizaRegistroGlucosa(RegistroGlucosaVo registroGlucosaVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		RespuestaVo respuestaVo = null;
		String msjEx = null;
		RegistroGlucosaDto registroGlucosaDto = null;
		
		if(registroGlucosaVo == null) {
			msjEx = "La información no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(registroGlucosaVo.getIdRegistroGlucosa() == null || registroGlucosaVo.getIdRegistroGlucosa().equals(Constants.CADENA_VACIA)) {
			msjEx = "El identificador del registro no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			if(!isNumeric(registroGlucosaVo.getAzucar())){
				msjEx = "El registro de la glucosa sólo debe contener números.";
				throw new RuntimeException(msjEx);
			}
			
			registroGlucosaDto = new RegistroGlucosaDto();
			registroGlucosaDto.setIdRegistroGlucosa(Integer.valueOf(registroGlucosaVo.getIdRegistroGlucosa()));
			registroGlucosaDto.setAzucar(Double.parseDouble(registroGlucosaVo.getAzucar()));
			
			
			registroGlucosaAppService.actualizaRegistroGlucosaAppService(registroGlucosaDto);
			
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIdUsuario(registroGlucosaVo.getIdPaciente());
			respuestaVo.setIndividuoRol(Constants.ID_ROL_PACIENTE);
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La información se actualizó correctamente.");
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	/**
	 * Proposito : Recuperar un registro de glucosa por id
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param idRegistroGlucosa				-	Identificador del registro que se quiere recuperar
	 * @return RegistroGlucosaVo			-	Registro recuperado
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion
	 */
	public RegistroGlucosaVo recuperaRegistroGlucosaPorId(String idRegistroGlucosa) throws RuntimeException{
		log.debug("Inicio - Facade");
		RegistroGlucosaVo registroGlucosaVo = null;
		RegistroGlucosaDto registroGlucosaDto = null;
		Integer idRegistro = null;
		String msjEx = null;
		Locale esLocale = new Locale("es", "ES");
		SimpleDateFormat formateador = new SimpleDateFormat("d 'de' MMMM 'de' yyyy",esLocale);
		
		if(idRegistroGlucosa == null || idRegistroGlucosa.equals(Constants.CADENA_VACIA)) {
			msjEx = "El identificador del registro no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!isNumeric(idRegistroGlucosa)) {
			msjEx = "El identificador del registro no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			
			idRegistro = new Integer(idRegistroGlucosa);
			
			registroGlucosaDto = registroGlucosaAppService.recuperaRegistroGlucosaPorIdAppService(idRegistro);
			
			{//Transformacion del DTO
				registroGlucosaVo = new RegistroGlucosaVo();
				registroGlucosaVo.setIdRegistroGlucosa(registroGlucosaDto.getIdRegistroGlucosa().toString());
				registroGlucosaVo.setIdPaciente(registroGlucosaDto.getIdPaciente().toString());
				registroGlucosaVo.setAzucar(String.valueOf(registroGlucosaDto.getAzucar()));
				registroGlucosaVo.setFechaRegistro(formateador.format(registroGlucosaDto.getFechaRegistro()));
				registroGlucosaVo.setFechaActualizacion(registroGlucosaDto.getFechaActualizacion() != null ? formateador.format(registroGlucosaDto.getFechaActualizacion()) : Constants.CADENA_VACIA);
			}
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return registroGlucosaVo;
	}
	
	/**
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 12/02/2018
	 * @param idPaciente
	 * @return
	 * @throws RuntimeException
	 */
	public List<RegistroGlucosaVo> recuperaRegistroGlucosaPorIdPaciente(String idPaciente) throws RuntimeException{
		log.debug("Inicio - Facade");
		List<RegistroGlucosaVo> registroGlucosaVoList = null;
		RegistroGlucosaVo registroGlucosaVo = null;
		List<RegistroGlucosaDto> registroGlucosaDtoList = null;
		Integer idPacienteInt = null;
		String msjEx = null;
		Locale esLocale = new Locale("es", "ES");
		SimpleDateFormat formateador = new SimpleDateFormat("d 'de' MMMM 'de' yyyy",esLocale);
		
		
		
		if(idPaciente == null || idPaciente.equals(Constants.CADENA_VACIA)) {
			msjEx = "El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!isNumeric(idPaciente)) {
			msjEx = "El identificador del paciente no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			
			idPacienteInt = new Integer(idPaciente);
			
			registroGlucosaDtoList = registroGlucosaAppService.recuperaListaRegistroGlucosaPorIdPacienteAppService(idPacienteInt);
			
			{//Transformacion del DTO
				registroGlucosaVoList = new ArrayList<RegistroGlucosaVo>();
				for(RegistroGlucosaDto registroGlucosaDto : registroGlucosaDtoList) {
					registroGlucosaVo = new RegistroGlucosaVo();
					
					registroGlucosaVo.setIdRegistroGlucosa(registroGlucosaDto.getIdRegistroGlucosa().toString());
					registroGlucosaVo.setIdPaciente(registroGlucosaDto.getIdPaciente().toString());
					registroGlucosaVo.setAzucar(String.valueOf(registroGlucosaDto.getAzucar()));
					registroGlucosaVo.setFechaRegistro(formateador.format(registroGlucosaDto.getFechaRegistro()));
					registroGlucosaVo.setFechaActualizacion(registroGlucosaDto.getFechaActualizacion() != null ? formateador.format(registroGlucosaDto.getFechaActualizacion()) : Constants.CADENA_VACIA);
					
					registroGlucosaVoList.add(registroGlucosaVo);
				}
				
			}
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return registroGlucosaVoList;
	}
	
	
	
}
