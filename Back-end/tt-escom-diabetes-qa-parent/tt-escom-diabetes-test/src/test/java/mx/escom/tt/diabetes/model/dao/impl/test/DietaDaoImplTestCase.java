package mx.escom.tt.diabetes.model.dao.impl.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.DietaDao;
import mx.escom.tt.diabetes.model.dto.DietaDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class DietaDaoImplTestCase {
	
	
	@Autowired DietaDao dietaDao;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarDietaPorId()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 */
	@Test
	public void recuperarDietaPorIdTestCase() {
		log.debug("Inicio - Test");
		
		Integer idDieta = 1;
		DietaDto dietaDto = null;
		try{
			
			dietaDto = dietaDao.recuperarDietaPorId(idDieta);
			
			if(dietaDto != null) {
				log.debug(dietaDto.toString());
			}
			
		}catch(RuntimeException rtex) {
			log.debug("rtex.getMessage() : " + rtex.getMessage());
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarDietaPorIdPaciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 */
	@Test
	public void recuperarDietaPorIdPacienteTestCase() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 126;
		List<DietaDto> dietaDtoList = null;
		try{
			
			dietaDtoList = dietaDao.recuperarDietaPorIdPaciente(idPaciente);
			
			if(dietaDtoList != null) {
				XStream xStream = new XStream();
				log.debug(xStream.toXML(dietaDtoList));
			}
			
		}catch(RuntimeException rtex) {
			log.debug("rtex.getMessage() : " + rtex.getMessage());
		}
		log.debug("Fin - Test");
	}

}
