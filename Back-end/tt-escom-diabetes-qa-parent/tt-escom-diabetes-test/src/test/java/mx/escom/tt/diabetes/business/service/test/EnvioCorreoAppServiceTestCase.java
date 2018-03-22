package mx.escom.tt.diabetes.business.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.EnvioCorreoAppService;
import mx.escom.tt.diabetes.business.service.TokenMedicoAppService;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class EnvioCorreoAppServiceTestCase {

	@Autowired private EnvioCorreoAppService envioCorreoAppService;
	@Autowired private TokenMedicoAppService tokenMedicoAppService;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo enviarCorreoElectronico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 19/03/2018
	 */
	@Test
	public void enviarCorreoTestCase() {
		log.debug("Inicio - Test");
		
		String asunto = "Correo Final";
		String[] arrayPara = {"edgarivancs@gmail.com"};
		String mensaje = "Se modifica el cuerpo del mensaje";
		String remitente = "edgar.hurtado@habilgroup.com";
		try {
			envioCorreoAppService.enviarCorreoElectronico(asunto, arrayPara, null, mensaje,remitente);
		}catch(Exception ex) {
			throw (ex);
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo enviarTokenEmailAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 19/03/2018
	 */
	@Test
	public void enviarTokenEmailTestCase() {
		log.debug("Inicio - Test");
		
		String[] email = {"edgarivancs@gmail.com"};
		String remitente = "edgar.hurtado@habilgroup.com";
		String token = "123ABC";
		String nombreMedico = "Kevin Adrian";
		try {
			tokenMedicoAppService.enviarTokenEmailAppService(email, token, nombreMedico, remitente);
		}catch(Exception ex) {
			throw (ex);
		}
		log.debug("Fin - Test");
	}
}
