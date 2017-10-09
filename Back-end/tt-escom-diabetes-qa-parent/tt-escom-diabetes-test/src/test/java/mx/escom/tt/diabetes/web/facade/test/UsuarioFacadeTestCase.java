package mx.escom.tt.diabetes.web.facade.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
import mx.escom.tt.diabetes.commons.utils.Constants;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class UsuarioFacadeTestCase {
	
	@Autowired UsuarioFacade usuarioFacade;
	
	
	@Test
	public void recuperarUsuarioPorIdFacadeTestCase() {
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = null;
		String idUsuarioStr = "1";
		
		usuarioDto = usuarioFacade.recuperarUsuarioPorIdFacade(idUsuarioStr);

		if(usuarioDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + Constants.SALTO_LINEA + xStream.toXML(usuarioDto));
		}
		
		log.debug("Fin - Test");
	}
	
}
