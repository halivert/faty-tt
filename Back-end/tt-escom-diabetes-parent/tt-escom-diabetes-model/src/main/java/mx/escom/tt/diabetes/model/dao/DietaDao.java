package mx.escom.tt.diabetes.model.dao;

import java.util.List;

import mx.escom.tt.diabetes.model.dto.DietaDto;

public interface DietaDao {
	
	/**
	 * Proposito : Guardar una dieta creada por un medico
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param dietaDto						-	Informacion con la dieta que se quiere guardar
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo
	 */
	public void guardarDieta(DietaDto dietaDto) throws RuntimeException;

	/**
	 * Proposito : Recuperar una dieta por medio de su identificador
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param idDieta						-	Identificador de la dieta que se quiere recuperar
	 * @return DietaDto						-	Dieta recuperada 	
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public DietaDto recuperarDietaPorId(Integer idDieta) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar una lista de dietas asociadas a un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018	
	 * @param idPaciente					-	Identificador del paciente de quien se quieren recuperar las dietas
	 * @return List<DietaDto>				-	Lista con las dietas asociadas al paciente
	 * @throws RuntimeException				-	Si ocurre un error en la persistencia de datos
	 */
	public List<DietaDto> recuperarDietaPorIdPaciente(Integer idPaciente) throws RuntimeException;
	
}
