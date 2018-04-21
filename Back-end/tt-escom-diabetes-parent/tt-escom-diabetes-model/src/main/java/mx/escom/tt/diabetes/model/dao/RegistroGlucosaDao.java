package mx.escom.tt.diabetes.model.dao;

import java.sql.Timestamp;
import java.util.List;

import mx.escom.tt.diabetes.commons.vo.RegistroGlucosaCommonVo;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;

public interface RegistroGlucosaDao {

	/**
	 * Proposito : Guardar un nuevo registro de glucosa en la base de datos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param regGlucosaDto					-	Objeto con la informacion que se va a guardar
	 * @throws RuntimeException				- 	Si ocurre un error durante la ejecucion
	 */
	public void guardaRegistroGlucosa(RegistroGlucosaDto regGlucosaDto) throws RuntimeException;
	
	/**
	 * Proposito : Actualizar un registro de glucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param regGlucosaDto					- 	Objeto con la informacion que se va a actualizar
	 * @throws RuntimeException				- 	Si ocurre un error durante la ejecucion
	 */
	public void actualizaRegistroGlucosa(RegistroGlucosaDto regGlucosaDto) throws RuntimeException;
	
	
	/**
	 * Proposito : Recuperar la informacion de un registro por medio de su identificador
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @return RegistroGlucosaDto			-	Objeto con la informacion que se recupero 
	 * @throws RuntimeException				- 	Si ocurre un error durante la ejecucion
	 */
	public RegistroGlucosaDto recuperaRegistroGlucosaPorId(Integer idRegistroGlucosa) throws RuntimeException;

	/**
	 * Proposito : Recuperar una lista de registros por medio del id del paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @return List<RegistroGlucosaDto>		-	Lista de registros asociados a un paciente
	 * @throws RuntimeException				- 	Si ocurre un error durante la ejecucion
	 */
	public List<RegistroGlucosaDto> recuperaListaRegistroGlucosaPorIdPaciente(Integer idPaciente) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar una lista de registros por medio de una busqueda por filtros
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @return List<RegistroGlucosaCommonVo>		-	Lista con los registros que cumplen con la busqueda por filtros
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion
	 */
	public List<RegistroGlucosaCommonVo> recuperaListaRegistroGlucosaPorFiltros(Integer idPaciente, Timestamp fechaInicio, Timestamp fechaFin) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar n numero de registros de glucosa por id paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 14/04/2018
	 * @param idPaciente						-	Identificador del paciente que se quieren recuperar los registros
	 * @param limiteRegistro					-	Numero maximo de registros a recuperar
	 * @return List<RegistroGlucosaCommonVo>		-	Lista con los registros que cumplen con la busqueda por filtros
	 * @throws RuntimeException				-	Si ocurre un error en la persistencia de datos
	 */
	public List<RegistroGlucosaCommonVo> recuperaNRegistroGlucosa(Integer idPaciente, Integer limiteRegistro) throws RuntimeException;
}
