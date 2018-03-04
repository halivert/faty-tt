package mx.escom.tt.diabetes.model.dao.impl.test;

import java.sql.Timestamp;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.vo.UltimoHistorialClinicoVo;
import mx.escom.tt.diabetes.model.dao.HistorialClinicoDao;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class HistorialClinicoDaoImplTestCase {
	
	
	@Autowired HistorialClinicoDao  historialClinicoDao;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardarHistorialClinico(), de la clase HistorialClinicoDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void guardarHistorialClinico(){
		log.debug("Inicio - Test");
		
		HistorialClinicoDto historialClinicoDto = new HistorialClinicoDto();
		
		Integer idPaciente = 117;
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		double peso = 72.5;
		double talla = 70;
		double estatura = 170;
		double lipidos = 2.44;
		double carbohidratos = 3.4;
		double proteinas = 2.5;
		double azucar = 100;
		
		{//SE ARMA EL DTO
			historialClinicoDto.setIdPaciente(idPaciente);
			historialClinicoDto.setFecha(fecha);
			historialClinicoDto.setPeso(peso);
			historialClinicoDto.setTalla(talla);
			historialClinicoDto.setEstatura(estatura);
			//historialClinicoDto.setImc(imc);
			historialClinicoDto.setLipidos(lipidos);
			historialClinicoDto.setCarbohidratos(carbohidratos);
			historialClinicoDto.setProteinas(proteinas);
			historialClinicoDto.setAzucar(azucar);		
		}
		
		historialClinicoDao.guardarHistorialClinico(historialClinicoDto);

		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Actualizar la informacion de un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 05/02/2018
	 */
	@Test
	public void actualizarHistorialClinico(){
		log.debug("Inicio - Test");
		
		HistorialClinicoDto historialClinicoDto = null;
		
		Integer idHistorialClinico = 5;
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		double peso = 72.5;
		double talla = 70;
		double estatura = 170;
		double lipidos = 2.44;
		double carbohidratos = 3.4;
		double proteinas = 2.5;
		double azucar = 100;
		double imc = 1;
		
		historialClinicoDto = historialClinicoDao.recuperarHistorialClinicoPorId(idHistorialClinico);
		
		{//SE ARMA EL DTO
			historialClinicoDto.setFecha(fecha);
			historialClinicoDto.setPeso(peso);
			historialClinicoDto.setTalla(talla);
			historialClinicoDto.setEstatura(estatura);
			historialClinicoDto.setImc(imc);
			historialClinicoDto.setLipidos(lipidos);
			historialClinicoDto.setCarbohidratos(carbohidratos);
			historialClinicoDto.setProteinas(proteinas);
			historialClinicoDto.setAzucar(azucar);		
		}
		
		historialClinicoDao.actualizarHistorialClinico(historialClinicoDto);
		log.debug("Fin - Test");
	}
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarHistorialClinicoPorId(), de la clase HistorialClinicoDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void recuperarHistorialClinico() {
		log.debug("Inicio - Test");
		
		HistorialClinicoDto historialClinicoDto = null;
		Integer idHistorialClinico = 13;
		
	
		historialClinicoDto = historialClinicoDao.recuperarHistorialClinicoPorId(idHistorialClinico);
		
		if(historialClinicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(historialClinicoDto): \n" + xStream.toXML(historialClinicoDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarListaHistorialClinicoPorIdPaciente(), de la clase HistorialClinicoDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void recuperarHistorialClinicoPorIdUsuario() {
		log.debug("Inicio - Test");
		
		List<HistorialClinicoDto> listHistorialClinicoDto = null;
		Integer idPaciente = 96;
	
		listHistorialClinicoDto = historialClinicoDao.recuperarListaHistorialClinicoPorIdPaciente(idPaciente);
		
		if(listHistorialClinicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(listHistorialClinicoDto): \n" + xStream.toXML(listHistorialClinicoDto));
		}
		
		log.debug("Fin - Test");
	}

	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarUltimoHistorialClinicoPorIdPaciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/02/2018
	 * @see HistorialClinicoDao#recuperarUltimoHistorialClinicoPorIdPaciente(Integer)
	 */
	@Test
	public void recuperarUltimoHistorialClinicoPorIdPaciente() {
		log.debug("Inicio - Test");
		
		UltimoHistorialClinicoVo ultimoHistorialClinicoVo = null;
		Integer idPaciente = 117;
	
		try{
			ultimoHistorialClinicoVo = historialClinicoDao.recuperarUltimoHistorialClinicoPorIdPaciente(idPaciente);
			if(ultimoHistorialClinicoVo != null) {
				XStream xStream = new XStream();
				log.debug("xStream.toXML(listHistorialClinicoDto): \n" + xStream.toXML(ultimoHistorialClinicoVo));
			}
			
		}catch(RuntimeException rtex) {
			log.debug("rtex.getMessage() : " + rtex.getMessage());
		}
		log.debug("Fin - Test");
	}
}
