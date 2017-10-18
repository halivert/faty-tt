package mx.escom.tt.diabetes.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.model.dto.PacienteDto;

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
	public void guardarPaciente(String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = new PacienteDto();
		Integer idIndividuo = null;
		Integer idMedico = null;
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
		if(idMedicoStr != null && idMedicoStr.trim().equals("")) {
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
		
		pacienteAppService.guardarPaciente(pacienteDto);
		log.debug("Fin - Facade");
	}
	
	/**
	 * 
	 * Proposito : Eliminar un paciente de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 */
	public void eliminarPaciente(String idPacienteStr) {
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		
		if(idPacienteStr == null || !idPacienteStr.trim().equals("")) {
			idPaciente = new Integer(idPacienteStr);
		}
		
		pacienteAppService.eliminarPaciente(idPaciente);
		
		log.debug("Fin - Facade");
	}
	
	/**
	 * 
	 * Proposito : Recuperar un paciente de la BD
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPacienteStr			- Identificador del paciente
	 * @return	PacienteDto			- Objeto con la informacion del paciente
	 */
	public PacienteDto recuperarPaciente(String idPacienteStr) {
		log.debug("Inicio - Facade");
		
		Integer idPaciente = null;
		PacienteDto pacienteDto = null;
		
		if(idPacienteStr == null || idPacienteStr.trim().equals("")) {
			throw new RuntimeException();
		}
		idPaciente = new Integer(idPacienteStr);
		pacienteDto = pacienteAppService.recuperarPaciente(idPaciente);
		
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
	public void actualizarInformacionPaciente(String idPacienteStr, String idIndividuoStr, String idMedicoStr, String pesoStr, String tallaStr,
			String estaturaStr, String imcStr, String lipidosStr, String carbohidratosStr, String proteinasStr) {
		
		log.debug("Inicio - Facade");
		
		PacienteDto pacienteDto = null;
		Integer idIndividuo = null;
		Integer idMedico = null;
		double peso;
		double talla;
		double estatura;
		double imc;
		double lipidos;
		double carbohidratos;
		double proteinas;
		Integer idPaciente;
		
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
		
		
		pacienteAppService.actualizarInformacionPaciente(pacienteDto);
		
		log.debug("Fin - Facade");
	}

}
