package mx.escom.tt.diabetes.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.AlimentoDao;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;

@CommonsLog
@Service
public class AlimentoAppService {

	@Autowired AlimentoDao alimentoDao;
	
	/**
	 * Proposito : Recuperar una lista de alimentos por medio del tipo de alimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @param tipoAlimento					-	Tipo de alimento 
	 * @return List<AlimentoDto>			-	Lista de alimentos que cumplen con el criterio de busqueda
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo
	 */
	public List<AlimentoDto> recuperarAlimentosPorTipoAlimento(String tipoAlimento) throws RuntimeException{
		log.debug("Inicio - Service");
		
		List<AlimentoDto> listAlimentoDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(tipoAlimento == null) {
				msjEx = "El tipo de alimento no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}	
		try {
			listAlimentoDto = alimentoDao.recuperarAlimentosPorTipoAlimento(tipoAlimento);
			
			if(listAlimentoDto.size() == 0) {
				msjEx = "No se encontraron registros para el tipo de alimento : " + tipoAlimento;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx,ex);
		}
		log.debug("Fin - Service");
		return listAlimentoDto;
	}
}
