package mx.escom.tt.diabetes.business.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.MedicoAppService;
import mx.escom.tt.diabetes.model.dto.MedicoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class MedicoAppServiceTestCase {
	
	@Autowired MedicoAppService medicoAppService;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarMedico()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 */
	@Test
	public void recuperarMedicoPorId() {
		log.debug("Inicio - Test");
		
		MedicoDto medicoDto = null;
		
		Integer idMedico = 2;
		
		medicoDto = medicoAppService.recuperarMedico(idMedico);

		if(medicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(medicoDto): " + xStream.toXML(medicoDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardarMedico()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 */
	@Test
	public void guardarMedico() {
		log.debug("Inicio - Test");
		
		MedicoDto medicoDto = new MedicoDto();
		
		medicoDto.setIdUsuario(2);
		medicoDto.setCedulaProfesional("SDFSD");
		
		medicoAppService.guardarMedico(medicoDto);
		
		log.debug("Fin - Test");
	}

}
