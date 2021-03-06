package mx.escom.tt.diabetes.model.dao;

import java.util.List;

import mx.escom.tt.diabetes.commons.vo.UltimoHistorialClinicoVo;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;

public interface HistorialClinicoDao {
	
	/**
	 * Proposito : Guardar un registro de historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoDto		-	Informacion con el registro del historial clinico
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public void guardarHistorialClinico(HistorialClinicoDto historialClinicoDto) throws RuntimeException;

	/**
	 * Proposito : Recuperar la informacion de un registro de historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idHistorialClinico		-	Identificador del historial clinico a buscar
	 * @return HistorialClinicoDto		-	Informacion del registro buscado
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public HistorialClinicoDto recuperarHistorialClinicoPorId(Integer idHistorialClinico) throws RuntimeException;
	
	/**
	 * Proposito : Obtener el ultimo historial clinico registrado de un paciente por medio de su id
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/02/2018
	 * @param idPaciente				-	Identificador del paciente
	 * @return UltimoHistorialClinicoVo	-	Informacion del registro buscado
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion
	 */
	public UltimoHistorialClinicoVo recuperarUltimoHistorialClinicoPorIdPaciente(Integer idPaciente) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar la lista de registros de historial clinico de un Usuario-Paciente. 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idUsuario						-	Identificador del Usuario-Paciente.
	 * @return List<HistorialClinicoDto>	-	Lista de registros de historial clinico del Usuario-Paciente. 
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion.
	 */
	public List<HistorialClinicoDto> recuperarListaHistorialClinicoPorIdPaciente(Integer idPaciente) throws RuntimeException;

	/**
	 * Proposito : Actualizar la informacion de un histotial clinico 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 05/02/2018
	 * @param historialClinicoDto			-	Objeto con la informacion que se quiere actualizar 
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion
	 */
	public void actualizarHistorialClinico(HistorialClinicoDto historialClinicoDto) throws RuntimeException;

}
