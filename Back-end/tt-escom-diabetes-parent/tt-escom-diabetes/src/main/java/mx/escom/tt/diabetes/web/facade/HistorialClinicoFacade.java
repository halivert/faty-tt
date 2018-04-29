package mx.escom.tt.diabetes.web.facade;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.HistorialClinicoAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.UltimoHistorialClinicoVo;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoListVo;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
import mx.escom.tt.diabetes.web.vo.UltimoHistorialFacadeVo;

@Service
@CommonsLog
public class HistorialClinicoFacade {

	@Autowired
	HistorialClinicoAppService historialClinicoAppService;

	@Qualifier("FormatoFechaNacimiento")
	@Autowired
	SimpleDateFormat formatoFecha;
	
	@Resource(name = "MapaIndiceActividadFisica")
	private Map<Integer, String> mapaIndiceActividadFisica;

	/**
	 * Proposito : Guardar la informacion de un historial clinico.
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoVo
	 *            - Informacion de historial clinico.
	 * @return RespuestaVo - Respuesta luego de la ejecucion del metodo.
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion.
	 */
	public RespuestaVo guardarHistorialClinico(HistorialClinicoVo historialClinicoVo) throws RuntimeException {
		log.debug("Inicio - Facade");

		RespuestaVo respuestaVo = null;
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;

		try {

			historialClinicoDto = this.historialClinicoVoToDto(historialClinicoVo, Boolean.FALSE);

			if (historialClinicoDto == null) {
				msjEx = "Ocurrió un erro al guardar la información del paciente, intente nuevamente.";
				throw new RuntimeException(msjEx);
			}

			historialClinicoAppService.guardarHistorialClinico(historialClinicoDto);

			respuestaVo = new RespuestaVo();

			respuestaVo.setIdUsuario(historialClinicoVo.getIdPaciente());
			respuestaVo.setIndividuoRol(Constants.ID_ROL_PACIENTE);
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La información se guardó correctamente.");

		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return respuestaVo;
	}

	/**
	 * Proposito : Actualizar un historial clinico en la base de datos
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 05/02/2018
	 * @param historialClinicoVo
	 *            - Objeto con la finroamacion que se quiere actualizar
	 * @return RespuestaVo - Respuesta con mensaje existoso
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion
	 */
	public RespuestaVo actualizarHistorialClinico(HistorialClinicoVo historialClinicoVo) throws RuntimeException {
		log.debug("Inicio - Facade");

		RespuestaVo respuestaVo = null;
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;

		try {
				
			historialClinicoDto = this.historialClinicoVoToDto(historialClinicoVo, Boolean.TRUE);

			if (historialClinicoDto == null) {
				msjEx = "Ocurrió un erro al actualizar la información del paciente, intente nuevamente.";
				throw new RuntimeException(msjEx);
			}
				
			historialClinicoAppService.actualizarHistorialClinico(historialClinicoDto);

			respuestaVo = new RespuestaVo();
			respuestaVo.setIdUsuario(historialClinicoVo.getIdPaciente());
			respuestaVo.setIndividuoRol(Constants.ID_ROL_PACIENTE);
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La información del paciente se actualizó correctamente.");

		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "actualizar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return respuestaVo;
	}

	/**
	 * Proposito : Recuperar la lista de historial clinico asociada a un paciente.
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPacienteStr
	 *            - Identificador del paciente.
	 * @return List<HistorialClinicoListVo> - Informacion del historial clinico del
	 *         paciente.
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion.
	 */
	public List<HistorialClinicoListVo> recuperarListaHistorialClinicoPorPaciente(String idPacienteStr)
			throws RuntimeException {
		log.debug("Inicio - Facade");

		Integer idPaciente = null;
		String msjEx = null;
		List<HistorialClinicoDto> listHistorialClinicoDto = null;
		HistorialClinicoListVo historialClinicoVo = null;
		ArrayList<HistorialClinicoListVo> lisHistorialClinicoVo = null;

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		try {

			if (idPacienteStr != null && !idPacienteStr.trim().equals("")) {
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
				historialClinicoVo.setFecha(formateador.format(historialClinicoDto.getFecha()));

				lisHistorialClinicoVo.add(historialClinicoVo);
			}

		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return lisHistorialClinicoVo;
	}

	/**
	 * Proposito : Recuperar la informacion de un historial clinico
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idHistorialClinicoStr
	 *            - Identificador del historial al buscar.
	 * @return HistorialClinicoVo - Informacion del historial clinico.
	 */
	public HistorialClinicoVo recuperarHistorialClinicoPorId(String idHistorialClinicoStr) {
		log.debug("Inicio - Facade");

		HistorialClinicoDto historialClinicoDto = null;
		HistorialClinicoVo historialClinicoVo = null;

		Integer idHistorialClinico = null;
		String msjEx = null;

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		try {

			if (idHistorialClinicoStr != null && !idHistorialClinicoStr.trim().equals("")) {
				idHistorialClinico = new Integer(idHistorialClinicoStr);
			}

			historialClinicoDto = historialClinicoAppService.recuperarHistorialClinicoPorId(idHistorialClinico);

			if (historialClinicoDto != null) {
				historialClinicoVo = new HistorialClinicoVo();

				historialClinicoVo.setIdHistorialClinico(historialClinicoDto.getIdHistorialClinico().toString());
				historialClinicoVo.setIdPaciente(historialClinicoDto.getIdPaciente().toString());
				historialClinicoVo.setFecha(formateador.format(historialClinicoDto.getFecha()));
				historialClinicoVo.setPeso(String.valueOf(historialClinicoDto.getPeso()));
				historialClinicoVo.setTalla(String.valueOf(historialClinicoDto.getTalla()));
				historialClinicoVo.setEstatura(String.valueOf(historialClinicoDto.getEstatura()));
				historialClinicoVo.setImc(String.valueOf(historialClinicoDto.getImc()));
				historialClinicoVo.setLipidos(String.valueOf(historialClinicoDto.getLipidos()));
				historialClinicoVo.setCarbohidratos(String.valueOf(historialClinicoDto.getCarbohidratos()));
				historialClinicoVo.setProteinas(String.valueOf(historialClinicoDto.getProteinas()));
				historialClinicoVo.setAzucar(String.valueOf(historialClinicoDto.getAzucar()));
				historialClinicoVo.setActividadFisica(mapaIndiceActividadFisica.get(historialClinicoDto.getActividadFisica()));
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return historialClinicoVo;
	}

	/**
	 * Proposito : Recupear el historial clinico mas actual de un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/02/2018
	 * @param idPacienteStr
	 *            - Identificador del paciente
	 * @return UltimoHistorialFacadeVo - Informacion recuperada
	 */
	public UltimoHistorialFacadeVo recuperarUltimoHistorialClinicoPorIdPaciente(String idPacienteStr)
			throws RuntimeException {
		log.debug("Inicio - Facade");

		UltimoHistorialClinicoVo ultimoHistorialClinicoVo = null;
		UltimoHistorialFacadeVo ultimoHistorialFacadeVo = null;
		Integer idPaciente = null;
		String msjEx = null;

		Locale esLocale = new Locale("es", "ES");
		SimpleDateFormat formateador = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", esLocale);
		// SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		try {

			if (idPacienteStr != null && !idPacienteStr.trim().equals("")) {
				idPaciente = new Integer(idPacienteStr);
			}

			ultimoHistorialClinicoVo = historialClinicoAppService
					.recuperarUltimoHistorialClinicoPorIdPaciente(idPaciente);

			if (ultimoHistorialClinicoVo != null) {
				ultimoHistorialFacadeVo = new UltimoHistorialFacadeVo();

				ultimoHistorialFacadeVo
						.setIdHistorialClinico(ultimoHistorialClinicoVo.getID_HISTORIAL_CLINICO().toString());
				ultimoHistorialFacadeVo.setIdPaciente(ultimoHistorialClinicoVo.getID_PACIENTE().toString());
				ultimoHistorialFacadeVo.setNombreComppleto(
						ultimoHistorialClinicoVo.getNOMBRE() + " " + ultimoHistorialClinicoVo.getAPELLIDO_PATERNO()
								+ " " + ultimoHistorialClinicoVo.getAPELLIDO_MATERNO());
				ultimoHistorialFacadeVo
						.setFechaNacimiento(formateador.format(ultimoHistorialClinicoVo.getFECHA_NACIMIENTO()));
				ultimoHistorialFacadeVo.setEmail(ultimoHistorialClinicoVo.getEMAIL().trim());
				ultimoHistorialFacadeVo.setSexo(ultimoHistorialClinicoVo.getSEXO().trim());
				ultimoHistorialFacadeVo.setFechaHistorial(formatoFecha.format(ultimoHistorialClinicoVo.getFECHA()));
				ultimoHistorialFacadeVo.setPeso(String.valueOf(ultimoHistorialClinicoVo.getPESO()));
				ultimoHistorialFacadeVo.setTalla(String.valueOf(ultimoHistorialClinicoVo.getTALLA()));
				ultimoHistorialFacadeVo.setEstatura(String.valueOf(ultimoHistorialClinicoVo.getESTATURA()));
				ultimoHistorialFacadeVo.setImc(String.valueOf(ultimoHistorialClinicoVo.getIMC()));
				ultimoHistorialFacadeVo.setLipidos(String.valueOf(ultimoHistorialClinicoVo.getLIPIDOS()));
				ultimoHistorialFacadeVo.setCarbohidratos(String.valueOf(ultimoHistorialClinicoVo.getCARBOHIDRATOS()));
				ultimoHistorialFacadeVo.setProteinas(String.valueOf(ultimoHistorialClinicoVo.getPROTEINAS()));
				ultimoHistorialFacadeVo.setAzucar(String.valueOf(ultimoHistorialClinicoVo.getAZUCAR()));
				ultimoHistorialFacadeVo.setActividadFisica(mapaIndiceActividadFisica.get(ultimoHistorialClinicoVo.getACTIVIDAD_FISICA()));
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial clínico." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return ultimoHistorialFacadeVo;

	}

	public HistorialClinicoDto historialClinicoVoToDto(HistorialClinicoVo historialClinicoVo, boolean isUpdate) throws RuntimeException {
		log.debug("Inicio - Facade");
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;

		if(isUpdate) {
			if(StringUtils.isEmpty(historialClinicoVo.getIdHistorialClinico())){
				msjEx = "El identificador del historial no puede ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
		}
		if(StringUtils.isEmpty(historialClinicoVo.getIdPaciente())){
			msjEx = "El identificador del paciente no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getFecha())){
			msjEx = "La fecha no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getTalla())){
			msjEx = "La talla no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getEstatura())){
			msjEx = "La estatura no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getImc())){
			msjEx = "El IMC no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getLipidos())){
			msjEx = "El porcentaje de lípidos no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getCarbohidratos())){
			msjEx = "El porcentaje de carbohidratos no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getProteinas())){
			msjEx = "El porcentaje de proteinas no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getAzucar())){
			msjEx = "El nivel de azíucar no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		if(StringUtils.isEmpty(historialClinicoVo.getActividadFisica())){
			msjEx = "La actividad física del paciente no puede ser nula o vacía.";
			throw new RuntimeException(msjEx);
		}
		try {
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
			historialClinicoDto.setActividadFisica(Integer.valueOf(historialClinicoVo.getActividadFisica()));
			
			if(isUpdate) {
				historialClinicoDto.setIdHistorialClinico(Integer.valueOf(historialClinicoVo.getIdHistorialClinico()));
			}

		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "hacer la transformación del VO al DTO." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Facade");
		return historialClinicoDto;
	}
	
}
