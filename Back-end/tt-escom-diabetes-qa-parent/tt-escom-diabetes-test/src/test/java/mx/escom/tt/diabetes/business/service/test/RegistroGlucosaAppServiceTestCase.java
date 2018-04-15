package mx.escom.tt.diabetes.business.service.test;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.RegistroGlucosaAppService;
import mx.escom.tt.diabetes.commons.vo.RegistroGlucosaCommonVo;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class RegistroGlucosaAppServiceTestCase {

	@Autowired RegistroGlucosaAppService registroGlucosaAppService;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardarRegistroGlucosaAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaAppService#guardarRegistroGlucosaAppService(RegistroGlucosaDto)
	 */
	@Test
	public void guardarRegistroGlucosaAppService() {
		log.debug("Incio - Test");
		
		Integer idPaciente = 124;
		double azucar = 100;
		Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());
		
		RegistroGlucosaDto registroGlucosaDto = new RegistroGlucosaDto();
		
		{//Se asignan variables al DTO
			registroGlucosaDto.setIdPaciente(idPaciente);
			registroGlucosaDto.setFechaRegistro(fechaRegistro);
			registroGlucosaDto.setAzucar(azucar);
			
		}
		registroGlucosaAppService.guardarRegistroGlucosaAppService(registroGlucosaDto);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo actualizaRegistroGlucosaAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaAppService#actualizaRegistroGlucosaAppService(RegistroGlucosaDto)
	 */
	@Test
	public void actualizaRegistroGlucosaAppService() {
		log.debug("Incio - Test");
		RegistroGlucosaDto registroGlucosaDto = new RegistroGlucosaDto();
		Integer idRegistroGlucosa = 2;
		double azucar = 123;
		
		registroGlucosaDto.setIdRegistroGlucosa(idRegistroGlucosa);
		registroGlucosaDto.setAzucar(azucar);
		
		registroGlucosaAppService.actualizaRegistroGlucosaAppService(registroGlucosaDto);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaRegistroGlucosaPorIdAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaAppService#recuperaListaRegistroGlucosaPorIdPacienteAppService(Integer)
	 */
	@Test
	public void recuperaRegistroGlucosaPorIdAppService() {
		log.debug("Incio - Test");
		RegistroGlucosaDto registroGlucosaDto = null;
		Integer idRegistroGlucosa = 7;
		
		registroGlucosaDto = registroGlucosaAppService.recuperaRegistroGlucosaPorIdAppService(idRegistroGlucosa);
		
		if(registroGlucosaDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(historialClinicoDto): \n" + xStream.toXML(registroGlucosaDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaListaRegistroGlucosaPorIdPacienteAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaAppService#recuperaListaRegistroGlucosaPorIdPacienteAppService(Integer)
	 */
	@Test
	public void recuperaListaRegistroGlucosaPorIdPacienteAppService() {
		log.debug("Incio - Test");
		List<RegistroGlucosaDto> registroGlucosaDto = null;
		Integer idPaciente = 96;
		
		registroGlucosaDto = registroGlucosaAppService.recuperaListaRegistroGlucosaPorIdPacienteAppService(idPaciente);
		log.debug("registroGlucosaDto.size() : " + registroGlucosaDto.size());
		
		if(!registroGlucosaDto.isEmpty()) {
			
			XStream xStream = new XStream();
			log.debug("xStream.toXML(historialClinicoDto): \n" + xStream.toXML(registroGlucosaDto));
		}
		
		log.debug("Fin - Test");
	}
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaNRegistroGlucosaAppService
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 14/04/2018
	 * @see RegistroGlucosaAppService#recuperaNRegistroGlucosaAppService(Integer, Integer)
	 */
	@Test
	public void recuperaNRegistroGlucosaAppServiceTestCase() {
		log.debug("Incio - Test");
		List<RegistroGlucosaCommonVo> registroGlucosaCommonVo = null;
		Integer idPaciente = 3;
		Integer limiteRegistro = 1;
		
		registroGlucosaCommonVo = registroGlucosaAppService.recuperaNRegistroGlucosaAppService(idPaciente, limiteRegistro);
		
		log.debug("registroGlucosaCommonVo.size() : " + registroGlucosaCommonVo.size());
		
		if(!registroGlucosaCommonVo.isEmpty()) {
			
			XStream xStream = new XStream();
			log.debug("xStream.toXML(registroGlucosaCommonVo): \n" + xStream.toXML(registroGlucosaCommonVo));
		}
		
		log.debug("Fin - Test");
	}
	
	
	
	
}
