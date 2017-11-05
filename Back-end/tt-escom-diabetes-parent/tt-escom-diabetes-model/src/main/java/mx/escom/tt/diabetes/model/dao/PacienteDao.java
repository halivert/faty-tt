package mx.escom.tt.diabetes.model.dao;

import mx.escom.tt.diabetes.model.dto.PacienteDto;

public interface PacienteDao {
	
	
	/**
	 * 
	 * Proposito : Metodo para guardar la informacion de un paciente  
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param pacienteDto 			- Objeto de la clase PacienteDto
	 * @throws RuntimeException		- Si ocurre un error en tiempo de ejecucion
	 */
	public void guardarPaciente(PacienteDto pacienteDto) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : Metodo para recuperar la informacion de un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPaciente			- Identificador del paciente
	 * @return PacienteDto			- Informacion del paciente 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion
	 */
	public PacienteDto recuperarPacientePorIdPaciente(Integer idPaciente) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : Metodo para eliminar un paciente de la BD por medio de su identificador
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idPaciente			- Identificador del paciente
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion
	 */
	public void eliminarPacientePorIdPaciente(Integer idPaciente) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : Actualizar la informacion de un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param pacienteDto			- Paciente al que se va a actualizar a informacion 
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion
	 */
	public void actualizarInformacionPaciente(PacienteDto pacienteDto) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar el idPaciente por medio de idIndividuo
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param Integer
	 * @throws RuntimeException		-	Si ocurre un error durante la ejecucion
	 */
	public Integer recuperarIdPacientePorIdIndividuo(Integer idIndividuo) throws RuntimeException;
	

}
