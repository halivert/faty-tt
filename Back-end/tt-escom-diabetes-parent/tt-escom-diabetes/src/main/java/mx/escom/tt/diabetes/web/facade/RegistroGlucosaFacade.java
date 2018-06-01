package mx.escom.tt.diabetes.web.facade;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.RegistroGlucosaAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.utils.NumberHelper;
import mx.escom.tt.diabetes.commons.vo.RegistroGlucosaCommonVo;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;
import mx.escom.tt.diabetes.web.vo.RegistroGlucosaVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class RegistroGlucosaFacade extends NumberHelper{

	@Autowired RegistroGlucosaAppService registroGlucosaAppService;
	
	@Qualifier("FormatoTimpeStamp")
	@Autowired SimpleDateFormat formatoFecha;
	
	@Qualifier("FormatoFechaNacimiento")
	@Autowired SimpleDateFormat formatoFechaNacimiento;
	
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
			
			if(Double.parseDouble(registroGlucosaVo.getAzucar()) < 50 || Double.parseDouble(registroGlucosaVo.getAzucar()) > 450){
				msjEx = "Revisar el nivel de glucosa.";
				throw new RuntimeException(msjEx);
			}
			
			{//Se genera el formato de la fecha
				Date date = formatoFechaNacimiento.parse(registroGlucosaVo.getFechaRegistro());
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
			throw new RuntimeException(ex.getMessage());
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
		if(StringUtils.isEmpty(registroGlucosaVo.getIdRegistroGlucosa())) {
			msjEx = "El identificador del registro no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			if(!isNumeric(registroGlucosaVo.getAzucar())){
				msjEx = "El registro de la glucosa sólo debe contener números.";
				throw new RuntimeException(msjEx);
			}
			
			if(Double.parseDouble(registroGlucosaVo.getAzucar()) < 50 || Double.parseDouble(registroGlucosaVo.getAzucar()) > 450){
				msjEx = "Revisar el nivel de glucosa.";
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
			throw new RuntimeException(ex.getMessage());
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
		
		if(StringUtils.isEmpty(idRegistroGlucosa)) {
			msjEx = "El identificador del registro no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!StringUtils.isNumeric(idRegistroGlucosa)) {
			msjEx = "El identificador del registro no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			idRegistro = new Integer(idRegistroGlucosa);			
			registroGlucosaDto = registroGlucosaAppService.recuperaRegistroGlucosaPorIdAppService(idRegistro);
			registroGlucosaVo = transformaRegistroGlucosaToVo(registroGlucosaDto);
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return registroGlucosaVo;
	}
	
	/**
	 * Proposito : Recuperar los registros de glucosa asociados a un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 12/02/2018
	 * @param idPaciente					- Identificador del paciente
	 * @return List<RegistroGlucosaVo>	-	Lista de registros recuperados
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public List<RegistroGlucosaVo> recuperaRegistroGlucosaPorIdPaciente(String idPaciente) throws RuntimeException{
		log.debug("Inicio - Facade");
		List<RegistroGlucosaVo> registroGlucosaVoList = null;
		RegistroGlucosaVo registroGlucosaVo = null;
		List<RegistroGlucosaDto> registroGlucosaDtoList = null;
		Integer idPacienteInt = null;
		String msjEx = null;
		
		if(StringUtils.isEmpty(idPaciente)) {
			msjEx = "El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!StringUtils.isNumeric(idPaciente)) {
			msjEx = "El identificador del paciente no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			
			idPacienteInt = new Integer(idPaciente);
			
			registroGlucosaDtoList = registroGlucosaAppService.recuperaListaRegistroGlucosaPorIdPacienteAppService(idPacienteInt);
			
			{//Transformacion del DTO
				registroGlucosaVoList = new ArrayList<RegistroGlucosaVo>();
				for(RegistroGlucosaDto registroGlucosaDto : registroGlucosaDtoList) {
					registroGlucosaVo = transformaRegistroGlucosaToVo(registroGlucosaDto);

					registroGlucosaVoList.add(registroGlucosaVo);
				}
			}
		}catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar los registros de glucosa." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return registroGlucosaVoList;
	}
	
	
	/**
	 * Proposito : Recuperar N numero de registros asociados a un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 14/04/2018
	 * @param idPaciente					-	Identificador del paciente
	 * @param limiteRegistro				-	Limite de registros a recuperar
	 * @return List<RegistroGlucosaVo>	-	Lista de registros recuperados
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public List<RegistroGlucosaVo> recuperaNRegistroGlucosa(String idPaciente, String limiteRegistro) throws RuntimeException{
		log.debug("Inicio - Facade");
		List<RegistroGlucosaVo> registroGlucosaVoList = null;
		RegistroGlucosaVo registroGlucosaVo = null;
		List<RegistroGlucosaCommonVo> registroGlucosaCommonVoList = null;
		Integer idPacienteInt = null;
		Integer limiteRegistroInt = null;
		String msjEx = null;
		
		if(StringUtils.isEmpty(idPaciente)) {
			msjEx = "El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!StringUtils.isNumeric(idPaciente)) {
			msjEx = "El identificador del paciente no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		
		try{
			idPacienteInt = new Integer(idPaciente);
			limiteRegistroInt = new Integer(limiteRegistro);
			
			registroGlucosaCommonVoList = registroGlucosaAppService.recuperaNRegistroGlucosaAppService(idPacienteInt, limiteRegistroInt);
			{//Transformacion del Objeto
				registroGlucosaVoList = new ArrayList<RegistroGlucosaVo>();
				for(RegistroGlucosaCommonVo registroGlucosaCommonVo : registroGlucosaCommonVoList) {
					registroGlucosaVo = transformaRegistroGlucosaToVo(registroGlucosaCommonVo);
					registroGlucosaVoList.add(registroGlucosaVo);
				}
			}
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar los registros de glucosa." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return registroGlucosaVoList;
	}
	
	/**
	 * Proposito : 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @param idPaciente
	 * @param fechaInicio
	 * @param fechaFin
	 * @return List<RegistroGlucosaVo>	-	Lista de registros recuperados
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public List<RegistroGlucosaVo> recuperaListaRegistroGlucosaPorFiltros(String idPaciente, String fechaInicio, String fechaFin) throws RuntimeException{
		log.debug("Inicio - Facade");
		List<RegistroGlucosaVo> registroGlucosaVoList = null;
		RegistroGlucosaVo registroGlucosaVo = null;
		List<RegistroGlucosaCommonVo> registroGlucosaCommonVoList = null;
		Integer idPacienteInt = null;
		String msjEx = null;
		
		if(StringUtils.isEmpty(idPaciente)) {
			msjEx = "El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(!StringUtils.isNumeric(idPaciente)) {
			msjEx = "El identificador del paciente no puede tener letras.";
			throw new RuntimeException(msjEx);
		}
		 
		try{
			idPacienteInt = new Integer(idPaciente);
			
			fechaInicio = fechaInicio.replace("-", "/");
			fechaFin = fechaFin.replace("-", "/");
			
			Date date1 = formatoFechaNacimiento.parse(fechaInicio);
			Date date2 = formatoFechaNacimiento.parse(fechaFin);
			
			String fechaInicioStr = formatoFecha.format(date1);
			String fechaFinStr = formatoFecha.format(date2);
			
			registroGlucosaCommonVoList = registroGlucosaAppService.recuperaListaRegistroGlucosaPorFiltrosAppService(idPacienteInt, Timestamp.valueOf(fechaInicioStr),Timestamp.valueOf(fechaFinStr));
			{//Transformacion del Objeto
				registroGlucosaVoList = new ArrayList<RegistroGlucosaVo>();
				for(RegistroGlucosaCommonVo registroGlucosaCommonVo : registroGlucosaCommonVoList) {
					registroGlucosaVo = transformaRegistroGlucosaToVo(registroGlucosaCommonVo);
					registroGlucosaVoList.add(registroGlucosaVo);
				}
			}
			
		}catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar los registros de glucosa." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return registroGlucosaVoList;
	}
	
	/**
	 * Proposito : Transformar un objeto RegistroGlucosaCommonVo a un RegistroGlucosaVo
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @param registroGlucosaCommonVo
	 * @return RegistroGlucosaVo			-	Objeto transformado
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public RegistroGlucosaVo transformaRegistroGlucosaToVo(RegistroGlucosaCommonVo registroGlucosaCommonVo) throws RuntimeException{
		log.debug("Inicio - CommonVo to Vo");
		RegistroGlucosaVo registroGlucosaVo = null;
		
		Locale esLocale = new Locale("es", "ES");
		SimpleDateFormat formateador = new SimpleDateFormat("d 'de' MMMM 'de' yyyy",esLocale);
		
		registroGlucosaVo = new RegistroGlucosaVo();
		registroGlucosaVo.setIdRegistroGlucosa(registroGlucosaCommonVo.getID_REGISTRO_GLUCOSA().toString());
		registroGlucosaVo.setIdPaciente(registroGlucosaCommonVo.getID_PACIENTE().toString());
		registroGlucosaVo.setAzucar(String.valueOf(registroGlucosaCommonVo.getAZUCAR()));
		registroGlucosaVo.setFechaRegistro(formateador.format(registroGlucosaCommonVo.getFECHA_REGISTRO()));
		registroGlucosaVo.setFechaActualizacion(registroGlucosaCommonVo.getFECHA_ACTUALIZACION() != null ? formateador.format(registroGlucosaCommonVo.getFECHA_ACTUALIZACION()) : Constants.CADENA_VACIA);
		
		log.debug("Fin - CommonVo to Vo");
		return registroGlucosaVo;
	}
	
	/**
	 * Proposito : Transformar un objeto RegistroGlucosaDto a un RegistroGlucosaVo
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @param registroGlucosaDto
	 * @return RegistroGlucosaVo			-	Objeto transformado
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public RegistroGlucosaVo transformaRegistroGlucosaToVo(RegistroGlucosaDto registroGlucosaDto) throws RuntimeException{
		log.debug("Inicio - Dto to Vo");
		RegistroGlucosaVo registroGlucosaVo = null;
		
		Locale esLocale = new Locale("es", "ES");
		SimpleDateFormat formateador = new SimpleDateFormat("d 'de' MMMM 'de' yyyy",esLocale);
		
		registroGlucosaVo = new RegistroGlucosaVo();
		registroGlucosaVo.setIdRegistroGlucosa(registroGlucosaDto.getIdRegistroGlucosa().toString());
		registroGlucosaVo.setIdPaciente(registroGlucosaDto.getIdPaciente().toString());
		registroGlucosaVo.setAzucar(String.valueOf(registroGlucosaDto.getAzucar()));
		registroGlucosaVo.setFechaRegistro(formateador.format(registroGlucosaDto.getFechaRegistro()));
		registroGlucosaVo.setFechaActualizacion(registroGlucosaDto.getFechaActualizacion() != null ? formateador.format(registroGlucosaDto.getFechaActualizacion()) : Constants.CADENA_VACIA);
		
		log.debug("Fin - Dto to Vo");
		return registroGlucosaVo;
	}
	
}
