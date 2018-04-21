package mx.escom.tt.diabetes.business.service.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.DietaAppService;
import mx.escom.tt.diabetes.model.dto.DietaDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class DietaAppServiceTestCase {
	
	
	@Autowired DietaAppService dietaAppService;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarDietaPorIdAppService
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 */
	@Test
	public void recuperarDietaPorIdAppServiceTestCase() {
		log.debug("Inicio - Test");
		
		Integer idDieta = 3;
		DietaDto dietaDto = null;
	
		try{
			dietaDto = dietaAppService.recuperarDietaPorIdAppService(idDieta);
			if(dietaDto != null) {
				log.debug(dietaDto.toString());
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del medoto recuperarDietaPorIdPacienteAppService
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 */
	@Test
	public void recuperarDietaPorIdPacienteTestCase() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 126;
		List<DietaDto> dietaDtoList = null;
	
		try{
			dietaDtoList = dietaAppService.recuperarDietaPorIdPacienteAppService(idPaciente);
			if(dietaDtoList != null) {
				XStream xStream = new XStream();
				log.debug(xStream.toXML(dietaDtoList));
			}
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}

	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardarDietaAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 */
	@Test
	public void guardarDietaAppServiceTestCase() {
		log.debug("Inicio - Test");
		
		DietaDto dietaDto = null;
		Integer idMedico = 97;
		Integer idPaciente = 126;
		String descripcion = "Esta es una prueba a nivel Service";
		String alimentosDisponibles = "{  \r\n" + 
				"   \"Desayuno\":[  \r\n" + 
				"      {  \r\n" + 
				"         \"idAlimento\":1,\r\n" + 
				"         \"tipoAlimento\":\"Fruta\",\r\n" + 
				"         \"alimento\":\"Agua de coco\",\r\n" + 
				"         \"cantidad\":\"1 1/2\",\r\n" + 
				"         \"unidad\":\"taza\",\r\n" + 
				"         \"pesoBgr\":360,\r\n" + 
				"         \"pesoNgr\":360,\r\n" + 
				"         \"energia\":65,\r\n" + 
				"         \"proteina\":1.1,\r\n" + 
				"         \"lipidos\":0.7,\r\n" + 
				"         \"carbohidratos\":16.9,\r\n" + 
				"         \"fibraGr\":0,\r\n" + 
				"         \"vitaminaA\":0,\r\n" + 
				"         \"acidoAscorbico\":7.2,\r\n" + 
				"         \"acidoFolico\":0,\r\n" + 
				"         \"hierroNo\":4.3,\r\n" + 
				"         \"potasio\":529.2,\r\n" + 
				"         \"azucarGr\":0,\r\n" + 
				"         \"cargaGl\":\"ND\",\r\n" + 
				"         \"azucarPe\":\"0.0\",\r\n" + 
				"         \"colesterol\":\"ND\",\r\n" + 
				"         \"agSaturados\":\"ND\",\r\n" + 
				"         \"agMsaturados\":\"ND\",\r\n" + 
				"         \"agPsaturados\":\"ND\",\r\n" + 
				"         \"calcio\":\"ND\",\r\n" + 
				"         \"selenio\":\"ND\",\r\n" + 
				"         \"fosforo\":\"ND\",\r\n" + 
				"         \"potasioP\":\"ND\",\r\n" + 
				"         \"hierro\":\"ND\",\r\n" + 
				"         \"sodio\":\"ND\"\r\n" + 
				"      }\r\n" + 
				"   ],\r\n" + 
				"   \"C1\":[  \r\n" + 
				"      {  \r\n" + 
				"         \"idAlimento\":1,\r\n" + 
				"         \"tipoAlimento\":\"Fruta\",\r\n" + 
				"         \"alimento\":\"Agua de coco\",\r\n" + 
				"         \"cantidad\":\"1 1/2\",\r\n" + 
				"         \"unidad\":\"taza\",\r\n" + 
				"         \"pesoBgr\":360,\r\n" + 
				"         \"pesoNgr\":360,\r\n" + 
				"         \"energia\":65,\r\n" + 
				"         \"proteina\":1.1,\r\n" + 
				"         \"lipidos\":0.7,\r\n" + 
				"         \"carbohidratos\":16.9,\r\n" + 
				"         \"fibraGr\":0,\r\n" + 
				"         \"vitaminaA\":0,\r\n" + 
				"         \"acidoAscorbico\":7.2,\r\n" + 
				"         \"acidoFolico\":0,\r\n" + 
				"         \"hierroNo\":4.3,\r\n" + 
				"         \"potasio\":529.2,\r\n" + 
				"         \"azucarGr\":0,\r\n" + 
				"         \"cargaGl\":\"ND\",\r\n" + 
				"         \"azucarPe\":\"0.0\",\r\n" + 
				"         \"colesterol\":\"ND\",\r\n" + 
				"         \"agSaturados\":\"ND\",\r\n" + 
				"         \"agMsaturados\":\"ND\",\r\n" + 
				"         \"agPsaturados\":\"ND\",\r\n" + 
				"         \"calcio\":\"ND\",\r\n" + 
				"         \"selenio\":\"ND\",\r\n" + 
				"         \"fosforo\":\"ND\",\r\n" + 
				"         \"potasioP\":\"ND\",\r\n" + 
				"         \"hierro\":\"ND\",\r\n" + 
				"         \"sodio\":\"ND\"\r\n" + 
				"      }\r\n" + 
				"   ],\r\n" + 
				"   \"Comida\":[  \r\n" + 
				"      {  \r\n" + 
				"         \"idAlimento\":5,\r\n" + 
				"         \"tipoAlimento\":\"Fruta\",\r\n" + 
				"         \"alimento\":\"Anona\",\r\n" + 
				"         \"cantidad\":\"130\",\r\n" + 
				"         \"unidad\":\"g\",\r\n" + 
				"         \"pesoBgr\":130,\r\n" + 
				"         \"pesoNgr\":59,\r\n" + 
				"         \"energia\":59,\r\n" + 
				"         \"proteina\":1,\r\n" + 
				"         \"lipidos\":0.4,\r\n" + 
				"         \"carbohidratos\":14.7,\r\n" + 
				"         \"fibraGr\":2,\r\n" + 
				"         \"vitaminaA\":0,\r\n" + 
				"         \"acidoAscorbico\":5.3,\r\n" + 
				"         \"acidoFolico\":0,\r\n" + 
				"         \"hierroNo\":0.4,\r\n" + 
				"         \"potasio\":223.5,\r\n" + 
				"         \"azucarGr\":0,\r\n" + 
				"         \"cargaGl\":\"ND\",\r\n" + 
				"         \"azucarPe\":\"0.0\",\r\n" + 
				"         \"colesterol\":\"ND\",\r\n" + 
				"         \"agSaturados\":\"ND\",\r\n" + 
				"         \"agMsaturados\":\"ND\",\r\n" + 
				"         \"agPsaturados\":\"ND\",\r\n" + 
				"         \"calcio\":\"ND\",\r\n" + 
				"         \"selenio\":\"ND\",\r\n" + 
				"         \"fosforo\":\"ND\",\r\n" + 
				"         \"potasioP\":\"ND\",\r\n" + 
				"         \"hierro\":\"ND\",\r\n" + 
				"         \"sodio\":\"ND\"\r\n" + 
				"      }\r\n" + 
				"   ],\r\n" + 
				"   \"C2\":[  \r\n" + 
				"      {  \r\n" + 
				"         \"idAlimento\":9,\r\n" + 
				"         \"tipoAlimento\":\"Fruta\",\r\n" + 
				"         \"alimento\":\"Blueberries congeladas\",\r\n" + 
				"         \"cantidad\":\"3/4\",\r\n" + 
				"         \"unidad\":\"taza\",\r\n" + 
				"         \"pesoBgr\":116,\r\n" + 
				"         \"pesoNgr\":116,\r\n" + 
				"         \"energia\":59,\r\n" + 
				"         \"proteina\":0.5,\r\n" + 
				"         \"lipidos\":0.8,\r\n" + 
				"         \"carbohidratos\":14.2,\r\n" + 
				"         \"fibraGr\":3.2,\r\n" + 
				"         \"vitaminaA\":1.5,\r\n" + 
				"         \"acidoAscorbico\":9,\r\n" + 
				"         \"acidoFolico\":8.1,\r\n" + 
				"         \"hierroNo\":0.2,\r\n" + 
				"         \"potasio\":63,\r\n" + 
				"         \"azucarGr\":0,\r\n" + 
				"         \"cargaGl\":\"ND\",\r\n" + 
				"         \"azucarPe\":\"9.8\",\r\n" + 
				"         \"colesterol\":\"ND\",\r\n" + 
				"         \"agSaturados\":\"ND\",\r\n" + 
				"         \"agMsaturados\":\"ND\",\r\n" + 
				"         \"agPsaturados\":\"ND\",\r\n" + 
				"         \"calcio\":\"ND\",\r\n" + 
				"         \"selenio\":\"ND\",\r\n" + 
				"         \"fosforo\":\"ND\",\r\n" + 
				"         \"potasioP\":\"ND\",\r\n" + 
				"         \"hierro\":\"ND\",\r\n" + 
				"         \"sodio\":\"ND\"\r\n" + 
				"      }\r\n" + 
				"   ],\r\n" + 
				"   \"Cena\":[  \r\n" + 
				"      {  \r\n" + 
				"         \"idAlimento\":10,\r\n" + 
				"         \"tipoAlimento\":\"Fruta\",\r\n" + 
				"         \"alimento\":\"Blueberries congeladas con azucar\",\r\n" + 
				"         \"cantidad\":\"1/4\",\r\n" + 
				"         \"unidad\":\"taza\",\r\n" + 
				"         \"pesoBgr\":58,\r\n" + 
				"         \"pesoNgr\":58,\r\n" + 
				"         \"energia\":47,\r\n" + 
				"         \"proteina\":0.2,\r\n" + 
				"         \"lipidos\":0.1,\r\n" + 
				"         \"carbohidratos\":12.6,\r\n" + 
				"         \"fibraGr\":1.3,\r\n" + 
				"         \"vitaminaA\":0.5,\r\n" + 
				"         \"acidoAscorbico\":3.5,\r\n" + 
				"         \"acidoFolico\":4,\r\n" + 
				"         \"hierroNo\":0.2,\r\n" + 
				"         \"potasio\":34.5,\r\n" + 
				"         \"azucarGr\":0,\r\n" + 
				"         \"cargaGl\":\"ND\",\r\n" + 
				"         \"azucarPe\":\"11.4\",\r\n" + 
				"         \"colesterol\":\"ND\",\r\n" + 
				"         \"agSaturados\":\"ND\",\r\n" + 
				"         \"agMsaturados\":\"ND\",\r\n" + 
				"         \"agPsaturados\":\"ND\",\r\n" + 
				"         \"calcio\":\"ND\",\r\n" + 
				"         \"selenio\":\"ND\",\r\n" + 
				"         \"fosforo\":\"ND\",\r\n" + 
				"         \"potasioP\":\"ND\",\r\n" + 
				"         \"hierro\":\"ND\",\r\n" + 
				"         \"sodio\":\"ND\"\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}";
		try{
			
			dietaDto  = new DietaDto();
			dietaDto.setIdMedico(idMedico);
			dietaDto.setIdPaciente(idPaciente);
			dietaDto.setDescripcion(descripcion);
			dietaDto.setAlimentosDisponibles(alimentosDisponibles);
			
			dietaAppService.guardarDietaAppService(dietaDto);
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex.getMessage());
		}
		
		log.debug("Fin - Test");
	}
}
