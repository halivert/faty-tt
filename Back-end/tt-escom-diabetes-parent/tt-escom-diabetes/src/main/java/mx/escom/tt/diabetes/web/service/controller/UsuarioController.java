package mx.escom.tt.diabetes.web.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/service/usuarios")
public class UsuarioController {
	
	@Autowired UsuarioFacade usuarioFacade;

	
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("idUsuario") String idUsuario) {
		log.debug("Inicio - Controller");
		ResponseEntity<?> result = null;
		UsuarioDto usuarioDto = null;
		try {

			usuarioDto = usuarioFacade.recuperarUsuarioPorIdFacade(idUsuario);
			
			log.debug("usuarioDto.toString(): " + usuarioDto.toString());

			result = new ResponseEntity<UsuarioDto>(usuarioDto, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("ex.getMessage(): " + ex.getMessage());
			log.error(ex.getMessage());
			result = new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
}
