package mx.escom.tt.diabetes.business.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.UsuarioAppService;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class UsuarioAppServiceTestCase {

	@Autowired UsuarioAppService usuarioAppService; 
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarUsuarioPorIdAppService() de la clase UsuarioAppService. 
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 08/10/2017
	 * @see UsuarioAppService#recuperarUsuarioPorIdAppService(Integer)
	 */
	@Test
	public void recuperarUsuarioPorIdAppServiceTestCase() {
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = null;
		Integer idUsuario = 7;
		
		usuarioDto = usuarioAppService.recuperarUsuarioPorIdAppService(idUsuario);

		if(usuarioDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + xStream.toXML(usuarioDto));
		}
		
		log.debug("Fin - Test");
	}
}
