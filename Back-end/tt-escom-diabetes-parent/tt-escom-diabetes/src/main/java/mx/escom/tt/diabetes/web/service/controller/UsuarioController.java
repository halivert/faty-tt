package mx.escom.tt.diabetes.web.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
import mx.escom.tt.diabetes.web.vo.UsuarioVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/")
public class UsuarioController {
	
	@Autowired UsuarioFacade usuarioFacade;
	
	/**
	 * Proposito : Recuperar la informacion de un usuario por medio de su Identificador.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idUsuario			-	Identificador del usuario.
	 * @return ResponseEntity	-	Respuesta despues de la ejecucion del metodo.
	 */
	@RequestMapping(value = "/usuarios/{idUsuario}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarUsuario(@PathVariable("idUsuario") String idUsuario) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaErrorVo = null;
		UsuarioVo usuarioVo = null;
		
		try {
			
			usuarioVo = usuarioFacade.recuperarUsuarioPorId(idUsuario);
			result = new ResponseEntity<UsuarioVo>(usuarioVo, HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
	
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "/usuarios/{idUsuario}/password", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> reestablecerPassword(@RequestBody UsuarioVo usuarioVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaErrorVo = null;
		RespuestaVo respuestaVo = null;
		try {
			
			respuestaVo = usuarioFacade.reestablecerPassword(usuarioVo);
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

}
