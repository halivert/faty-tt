package mx.escom.tt.diabetes.model.dao;

import java.util.List;

import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.model.dto.MedicoDto;

public interface MedicoDao {

	/**
	 * Proposito : Guardar la informacion de un medico en la BD
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 * @param medicoDto		-	Objeto con la informacion de un medico 
	 * @throws RuntimeException	-	Si ocurre un error durante la ejecucion
	 */
	public void guardarMedico(MedicoDto medicoDto) throws RuntimeException;
	
	/**
	 * Proposito :	Recuperar la informacion de un medico 	 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 * @param idMedico		-	Identificador del medico
	 * @return 
	 * @throws RuntimeException
	 */
	public MedicoDto recuperarMedico(Integer idMedico) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar informacion de un medico por el id de Individuo
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param idIndividuo			-	idIndividuo del medico
	 * @return Integer				-	idMedico
	 * @throws RuntimeException		-	Si ocurre un error durante la ejecucion
	 */
	//TODO- Eliminar metodo	
	public Integer recuperarIdMedicoPorIdIndividuo(Integer idIndividuo) throws RuntimeException;
	
	
	/**
	 * Proposito : Recuperar una lista de pacientes asociados a un medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 * @param idMedico					-	Identificador del medico
	 * @return List<MedicoPacientesVo>	-	Lista de pacientes tratados por el medico
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	//TODO- Eliminar metodo
	public List<MedicoPacientesVo> recuperarPacientesDeMedico(Integer idMedico) throws RuntimeException;
	
}
