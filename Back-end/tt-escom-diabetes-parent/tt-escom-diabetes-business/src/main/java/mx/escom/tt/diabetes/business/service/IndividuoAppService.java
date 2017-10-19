package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@Service
public class IndividuoAppService {

	@Autowired IndividuoDao individuoDao;
	/**
	 * Proposito : Recuperar la informacion de un Individuo por medio de su identificador.
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 08/10/2017
	 * @param idUsuario				- Identificador del usuario.
	 * @return	UsuarioDto			- Objeto con la informacion del usuario.	
	 */
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws Exception {		
		IndividuoDto individuoDto = null;
		String msjEx = null;
		 
		try {
			individuoDto = individuoDao.obtenerIndividuo(idIndividuo);
			if (individuoDto == null) {
				msjEx = "No se encontraron registros para el individuo con ID : " + idIndividuo;
				throw new Exception(msjEx);
			}
		} catch (Exception ex) {
			msjEx = "No se ha podido obtener el individuo con ID: " + idIndividuo;
			throw new Exception(msjEx);
		}

		return individuoDto;
	}
	
}
