package mx.escom.tt.diabetes.model.dao.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.IndividuoAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class IndividuoDaoImplTestCase {
	@Autowired IndividuoDao individuoDao;
	@Autowired IndividuoAppService individuoAppService;
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo guardarIndividuo() de la clase IndividuoDao. 
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see IndividuoDao#guardarIndividuo(IndividuoDto)
	 */
	@Test
	public void guardarIndividuo() {
		log.debug("Inicio - Test");
		IndividuoDto individuoDto = new IndividuoDto();
		
		individuoDto.setNombre("Ivan");
		individuoDto.setApellidoMaterno("Guzman");
		individuoDto.setApellidoPaterno("Hurtado");
		individuoDto.setEmail("edgarivancs@gmail.com");
		
	
		try {
			individuoDao.guardarIndividuo(individuoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo borrarIndividuo() de la clase IndividuoDao. 
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see IndividuoDao#borrarIndividuo(Integer)
	 */
	@Test
	public void borrarIndividuo() {
		log.debug("Inicio - Test");
		
		try {
			individuoDao.borrarIndividuo(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo obtenerIndividuo() de la clase IndividuoDao. 
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see IndividuoDao#obtenerIndividuo(Integer)
	 */
	@Test
	public void obtenerIndividuo() {
		log.debug("Inicio - Test");
		
		try {
			
			IndividuoDto individuoDto = individuoDao.obtenerIndividuo(2);
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + Constants.SALTO_LINEA + xStream.toXML(individuoDto));
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo actualizarIndividuo() de la clase IndividuoDao. 
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see IndividuoDao#actualizarIndividuo(IndividuoDto)
	 */
	@Test
	public void actualizarIndividuo() {
		log.debug("Inicio - Test");
		IndividuoDto individuoDto = new IndividuoDto();
		individuoDto.setIdIndividuo(2);
		individuoDto.setNombre("Hali");
		individuoDto.setApellidoPaterno("Velazquez");
		
		try {
			individuoDao.actualizarIndividuo(individuoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo obtenerIndividuo() de la clase IndividuoAppService. 
	 *
	 * @author Hali, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see IndividuoAppService#obtenerIndividuo(Integer)
	 */
	@Test
	public void obtenerIndividuoAppS() {
		log.debug("Inicio - Test");
		try {
			IndividuoDto individuoDto = individuoAppService.obtenerIndividuo(2);
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + Constants.SALTO_LINEA + xStream.toXML(individuoDto));
		} catch (Exception e) {
			e.getMessage();
		}

		log.debug("Fin - Test");
	}

}
