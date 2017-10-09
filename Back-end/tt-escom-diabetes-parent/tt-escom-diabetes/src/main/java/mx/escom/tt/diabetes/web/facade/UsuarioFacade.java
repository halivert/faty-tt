package mx.escom.tt.diabetes.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.UsuarioAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@Service
@CommonsLog
public class UsuarioFacade {
	
	@Autowired UsuarioAppService usuarioAppService;
	
	
	public UsuarioDto recuperarUsuarioPorIdFacade(String idUsuarioStr) {
		log.debug("Inicio - Facade");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		Integer idUsuario = null;
		
		{//Validaciones 
			if(idUsuarioStr == null || idUsuarioStr.trim().equals("")) {
				msjEx="El identificador del usuario no puede ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			
			
			
			idUsuario = new Integer(idUsuarioStr);
			
			usuarioDto = usuarioAppService.recuperarUsuarioPorIdAppService(idUsuario);
			
			
		}catch(RuntimeException rtExc) { 
			throw new RuntimeException(rtExc.getMessage(),rtExc);
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + Constants.SALTO_LINEA + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		
		log.debug("Fin - Facade");
		return usuarioDto; 
	}
	

}
