package mx.escom.tt.diabetes.model.dao.impl.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.vo.RegistroGlucosaCommonVo;
import mx.escom.tt.diabetes.model.dao.RegistroGlucosaDao;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class RegistroGlucosaDaoImplTestCase {

	@Autowired RegistroGlucosaDao registroGlucosaDao;
	
	@Qualifier("FormatoTimpeStamp")
	@Autowired SimpleDateFormat formatoFecha;
	
	@Qualifier("FormatoFechaNacimiento")
	@Autowired SimpleDateFormat formatoFechaNacimiento;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardaRegistroGlucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaDao#guardaRegistroGlucosa(RegistroGlucosaDto)
	 */
	@Test
	public void guardaRegistroGlucosa(){
		log.debug("Inicio - Test");
		
		RegistroGlucosaDto registroGlucosaDto = new RegistroGlucosaDto();
		
		Integer idPaciente = 124;
		Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());
		//Timestamp fechaActualizacion = null;
		double azucar = 70;
		
		{//SE ARMA EL DTO
			registroGlucosaDto.setIdPaciente(idPaciente);
			registroGlucosaDto.setFechaRegistro(fechaRegistro);
			//registroGlucosaDto.setFechaActualizacion(null);
			registroGlucosaDto.setAzucar(azucar);
		}
		
		registroGlucosaDao.guardaRegistroGlucosa(registroGlucosaDto);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo actualizaRegistroGlucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaDao#actualizaRegistroGlucosa(RegistroGlucosaDto)
	 */
	@Test
	public void actualizaRegistroGlucosa(){
		log.debug("Inicio - Test");
		
		RegistroGlucosaDto registroGlucosaDto =null;
		
		Integer idRegistroGlucosa = 4;
		//Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());
		Timestamp fechaActualizacion = new Timestamp(System.currentTimeMillis());
		double azucar = 115;
		
		//Se recupera el registro a actualizar
		registroGlucosaDto = registroGlucosaDao.recuperaRegistroGlucosaPorId(idRegistroGlucosa);
		
		{//Se actualiza la informacion
			registroGlucosaDto.setAzucar(azucar);	
			registroGlucosaDto.setFechaActualizacion(fechaActualizacion);
		}
		
		registroGlucosaDao.actualizaRegistroGlucosa(registroGlucosaDto);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaRegistroGlucosaPorId
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaDao#recuperaRegistroGlucosaPorId(Integer)
	 */
	@Test
	public void recuperaRegistroGlucosaPorId(){
		log.debug("Inicio - Test");
		
		RegistroGlucosaDto registroGlucosaDto = null;
		Integer idRegistroGlucosa = 90;
		
		registroGlucosaDto = registroGlucosaDao.recuperaRegistroGlucosaPorId(idRegistroGlucosa);
		
		if(registroGlucosaDto != null){
			log.debug("registroGlucosaDto : " + registroGlucosaDto.toString());
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaListaRegistroGlucosaPorIdPaciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @see RegistroGlucosaDao#recuperaListaRegistroGlucosaPorIdPaciente(Integer)
	 */
	@Test
	public void recuperaListaRegistroGlucosaPorIdPaciente(){
		log.debug("Inicio - Test");
		
		Integer idPaciente = 124;
		List<RegistroGlucosaDto> registroGlucosaDto = null;
		
		registroGlucosaDto = registroGlucosaDao.recuperaListaRegistroGlucosaPorIdPaciente(idPaciente);
		
		if(!registroGlucosaDto.isEmpty()){
			log.debug("registroGlucosaDto.size() : " + registroGlucosaDto.size());
			XStream xStream = new XStream();
			log.debug("xStream.toXML(registroGlucosaDto): \n" + xStream.toXML(registroGlucosaDto));
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaNRegistroGlucosa
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 13/04/2018
	 * @see RegistroGlucosaDao#recuperaNRegistroGlucosa(Integer, Integer)
	 */
	@Test
	public void recuperaNRegistroGlucosaTestCase(){
		log.debug("Inicio - Test");
		
		Integer idPaciente = 3;
		Integer  limiteRegistro = 3;
		List<RegistroGlucosaCommonVo> registroGlucosaVo = null;
		
		registroGlucosaVo = registroGlucosaDao.recuperaNRegistroGlucosa(idPaciente, limiteRegistro);
		
		if(!registroGlucosaVo.isEmpty()){
			log.debug("registroGlucosaDto.size() : " + registroGlucosaVo.size());
			XStream xStream = new XStream();
			log.debug("xStream.toXML(registroGlucosaVo): \n" + xStream.toXML(registroGlucosaVo));
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperaListaRegistroGlucosaPorFiltros
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @see RegistroGlucosaDao#recuperaListaRegistroGlucosaPorFiltros(Integer, Timestamp, Timestamp)
	 */
	@Test
	public void recuperaListaRegistroGlucosaPorFiltrosTestCase(){
		log.debug("Inicio - Test");
		
		Integer idPaciente = 3;
		List<RegistroGlucosaCommonVo> registroGlucosaVo = null;
		Date date1, date2;
		
		try {
			date1 = formatoFechaNacimiento.parse("01/04/2018");
			date2 = formatoFechaNacimiento.parse("01/05/2018");
			 
			String a = formatoFecha.format(date1);
			String b = formatoFecha.format(date2);
			
			registroGlucosaVo = registroGlucosaDao.recuperaListaRegistroGlucosaPorFiltros(idPaciente, Timestamp.valueOf(a),  Timestamp.valueOf(b));
			
			if(!registroGlucosaVo.isEmpty()){
				log.debug("registroGlucosaDto.size() : " + registroGlucosaVo.size());
				XStream xStream = new XStream();
				log.debug("xStream.toXML(registroGlucosaVo): \n" + xStream.toXML(registroGlucosaVo));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
}
