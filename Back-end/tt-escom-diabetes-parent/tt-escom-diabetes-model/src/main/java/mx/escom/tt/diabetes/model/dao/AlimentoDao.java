package mx.escom.tt.diabetes.model.dao;

import java.util.List;

import mx.escom.tt.diabetes.model.dto.AlimentoDto;

public interface AlimentoDao {
	
	/**
	 * Proposito : Recuperar una lista de alimentos por medio del tipo de alimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @param tipoAlimento						-	Tipo de alimento que se quiere recuperar
	 * @return List<AlimentoDto>				-	Alimentos que cumple con el criterio de busqueda
	 * @throws RuntimeException					-	Si ocurre un error en la persistencia de datos
	 */
	public List<AlimentoDto> recuperarAlimentosPorTipoAlimento(String tipoAlimento) throws RuntimeException;

}
