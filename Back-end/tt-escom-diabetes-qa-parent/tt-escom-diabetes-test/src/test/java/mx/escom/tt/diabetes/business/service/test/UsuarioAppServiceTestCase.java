
package mx.escom.tt.diabetes.business.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.UsuarioAppService;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml"})
public class UsuarioAppServiceTestCase {

	@Autowired UsuarioAppService usuarioAppService; 
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarUsuarioPorIdAppService() de la clase UsuarioAppService. 
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 08/10/2017
	 * @see UsuarioAppService#recuperarUsuarioPorIdAppService(Integer)
	 */
	@Test
	public void recuperarUsuarioPorId() {
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = null;
		Integer idUsuario = 19;
		
		
		
		usuarioDto = usuarioAppService.recuperarUsuarioPorId(idUsuario);

		if(usuarioDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + xStream.toXML(usuarioDto));
		}
		
		log.debug("Fin - Test");
	}
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarPorEmailYKeyword(), de la clase UsuarioAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 */
	@Test
	public void recuperarPorEmailYKeyword() {
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = null;
		String email = "enrique@gmail.com";
		String keyword = "enrique123'4";
		
		try{
			usuarioDto = usuarioAppService.recuperarPorEmailYKeyword(email, keyword);
			if(usuarioDto != null) {
				XStream xStream = new XStream();
				log.debug("xStream.toXML(usuarioDto): \n" + xStream.toXML(usuarioDto));
			}
		}catch(RuntimeException ex) {
			log.debug("ex : " + ex );
		}


		
		log.debug("Fin - Test");
	}
	
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo guardarUsuario(), de la clase UsuarioAppService
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @throws ParseException
	 */
	@Test
	public void guardarUsuario() throws ParseException{
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = new UsuarioDto();
		String nombre = "Kevin";
		String apellidoPaterno = "Hurtado";
		String apellidoMaterno = "Guzman";
		String email = "kevin@hotmail.com";
		String keyword = "enrique1234";
		String sexo = "Masculino";
		String fechaNacStr = "18/06/2004";
		
		
		String expectedPattern = "dd/MM/yyyy";
	    SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date fechaNacimiento = formatter.parse(fechaNacStr);
		
		{//SE ARMA EL DTO
			
			usuarioDto.setNombre(nombre);
			usuarioDto.setApellidoPaterno(apellidoPaterno);
			usuarioDto.setApellidoMaterno(apellidoMaterno);
			usuarioDto.setEmail(email);
			usuarioDto.setKeyword(keyword);
			usuarioDto.setFechaNacimiento(fechaNacimiento);
			usuarioDto.setSexo(sexo);
			
		}
		
		MedicoDto medicoDto = new MedicoDto();
		String cedulaProfesional = "CEDULA134";
		
		medicoDto.setCedulaProfesional(cedulaProfesional );
		
		usuarioAppService.guardarUsuario(usuarioDto, medicoDto, null);
		
		log.debug("Inicio - Test");
	}
	
	
}
