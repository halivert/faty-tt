package mx.escom.tt.diabetes.web.facade.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.facade.TokenMedicoFacade;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class TokenMedicoFacadeTestCase {
	
	
	@Autowired TokenMedicoFacade tokenMedicoFacade;
	
	@Test
	public void enviarTokenReestablecerPassword() {
		log.debug("Inicio - Test");
		String[] destinatario = {"edgarivans@gmail.com"};
		
		try {
			tokenMedicoFacade.enviarTokenReestablecerPassword(destinatario);
		}catch(Exception ex) {
			log.debug("ex : " + ex.getMessage()); 
		}
		log.debug("Fin - Test");
	}

}
