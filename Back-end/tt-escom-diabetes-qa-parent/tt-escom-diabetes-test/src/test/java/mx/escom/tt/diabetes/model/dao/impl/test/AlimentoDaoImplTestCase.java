package mx.escom.tt.diabetes.model.dao.impl.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.AlimentoDao;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class AlimentoDaoImplTestCase {
	
	@Autowired AlimentoDao alimentoDao;
	
	
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarAlimentosPorTipoAlimento
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 * @see AlimentoDao#recuperarAlimentosPorTipoAlimento(String)
	 */
	@Test
	public void recuperarAlimentosPorTipoAlimentoTestCase() {
		log.debug("Inicio - Test");
		
		List<AlimentoDto> alimentoDtoList = null;
		String tipoAlimento = "Verdura";
		
		try{
			alimentoDtoList = alimentoDao.recuperarAlimentosPorTipoAlimento(tipoAlimento);
			
			if(alimentoDtoList != null) {
				log.debug("alimentoDtoList.size() : " + alimentoDtoList.size());
			}
			
		}catch(RuntimeException rtex) {
			log.debug("rtex.getMessage() : " + rtex.getMessage());
		}
		log.debug("Fin - Test");
	}

}
