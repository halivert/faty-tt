package mx.escom.tt.diabetes.web.facade.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.facade.RegistroGlucosaFacade;
import mx.escom.tt.diabetes.web.vo.RegistroGlucosaVo;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class RegistroGlucosaFacadeTestCase {
	
	@Autowired RegistroGlucosaFacade registroGlucosaFacade;
	
	
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardaRegistroGlucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaFacade#guardaRegistroGlucosa(RegistroGlucosaVo)
	 */
	@Test
	public void guardarRegistroGlucosa() {
		log.debug("Inicio - Test");
		RegistroGlucosaVo registroGlucosaVo = new RegistroGlucosaVo();

		String idRegistroGlucosa = null;
		String idPaciente = "127";
		
		String azucar = "97";
		String fechaActualizacion = null;
		
		String fechaRegistro = "16/02/2018";
		
		
		//Se arma el VO
		registroGlucosaVo.setIdRegistroGlucosa(idRegistroGlucosa);
		registroGlucosaVo.setIdPaciente(idPaciente);
		registroGlucosaVo.setAzucar(azucar);
		registroGlucosaVo.setFechaRegistro(fechaRegistro);
		registroGlucosaVo.setFechaActualizacion(fechaActualizacion);

		try{
				
			registroGlucosaFacade.guardaRegistroGlucosa(registroGlucosaVo);
		}catch (RuntimeException ex) {
			log.debug("ex : " + ex.getMessage());
		} 
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo actualizaRegistroGlucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaFacade#actualizaRegistroGlucosa(RegistroGlucosaVo)
	 */
	@Test
	public void actualizaRegistroGlucosa() {
		log.debug("Inicio - Test");
		RegistroGlucosaVo registroGlucosaVo = new RegistroGlucosaVo();

		String idRegistroGlucosa = "18";
		String azucar = "97";
		String fechaRegistro = null;
		String fechaActualizacion = null;
		
		//Se arma el VO
		registroGlucosaVo.setIdRegistroGlucosa(idRegistroGlucosa);
		registroGlucosaVo.setAzucar(azucar);
		registroGlucosaVo.setFechaRegistro(fechaRegistro);		
		registroGlucosaVo.setFechaActualizacion(fechaActualizacion);

		try{
			registroGlucosaFacade.actualizaRegistroGlucosa(registroGlucosaVo);
			
		}catch (RuntimeException ex) {
			log.debug("ex : " + ex.getMessage());
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaRegistroGlucosaPorId
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaFacade#recuperaRegistroGlucosaPorId(String)
	 */
	@Test
	public void recuperaRegistroGlucosaPorId() {
		log.debug("Inicio - Test");
		RegistroGlucosaVo registroGlucosaVo = null;

		String idRegistroGlucosa = "7";
		

		try{
			registroGlucosaVo = registroGlucosaFacade.recuperaRegistroGlucosaPorId(idRegistroGlucosa);
			
			if(registroGlucosaVo != null) {
				log.debug("registroGlucosaVo : " + registroGlucosaVo.toString());
			}
			
		}catch (RuntimeException ex) {
			log.debug("ex : " + ex.getMessage());
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaRegistroGlucosaPorIdPaciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaFacade#recuperaRegistroGlucosaPorIdPaciente(String)
	 */
	@Test
	public void recuperaRegistroGlucosaPorIdPaciente() {
		log.debug("Inicio - Test");
		List<RegistroGlucosaVo> registroGlucosaVo = null;

		String idPaciente = "124";
		

		try{
			registroGlucosaVo = registroGlucosaFacade.recuperaRegistroGlucosaPorIdPaciente(idPaciente);
			
			if(registroGlucosaVo != null) {
				XStream xStream = new XStream();
				log.debug("registroGlucosaVo : " + xStream.toXML(registroGlucosaVo));
			}
			
		}catch (RuntimeException ex) {
			log.debug("ex : " + ex.getMessage());
		}
		log.debug("Fin - Test");
	}

}
