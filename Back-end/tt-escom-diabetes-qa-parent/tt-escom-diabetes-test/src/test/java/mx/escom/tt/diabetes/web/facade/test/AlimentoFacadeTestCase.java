package mx.escom.tt.diabetes.web.facade.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;
import mx.escom.tt.diabetes.web.facade.AlimentoFacade;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class AlimentoFacadeTestCase {
	
	@Autowired AlimentoFacade alimentoFacade;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarListaAlimentoPorTipoAlimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @see AlimentoFacade#recuperarListaAlimentoPorTipoAlimento(String)
	 */
	@Test
	public void recuperarListaAlimentoPorTipoAlimento() {
		log.debug("Inicio - Test");
		
		List<AlimentoDto> listAlimentoDto = null;
		String tipoAlimento = "verdura";
	
		try{
			listAlimentoDto = alimentoFacade.recuperarListaAlimentoPorTipoAlimento(tipoAlimento);
			
			if(listAlimentoDto != null) {
				XStream xStream = new XStream();
				log.debug("xStream.toXML(listAlimentoDto): " + xStream.toXML(listAlimentoDto));
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}

}
