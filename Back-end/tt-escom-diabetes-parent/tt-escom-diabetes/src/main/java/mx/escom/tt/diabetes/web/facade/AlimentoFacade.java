package mx.escom.tt.diabetes.web.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.AlimentoAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;

@Service
@CommonsLog
public class AlimentoFacade {

	@Autowired AlimentoAppService alimentoAppService;
	
	/**
	 * Proposito : Recuperar una lista de alimentos por medio del tipo de alimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @param tipoAlimento						-	Tipo de alimento que se quiere recuperar
	 * @return List<AlimentoDto>				-	Lista de almentos que cumplen con el criterio de busqueda
	 * @throws RuntimeException					-	Si ocurre un error durante al aejecucion
	 */
	public List<AlimentoDto> recuperarListaAlimentoPorTipoAlimento(String tipoAlimento) throws RuntimeException{
		log.debug("Inicio - Facade");
		List<AlimentoDto> result = null;
		String msjEx = null;
		
		if(tipoAlimento == null || tipoAlimento.equals(Constants.CADENA_VACIA)) {
			msjEx = "El tipo de alimento no puede ser nulo o vacío.";
			throw new RuntimeException(msjEx);
		}
		
		try {
			
			result = alimentoAppService.recuperarAlimentosPorTipoAlimento(tipoAlimento);
			
		}catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Facade");
		return result;
	}
}
