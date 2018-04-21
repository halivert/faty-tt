package mx.escom.tt.diabetes.web.facade.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.DietaDto;
import mx.escom.tt.diabetes.web.facade.DietaFacade;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class DietaFacadeTestCase {

	@Autowired DietaFacade dietaFacade;
	
	@Test
	public void recuperarDietaPorIdPacienteTestCase() {
		log.debug("Inicio - Test");
		
		String idPaciente = "126";
		List<DietaDto> dietaDtoList = null;
	
		try{
			dietaDtoList = dietaFacade.recuperaDietaPorIdPaciente(idPaciente);
			if(dietaDtoList != null) {
				XStream xStream = new XStream();
				log.debug(xStream.toXML(dietaDtoList));
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
}
