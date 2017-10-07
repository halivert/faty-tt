package mx.escom.tt.diabetes.model.dao.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.beans.factory.annotation.Autowired;

import mx.escom.tt.diabetes.model.dao.ClienteDao;
import mx.escom.tt.diabetes.model.dto.ClienteDto;

import lombok.extern.apachecommons.CommonsLog;


@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class ClienteDaoImplTestCase {
	
	 @Autowired ClienteDao clienteDao;
	 
	 
	 @Test
		public void recuperarTratamientosPorIdTramite(){
			log.debug("Inicio - Test");
			ClienteDto clienteDto = null;
			
			String idCliente = "1";
			
			clienteDto = clienteDao.recuperarClientePorId(idCliente);

			if(clienteDto != null) {
				log.debug("clienteDto.toString: " + clienteDto.toString());
			}
			log.debug("Fin - Test");
		}

}
