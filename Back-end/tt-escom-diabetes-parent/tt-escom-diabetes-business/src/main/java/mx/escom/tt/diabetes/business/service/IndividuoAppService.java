package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@Service
@CommonsLog
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
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws RuntimeException {		
		IndividuoDto individuoDto = null;
		 
		if(idIndividuo == null) {
			throw new RuntimeException();
		}
		
		individuoDto = individuoDao.obtenerIndividuo(idIndividuo);
	

		return individuoDto;
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param individuoDto
	 * @throws RuntimeException
	 */
	public void guardarInvididuo(IndividuoDto individuoDto) throws RuntimeException{
		log.debug("Inicio - Service");
		
		if(individuoDto == null) {
			throw new RuntimeException();
		}
		
		individuoDao.guardarIndividuo(individuoDto);
		
		log.debug("Fin - Service");
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param idIndividuo
	 * @throws RuntimeException
	 */
	public void borrarIndividuo(Integer idIndividuo) throws RuntimeException{
		log.debug("Inicio - Service");
		
		if(idIndividuo == null) {
			throw new RuntimeException();
		}
		individuoDao.borrarIndividuo(idIndividuo);
		log.debug("Fin - Service");
	}
	
	/**
	 * 
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 21/10/2017
	 * @param individuoDto
	 * @throws RuntimeException
	 */
	public void actualizarIndividuo(IndividuoDto individuoDto) throws RuntimeException{
		log.debug("Inicio - Service");
		if(individuoDto == null) {
			throw new RuntimeException();
		}
		
		individuoDao.actualizarIndividuo(individuoDto);
		log.debug("Fin - Service");
	}
	
}
