package mx.escom.tt.diabetes.model.dao.impl.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.beans.factory.annotation.Autowired;

import mx.escom.tt.diabetes.model.dao.UsuarioDao;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import lombok.extern.apachecommons.CommonsLog;

import com.thoughtworks.xstream.XStream;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class UsuarioDaoImplTestCase {
	
	 @Autowired UsuarioDao clienteDao;
 
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarUsuarioPorId() de la clase UsuarioDao. 
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see UsuarioDao#recuperarUsuarioPorId(Integer)
	 */
	@Test
	public void recuperarUsuarioPorId(){
		log.debug("Inicio - Test");
		UsuarioDto usuarioDto = null;
		
		Integer idUsuario = 5;
		
		usuarioDto = clienteDao.recuperarUsuarioPorId(idUsuario);

		if(usuarioDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(usuarioDto): " + xStream.toXML(usuarioDto));
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo registrarNuevoUsuario() de la clase UsuarioDao
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @see UsuarioDao#registrarNuevoUsuario(UsuarioDto)
	 */
	@Test
	public void guardarUsuario() {
		log.debug("Inicio - Test");
		
		UsuarioDto usuarioDto = new UsuarioDto();
		String nombre = "Adrian";
		Date fechaNac = new Date();
		Integer idUsuario = 5;
		
		usuarioDto.setIdUsuario(idUsuario);
		usuarioDto.setNombre(nombre);
		usuarioDto.setFechaNac(fechaNac);
		
		clienteDao.registrarNuevoUsuario(usuarioDto);
		
		log.debug("Fin - Test");
	}

}
