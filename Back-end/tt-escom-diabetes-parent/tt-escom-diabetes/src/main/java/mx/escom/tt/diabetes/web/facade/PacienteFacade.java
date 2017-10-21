package mx.escom.tt.diabetes.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@Service
@CommonsLog
public class PacienteFacade {

	@Autowired PacienteAppService pacienteAppService;
	
	/**
	 * 
	 * Proposito : Guardar un paciente en la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idIndividuoStr
	 * @param idMedicoStr
	 * @param pesoStr
	 * @param tallaStr
	 * @param estaturaStr
	 * @param imcStr
	 * @param lipidosStr
	 * @param carbohidratosStr
	 * @param proteinasStr
	 */
	public RespuestaVo guardarPaciente(String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = new PacienteDto();
		Integer idIndividuo = null;
		Integer idMedico = null;
		String msjError = null;
		RespuestaVo result = null;
		double peso;
		double talla;
		double estatura;
		double imc;
		double lipidos;
		double carbohidratos;
		double proteinas;
		
		//Validar que atributos deben ser obligatorios
		if(idIndividuoStr != null && !idIndividuoStr.trim().equals("")) {
			idIndividuo = new Integer(idIndividuoStr);
			pacienteDto.setIdIndividuo(idIndividuo);
		}
		
		if(idMedicoStr != null && !idMedicoStr.trim().equals("")) {
			idMedico = new Integer(idMedicoStr);
			pacienteDto.setIdMedico(idMedico);
		}
		if(pesoStr != null && !pesoStr.trim().equals("")) {
			peso = Double.parseDouble(pesoStr);
			pacienteDto.setPeso(peso);
		}
		
		if(tallaStr != null && !tallaStr.trim().equals("")) {
			talla = Double.parseDouble(tallaStr);
			pacienteDto.setTalla(talla);
		}
		
		if(estaturaStr != null && !estaturaStr.trim().equals("")) {
			estatura = Double.parseDouble(estaturaStr);
			pacienteDto.setEstatura(estatura);
		}
		
		if(imcStr != null && !imcStr.trim().equals("")) {
			imc = Double.parseDouble(imcStr);
			pacienteDto.setImc(imc);
		}
		
		if(lipidosStr != null && !lipidosStr.trim().equals("")) {
			lipidos = Double.parseDouble(lipidosStr);
			pacienteDto.setLipidos(lipidos);
		}
		
		if(carbohidratosStr != null && !carbohidratosStr.trim().equals("")) {
			carbohidratos = Double.parseDouble(carbohidratosStr);
			pacienteDto.setCarbohidratos(carbohidratos);
		}
		
		if(proteinasStr != null && !proteinasStr.trim().equals("")) {
			proteinas = Double.parseDouble(proteinasStr);
			pacienteDto.setProteinas(proteinas);
		}
		try {
			
			pacienteAppService.guardarPaciente(pacienteDto);
			
			result=new RespuestaVo();
			
			result.setMensaje("El paciente se ha guardado correctamente.");
			
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al guardar la información.";
			throw new RuntimeException(msjError);
		}
		

		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : Eliminar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 */
	public RespuestaVo eliminarPaciente(String idPacienteStr) {
		log.debug("Inicio - Facade");
		
		RespuestaVo result = null;
		Integer idPaciente = null;
		String msjError = null;
		
		if(idPacienteStr == null || !idPacienteStr.trim().equals("")) {
			idPaciente = new Integer(idPacienteStr);
		}
		
		
		try{
			pacienteAppService.eliminarPaciente(idPaciente);
			result=new RespuestaVo();
			result.setMensaje("Se borró el paciente.");
			
		}catch (Exception ex) {
			msjError="Ocurrió un error al eliminar el paciente.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
	
	/**
	 * 
	 * Proposito : Recuperar un paciente de la BD
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 * @return	PacienteDto			- Objeto con la informacion del paciente
	 */
	public PacienteDto recuperarPaciente(String idPacienteStr) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		PacienteDto pacienteDto = null;

		try{
			
			if(idPacienteStr == null || idPacienteStr.trim().equals("")) 
				throw new RuntimeException("El identificador del paciente no puede ser nulo.");
			
			idPaciente = new Integer(idPacienteStr);			
			pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
			
			if(pacienteDto == null) 
				throw new RuntimeException("No se encontró información del paciente.");
			
		}catch (Exception ex) {
			log.error("ex.getMessage() : " + ex.getMessage(),ex);
			throw new RuntimeException(ex.getMessage());
		}
			
		log.debug("Fin - Facade");
		return pacienteDto;
	}
	
	/**
	 * 
	 * Proposito :	Actualizar la informacion de un paciente 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/10/2017
	 * @param idPacienteStr
	 * @param idIndividuoStr
	 * @param idMedicoStr
	 * @param pesoStr
	 * @param tallaStr
	 * @param estaturaStr
	 * @param imcStr
	 * @param lipidosStr
	 * @param carbohidratosStr
	 * @param proteinasStr
	 */
	public RespuestaVo actualizarInformacionPaciente(String idPacienteStr, String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = null;
		Integer idIndividuo = null;
		Integer idMedico = null;
		RespuestaVo result = null;
		double peso;
		double talla;
		double estatura;
		double imc;
		double lipidos;
		double carbohidratos;
		double proteinas;
		Integer idPaciente;
		String msjError = null;
		
		if(idPacienteStr == null || idPacienteStr.trim().equals("")) {
			throw new RuntimeException();
		}
		
		
		idPaciente = new Integer(idPacienteStr);
		pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
		
		//Validar que atributos deben ser obligatorios
		if(idIndividuoStr != null && !idIndividuoStr.trim().equals("")) {
			idIndividuo = new Integer(idIndividuoStr);
			pacienteDto.setIdIndividuo(idIndividuo);
		}
		if(idMedicoStr != null && !idMedicoStr.trim().equals("")) {
			idMedico = new Integer(idMedicoStr);
			pacienteDto.setIdMedico(idMedico);
		}
		if(pesoStr != null && !pesoStr.trim().equals("")) {
			peso = Double.parseDouble(pesoStr);
			pacienteDto.setPeso(peso);
		}
		
		if(tallaStr != null && !tallaStr.trim().equals("")) {
			talla = Double.parseDouble(tallaStr);
			pacienteDto.setTalla(talla);
		}
		
		if(estaturaStr != null && !estaturaStr.trim().equals("")) {
			estatura = Double.parseDouble(estaturaStr);
			pacienteDto.setEstatura(estatura);
		}
		
		if(imcStr != null && !imcStr.trim().equals("")) {
			imc = Double.parseDouble(imcStr);
			pacienteDto.setImc(imc);
		}
		
		if(lipidosStr != null && !lipidosStr.trim().equals("")) {
			lipidos = Double.parseDouble(lipidosStr);
			pacienteDto.setLipidos(lipidos);
		}
		
		if(carbohidratosStr != null && !carbohidratosStr.trim().equals("")) {
			carbohidratos = Double.parseDouble(carbohidratosStr);
			pacienteDto.setCarbohidratos(carbohidratos);
		}
		
		if(proteinasStr != null && !proteinasStr.trim().equals("")) {
			proteinas = Double.parseDouble(proteinasStr);
			pacienteDto.setProteinas(proteinas);
		}
		
		try {
			pacienteAppService.actualizarInformacionPaciente(pacienteDto);
			
			result = new RespuestaVo();
			
			result.setMensaje("Se actualizó correctamente el paciente.");
		}catch (Exception ex) {
			msjError="Ha ocurrido un error al actualizar la información.";
			throw new RuntimeException(msjError);
		}
		
		log.debug("Fin - Facade");
		return result;
	}

}
