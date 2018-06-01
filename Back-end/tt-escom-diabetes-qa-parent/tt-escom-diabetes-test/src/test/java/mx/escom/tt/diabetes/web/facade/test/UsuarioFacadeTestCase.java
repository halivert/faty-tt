package mx.escom.tt.diabetes.web.facade.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
import mx.escom.tt.diabetes.web.vo.DietaReportePDFVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
import mx.escom.tt.diabetes.web.vo.UsuarioLoginVo;
import mx.escom.tt.diabetes.web.vo.UsuarioVo;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class UsuarioFacadeTestCase {
	
	@Autowired UsuarioFacade usuarioFacade;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarUsuarioPorId() de la clase UsuarioFacade. 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 */
	@Test
	public void recuperarUsuarioPorIdFacadeTestCase() {
		log.debug("Inicio - Test");
		
		UsuarioVo usuarioVo = null;
		String idUsuarioStr = "19";
		
		usuarioVo = usuarioFacade.recuperarUsuarioPorId(idUsuarioStr);

		if(usuarioVo != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): \n" + xStream.toXML(usuarioVo));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo login
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/03/2018
	 */
	@Test
	public void recuperarPorEmailYKeyword() {
		log.debug("Inicio - Test");
		
		String email = "enrique@gmail.com";
		String keyword = "enrique123'4";
		
		try{

			UsuarioLoginVo usuarioVo = new UsuarioLoginVo();
			
			usuarioVo.setEmail(email);
			usuarioVo.setKeyword(keyword);
			
			RespuestaVo respuestaVo = usuarioFacade.login(usuarioVo);
			
			if(respuestaVo != null) {
				
				log.debug("respuestaVo: \n" + respuestaVo);
			}
			
		}catch(RuntimeException ex) {
			log.debug("ex :" + ex);
		}
}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo guardarUsuario()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 */
	@Test
	public void guardarUsuario() {
		log.debug("Inicio - Test");
		
		UsuarioVo usuarioVo = new UsuarioVo();
		
		{//SE ARMA EL VO
			usuarioVo.setNombre("Miguel");
			usuarioVo.setApellidoPaterno("Rojas");
			usuarioVo.setApellidoMaterno("Rojas");
			usuarioVo.setEmail("a@a.com");
			usuarioVo.setFechaNacimiento("27/02/1994");
			usuarioVo.setKeyword("doctor");
			usuarioVo.setSexo("0");
			usuarioVo.setIdRol("0");
			usuarioVo.setCodigoMedico("Y5OVKF");
			usuarioVo.setCedulaProfesional("123");
		}
		usuarioFacade.guardarUsuario(usuarioVo);

		
		log.debug("Fin - Test");
	}
	
	@Test
	public void generarDietaPDFTestCase() {
		log.debug("Inicio - Test");
		
		DietaReportePDFVo dietaReportePDFVo = new DietaReportePDFVo();
		byte [] result = null;
		Date nombre = new Date();
		{//SE ARMA EL VO
			dietaReportePDFVo.setDescripcion("Desc");
			dietaReportePDFVo.setEdadPaciente("23");
			dietaReportePDFVo.setEstaturaPaciente("1.70");
			dietaReportePDFVo.setGastoET("2345");
			dietaReportePDFVo.setNombrePaciente("Edgar ivan Hurtado Guzman");
			dietaReportePDFVo.setPesoPaciente("70");
		}
	
		try {
			result = usuarioFacade.generarDietaPDF(dietaReportePDFVo);
			
			File pdfAttachmentFile = new File("/Users/edgarHG/Documents/documento_"+nombre.toString()+".pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(pdfAttachmentFile);
			fileOutputStream.write(result);
			fileOutputStream.close();
		}catch(Exception ex) {
			log.error(ex.getStackTrace());
		}
		
		log.debug("Fin - Test");
	}
	
	@Test
	public void encodeKeywordTestCase() {
		log.debug("Inicio - Test");
		
		String password = "vere";
		String result = null;
		try {
			result = usuarioFacade.encodeKeyword(password);
			
			log.debug("result : " + result);
		}catch(Exception ex) {
			log.error(ex.getStackTrace());
		}
		
		log.debug("Fin - Test");
	}
	
}
