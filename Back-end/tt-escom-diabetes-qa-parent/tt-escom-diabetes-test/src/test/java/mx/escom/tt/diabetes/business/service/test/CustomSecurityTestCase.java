package mx.escom.tt.diabetes.business.service.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.CustomSecurity;
import mx.escom.tt.diabetes.model.dto.DietaDto;


@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class CustomSecurityTestCase {

	@Autowired private CustomSecurity customSecurity;
	
	@Test
	public void createSecurityTokenTestCase() {
		log.debug("Inicio - Test");
		
		String username = "edgar.hurtado";
		String decodeString = null;
		try{
			
			decodeString = customSecurity.createSecurityToken(username);
			
			if(!StringUtils.isEmpty(decodeString)) {
				log.debug("decodeString : " + decodeString);
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
	
	@Test
	public void decodeSecurityTokenTestCase() {
		log.debug("Inicio - Test");
		
		String response = null;
		String token = "RWRnYXItMTMvMDUvMjAxOA";

		try{
			
			response = customSecurity.decodeSecurityToken(token);
			
			if(!StringUtils.isEmpty(response)) {
				log.debug("response : " + response);
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
}
