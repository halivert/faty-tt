package mx.escom.tt.diabetes.web.facade.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.web.facade.UsuarioFacade;
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
		String idUsuarioStr = "120";
		
		usuarioVo = usuarioFacade.recuperarUsuarioPorId(idUsuarioStr);

		if(usuarioVo != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): \n" + xStream.toXML(usuarioVo));
		}
		
		log.debug("Fin - Test");
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
			usuarioVo.setNombre("Token");
			usuarioVo.setApellidoPaterno("Hurtado");
			usuarioVo.setApellidoMaterno("Guzman");
			usuarioVo.setEmail("token@gmail.comm");
			usuarioVo.setFechaNacimiento("27/02/1994");
			usuarioVo.setKeyword("alejandra");
			usuarioVo.setSexo("0");
			usuarioVo.setIdRol("0");
			usuarioVo.setCodigoMedico("CAMBIO1234");
			usuarioVo.setCedulaProfesional("IVAN1234");
		}
		usuarioFacade.guardarUsuario(usuarioVo);

		
		log.debug("Fin - Test");
	}
	
}
