package mx.escom.tt.diabetes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.UsuarioDao;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@CommonsLog
@Service
public class UsuarioAppService {

	@Autowired UsuarioDao usuarioDao;
	
	/**
	 * Proposito : Recuperar la informacion de un Usuario por medio de su identificador.
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 08/10/2017
	 * @param idUsuario				- Identificador del usuario.
	 * @return	UsuarioDto			- Objeto con la informacion del usuario.	
	 * @throws RuntimeException		- Si ocurre un error durante la ejecucion.
	 */
	public UsuarioDto recuperarUsuarioPorIdAppService(Integer idUsuario) throws RuntimeException{
		log.debug("Inicio - Service");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		
		{//Validaciones 
			if(idUsuario == null) {
				msjEx = "El identificador del usuario no puede ser nulo";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			
			usuarioDto = usuarioDao.recuperarUsuarioPorId(idUsuario);
			
			if(usuarioDto == null) {
				msjEx = "No se encontraron registros para el usuario con Id : " + idUsuario + "." ;
				throw new RuntimeException(msjEx);
			}
			
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + Constants.SALTO_LINEA + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return usuarioDto;
	}
	
}
