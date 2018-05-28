package mx.escom.tt.diabetes.web.facade;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.DietaAppService;
import mx.escom.tt.diabetes.business.vo.ValoresNutrimentalesDietaVo;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.DietaDto;
import mx.escom.tt.diabetes.web.vo.DietaVo;
import mx.escom.tt.diabetes.web.vo.PacienteVo;

@Service
@CommonsLog
public class DietaFacade {
	
	@Autowired DietaAppService dietaAppService;
	
	/**
	 * Proposito : Guardar una nueva dieta en la base de datos
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,1,0. 30/03/2018
	 * @param idMedico						-	Identificador del medico
	 * @param idPaciente						-	Identificador del paciente
	 * @param dietaVo 						-	Objeto con la informacion de la dieta
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public void guardarDieta(String idMedico, String idPaciente, DietaVo dietaVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		String msjEx = null;
		DietaDto dietaDto = null;
		if(StringUtils.isEmpty(idMedico)) {
			throw new RuntimeException("El identificador del medico no puede ser nulo.");
		}
		if(StringUtils.isEmpty(idPaciente)){
			throw new RuntimeException("El identificador del idPaciente no puede ser nulo.");
		}
		if(StringUtils.isEmpty(dietaVo.getAlimentosDisponibles())) {
			throw new RuntimeException("Debe agregar alimentos a la dieta.");
		}
		if(StringUtils.isEmpty(dietaVo.getDescripcion())) {
			throw new RuntimeException("Debe agregar una descripcion a la dieta.");
		}
		if(StringUtils.isEmpty(dietaVo.getCaloriasDesayuno())) {
			throw new RuntimeException("Las calorias en el desayuno no pueden ser nulo o vacía.");
		}
		if(StringUtils.isEmpty(dietaVo.getCaloriasC1())) {
			throw new RuntimeException("Las calorias en la primera colación no pueden ser nulo o vacía.");
		}
		if(StringUtils.isEmpty(dietaVo.getCaloriasComida())) {
			throw new RuntimeException("Las calorias en la comida no pueden ser nulo o vacía.");
		}
		if(StringUtils.isEmpty(dietaVo.getCaloriasC2())) {
			throw new RuntimeException("Las calorias en la segunda colación no pueden ser nulo o vacía.");
		}
		if(StringUtils.isEmpty(dietaVo.getCaloriasCena())) {
			throw new RuntimeException("Las calorias en la cena no pueden ser nulo o vacía.");
		}
		
		try {	
			{//SE CREA EL DTO A PARTIR DE LA INFORMACION
				dietaDto = new DietaDto();
				dietaDto.setIdMedico(Integer.valueOf(idMedico));
				dietaDto.setIdPaciente(Integer.valueOf(idPaciente));
				dietaDto.setDescripcion(dietaVo.getDescripcion());
				dietaDto.setAlimentosDisponibles(dietaVo.getAlimentosDisponibles());
				dietaDto.setCaloriasDesayuno(Double.valueOf(dietaVo.getCaloriasDesayuno()));
				dietaDto.setCaloriasC1(Double.valueOf(dietaVo.getCaloriasC1()));
				dietaDto.setCaloriasComida(Double.valueOf(dietaVo.getCaloriasComida()));
				dietaDto.setCaloriasC2(Double.valueOf(dietaVo.getCaloriasC2()));
				dietaDto.setCaloriasCena(Double.valueOf(dietaVo.getCaloriasCena()));
			}
			dietaAppService.guardarDietaAppService(dietaDto);
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
	}
	
	/**
	 * Proposito : Recuperar una dieta por medio de su identificador
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param idDieta					-	Identificador de la dieta que se quiere recuperar
	 * @return DietaDto					-	Dieta que se recupero 		
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo
	 */
	public DietaDto recuperarDietaPorId(String idDieta) throws RuntimeException{
		log.debug("Inicio - Facade");
	
		String msjEx = null;
		DietaDto dietaDto = null;
		
		if(idDieta == null || idDieta.trim().equals("")) {
			throw new RuntimeException("El identificador de la dieta no puede ser nula.");
		}
		
		try {
			dietaDto = dietaAppService.recuperarDietaPorIdAppService(Integer.valueOf(idDieta));
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la dieta." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return dietaDto;
	}
	
	/**
	 * Proposito : Recuperar una lista de dietas asociadas a un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 * @param idPaciente				-	Identificador del paciente
	 * @return List<DietaDto>			-	Lista de dietas recuperadas
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public List<DietaDto> recuperaDietaPorIdPaciente(String idPaciente) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		String msjEx = null;
		List<DietaDto> dietaDtoList = null;
		
		if(idPaciente == null || idPaciente.trim().equals("")) {
			throw new RuntimeException("El identificador del paciente no puede ser nulo.");
		}
		
		try {
			dietaDtoList = dietaAppService.recuperarDietaPorIdPacienteAppService(Integer.valueOf(idPaciente));
			
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la dieta." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return dietaDtoList;
	}
	
	/**
	 * Proposito : Calcular los valores nutrimentalos que se deben asignar a la dieta de un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 22/04/2018
	 * @param pacienteVo						-	Objeto con informacion del paciente
	 * @return ValoresNutrimentalesDietaVo	-	Objeto con la informacion de los valores nutrimentales asignados para armar una dieta
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo
	 */
	public ValoresNutrimentalesDietaVo calcularValoresNutrimentalesDieta(PacienteVo pacienteVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		
		String msjEx = null;
		Integer edad = null;
		Double peso = null;
		Double estatura = null;
		String actividadFisica = null;
		Integer sexo = null;
		Double pLipidos = null;
		Double pCarbohidratos = null;
		Double pProteinas = null;
		ValoresNutrimentalesDietaVo valoresNutrimentalesDietaVo = null;
		
		
		if(pacienteVo == null) {
			throw new RuntimeException("La información del paciente no puede ser nula.");
		}
		log.debug("STRING " + pacienteVo.toString());
		
		if(StringUtils.isEmpty(pacienteVo.getEdad()) || !StringUtils.isNumeric(pacienteVo.getEdad())) {
			throw new RuntimeException("La edad del paciente no puede ser nula.");
		}else {
			edad = Integer.parseInt(pacienteVo.getEdad());
		}
		
		if(StringUtils.isEmpty(pacienteVo.getPeso())) {
			throw new RuntimeException("El peso del paciente no puede ser nulo.");
		}else {
			peso = Double.parseDouble(pacienteVo.getPeso());
		}
		
		if(StringUtils.isEmpty(pacienteVo.getEstatura())) {
			throw new RuntimeException("La estatura del paciente no puede ser nula.");
		}else {
			estatura = Double.parseDouble(pacienteVo.getEstatura());
		}
		if(pacienteVo.getActividad() == null) {
			throw new RuntimeException("La actividad física del paciente no puede ser nula.");
		}else {
			actividadFisica = pacienteVo.getActividad();
		}
		if(StringUtils.isEmpty(pacienteVo.getSexo())) {
			throw new RuntimeException("El sexo del paciente no puede ser nula.");
		}else {
			sexo = Integer.parseInt(pacienteVo.getSexo());
		}
		if(StringUtils.isEmpty(pacienteVo.getPorcentajeLipidos())) {
			throw new RuntimeException("El porcentaje de lípidos no puede ser nulo.");
		}else {
			pLipidos = Double.parseDouble(pacienteVo.getPorcentajeLipidos());
		}
		if(StringUtils.isEmpty(pacienteVo.getPorcentajeProteinas())) {
			throw new RuntimeException("El porcentaje de proteinas no puede ser nulo.");
		}else {
			pProteinas = Double.parseDouble(pacienteVo.getPorcentajeProteinas());
		}
		if(StringUtils.isEmpty(pacienteVo.getPorcentajeCarbohidratos())) {
			throw new RuntimeException("El porcentaje de carbohidratos no puede ser nulo.");
		}else {
			pCarbohidratos = Double.parseDouble(pacienteVo.getPorcentajeCarbohidratos());
		}
		
		try {
			
			valoresNutrimentalesDietaVo = dietaAppService.calcularValoresNutrimentalesDietaAppService(edad, peso, estatura, actividadFisica, sexo, pLipidos, pCarbohidratos, pProteinas);
			
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la dieta." + ex.getMessage();
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Facade");
		return valoresNutrimentalesDietaVo;
	}

}
