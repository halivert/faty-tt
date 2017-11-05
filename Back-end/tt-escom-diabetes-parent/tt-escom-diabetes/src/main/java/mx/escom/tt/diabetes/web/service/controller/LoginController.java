package mx.escom.tt.diabetes.web.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.vo.UsuarioLoginVo;
import mx.escom.tt.diabetes.web.vo.UsuarioVo;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@CommonsLog
@RestController
@RequestMapping(value = "/session/")
public class LoginController {

	@Autowired UsuarioFacade usuarioFacade;
	
	/**
	 * Proposito : Autenticar las credenciales proporcionadas por el usuario con las almacenadas en la BD, para poder iniciar sesion
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param UsuarioLoginVo		-	Vo con las credenciales del usuario 
	 * @return ResponseEntity		-	Respuesta de la solicitud.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> autenticar(@RequestBody UsuarioLoginVo usuarioVo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaErrorVo = null;
		RespuestaVo respuestaVo = null;
		
		try {
			
			respuestaVo = usuarioFacade.login(usuarioVo);
			result = new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * 
	 * Proposito : Guardar un usuario en la BD, y guardar tambien su informacion con base en su perfil
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param usuarioVo			-	Informacion del usuario
	 * @return
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarUsuario(@RequestBody UsuarioVo usuarioVo) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
	
		RespuestaVo respuestaVo = null;
		
		try {
			log.debug("usuarioVo : " + usuarioVo.toString());
			respuestaVo = usuarioFacade.guardarUsuario(usuarioVo);
			result = new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaVo = new RespuestaVo();
			respuestaVo.setRespuesta("ERROR");
			respuestaVo.setMensaje(ex.getMessage());
			
			result=new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	
	
}
