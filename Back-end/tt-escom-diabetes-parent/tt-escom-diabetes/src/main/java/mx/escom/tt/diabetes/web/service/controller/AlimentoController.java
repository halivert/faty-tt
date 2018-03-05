package mx.escom.tt.diabetes.web.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;
import mx.escom.tt.diabetes.web.facade.AlimentoFacade;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/alimentos")
public class AlimentoController {
	
	@Autowired AlimentoFacade alimentoFacade;

	@RequestMapping(value = "/{tipoAlimento}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarAlimentosPorTipoAlimento(@PathVariable("tipoAlimento") String tipoAlimento) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<AlimentoDto> listaAlimentoDto = null;
		RespuestaVo respuestaError=null;
		
		try {
			listaAlimentoDto = alimentoFacade.recuperarListaAlimentoPorTipoAlimento(tipoAlimento);	
			result = new ResponseEntity<List<AlimentoDto>>(listaAlimentoDto, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaVo();
			respuestaError.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
}
