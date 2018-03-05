package mx.escom.tt.diabetes.business.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.AlimentoAppService;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class AlimentoAppServiceTestCase {

	@Autowired AlimentoAppService alimentoAppService;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarAlimentosPorTipoAlimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @see AlimentoAppService#recuperarAlimentosPorTipoAlimento(String)
	 */
	@Test
	public void recuperarAlimentosPorTipoAlimentoTestCase() {
		log.debug("Inicio - Test");
		
		List<AlimentoDto> listAlimentoDto = null;
		String tipoAlimento = "verdura";
	
		try{
			listAlimentoDto = alimentoAppService.recuperarAlimentosPorTipoAlimento(tipoAlimento);
			if(listAlimentoDto != null) {
				log.debug("listAlimentoDto.size(): " + listAlimentoDto.size());
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
}
