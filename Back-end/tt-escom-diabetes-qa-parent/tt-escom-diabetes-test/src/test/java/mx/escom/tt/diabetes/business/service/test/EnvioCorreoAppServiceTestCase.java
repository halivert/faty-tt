package mx.escom.tt.diabetes.business.service.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.EnvioCorreoAppService;
import mx.escom.tt.diabetes.business.service.ReportesPDF;
import mx.escom.tt.diabetes.business.service.TokenMedicoAppService;
import mx.escom.tt.diabetes.business.vo.DietaReporteVo;
import mx.escom.tt.diebetes.dtree.DecisionTreeExample;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class EnvioCorreoAppServiceTestCase {

	@Autowired private EnvioCorreoAppService envioCorreoAppService;
	@Autowired private TokenMedicoAppService tokenMedicoAppService;
	@Autowired private ReportesPDF reportesPDF;
	
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
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo enviarCorreoReestablecerPassword
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 */
	@Test
	public void enviarCorreoReestablecerPasswordTestCase() {
		log.debug("Inicio - Test");
		
		String[] email = {"edgarivancs@gmail.com"};
		String remitente = "edgar.hurtado@habilgroup.com";
		String urlToken ="http://35.202.245.109/ceres/#/session/recuperaPassword/2343234";
		String nombreUsuario = "Edgar Ivan";
		try {
			tokenMedicoAppService.enviarCorreoReestablecerPassword(email, urlToken, nombreUsuario, remitente);
		}catch(Exception ex) {
			throw (ex);
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo crearReporte
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 */
	@Test
	public void crearReporteTestCase() {
		log.debug("Inicio - Test");
		
			byte[] result; 
			Date nombre = new Date();
			String urlJasper = null;
			String urlSubreport = null;
			Map<String, Object> parameters;
			List<DietaReporteVo> collectionDataSource = new ArrayList<DietaReporteVo>();
			
			try {
				urlJasper = "caratula_dieta.jasper";
				
				parameters = new HashMap<>();
				parameters.put("nombrePaciente", "Ivan Hurtado Guzmán");
				parameters.put("edadPaciente", "19");
				parameters.put("estaturaPaciente", "1.80");
				parameters.put("pesoPaciente", "100");
				parameters.put("get", "2400");
				
				
				
				for(int i = 0; i< 5; i++) {
					DietaReporteVo dietaReporteVo = new DietaReporteVo();
					dietaReporteVo.setAlimentosDesayuno("alimentos : " + i);
					log.debug(i);
					collectionDataSource.add(dietaReporteVo);
				}
				log.debug(collectionDataSource.size());
				
				
				result = reportesPDF.crearReporte(urlJasper, urlSubreport, parameters, collectionDataSource);
				
				
				File pdfAttachmentFile = new File("/Users/edgarHG/Documents/documento_"+nombre.toString()+".pdf");
				FileOutputStream fileOutputStream = new FileOutputStream(pdfAttachmentFile);
				fileOutputStream.write(result);
				fileOutputStream.close();
			}catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		
		log.debug("Fin - Test");
	}
}
