package mx.escom.tt.diabetes.web.facade.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.web.facade.PacienteFacade;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class PacienteFacadeTestCase {
	
	@Autowired PacienteFacade pacienteFacade;

	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo guardarPaciente(), de la clase PacienteFacade
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/10/2017
	 * @see PacienteFacade#guardarPaciente(String, String, String, String, String, String, String, String, String)
	 */
	@Test
	public void guardarPaciente() {
		log.debug("Inicio - Test");
		
		String idIndividuoStr = "";
		String idMedicoStr = null;
		String pesoStr = "65";
		String tallaStr = null;
		String estaturaStr = "160";
		String imcStr = null;
		String lipidosStr = null;
		String carbohidratosStr = null;
		String proteinasStr = null;
		
		pacienteFacade.guardarPaciente(idIndividuoStr, idMedicoStr, pesoStr, tallaStr, estaturaStr, imcStr, lipidosStr, carbohidratosStr, proteinasStr);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo eliminarPaciente(), de la clase PacienteFacade
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/10/2017
	 * @see PacienteFacade#eliminarPaciente(String)
	 */
	@Test
	public void eliminarPaciente() {
		log.debug("Inicio - Test");
		
		String idPacienteStr = "3";
		
		pacienteFacade.eliminarPaciente(idPacienteStr);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarPaciente(), de la clase PacienteFacade
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 17/10/2017
	 * @see PacienteFacade#recuperarPaciente(String)
	 */
	@Test
	public void recuperarPaciente() {
		log.debug("Inicio - Test");
		
		String idPacienteStr = "4";
		PacienteDto pacienteDto = null;
		
		pacienteDto = pacienteFacade.recuperarPaciente(idPacienteStr);
		
		if(pacienteDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(pacienteDto): " + Constants.SALTO_LINEA + xStream.toXML(pacienteDto));
		}
		
		log.debug("Fin - Test");
	}
	
	@Test
	public void actualizarPaciente() {
		log.debug("Inicio - Test");
		
		String idPacienteStr = "5";
		String idIndividuoStr = "3";
		String idMedicoStr = "1";
		String pesoStr = "65";
		String tallaStr = null;
		String estaturaStr = "180";
		String imcStr = null;
		String lipidosStr = null;
		String carbohidratosStr = "12.03";
		String proteinasStr = "4.65";
	
		pacienteFacade.actualizarInformacionPaciente(idPacienteStr, idIndividuoStr, idMedicoStr, pesoStr, tallaStr, estaturaStr, imcStr, lipidosStr, carbohidratosStr, proteinasStr);
		
		log.debug("Fin - Test");
	}
}
