package mx.escom.tt.diabetes.business.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.PacienteDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class PacienteAppServiceTestCase {
	
	@Autowired
	PacienteAppService pacienteAppService;
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarPaciente, de la clase recuperarPacientePorIdPacienteAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteAppService#recuperarPaciente(Integer)
	 */
	@Test
	public void recuperarPacientePorIdPacienteAppServiceTestCase() {
		log.debug("Incio - Test");
		
		Integer idPaciente = 2;
		PacienteDto pacienteDto = null;
		
		pacienteDto = pacienteAppService.recuperarPacientePorIdPacienteAppService(idPaciente);
				
		if(pacienteDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(pacienteDto): " + Constants.SALTO_LINEA + xStream.toXML(pacienteDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo guardarPaciente, de la clase guardarPacienteAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteAppService#guardarPaciente(PacienteDto)
	 */
	@Test
	public void guardarPacienteAppServiceTestCase() {
		log.debug("Incio - Test");
		PacienteDto pacienteDto = new PacienteDto();
		
		//Integer idIndividuo = 2;
		//Integer idMedico = 1;
		/*double peso = 100;
		double talla = 80.4;
		double estatura = 140;
		double imc = 35.67;
		double carbohidratos = 34;
		double proteinas = 25;
		
		pacienteDto.setIdIndividuo(idIndividuo);
		pacienteDto.setIdMedico(idMedico);
		pacienteDto.setPeso(peso);		
		pacienteDto.setTalla(talla);		
		pacienteDto.setEstatura(estatura);		
		pacienteDto.setImc(imc);		
		pacienteDto.setCarbohidratos(carbohidratos);		
		pacienteDto.setProteinas(proteinas);*/
		
		pacienteAppService.guardarPacienteAppService(pacienteDto);
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo eliminarPacienteAppService, de la clase PacienteAppService 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteAppService#eliminarPaciente(Integer)
	 */
	@Test
	public void eliminarPacienteAppServiceTestCase() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 2;
		
		pacienteAppService.eliminarPacienteAppService(idPaciente);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo actualizarInformacionPacienteAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 */
	@Test
	public void cambiarMedicoPacienteAppServiceTestCase() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 129;
		String codigo = "5T1RM";
		
		pacienteAppService.cambiarMedicoPacienteAppService(idPaciente, codigo);	
		log.debug("Fin - Test");
	}
	
	

}
