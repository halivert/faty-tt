package mx.escom.tt.diabetes.model.dao.impl.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.model.dao.MedicoDao;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class MedicoDaoImplTestCase {
	
	@Autowired MedicoDao medicoDao;
	@Autowired TokenMedicoDao tokenMedicoDao;
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo guardarMedico()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 */
	@Test
	public void guardarMedico() {
		log.debug("Inicio - Test");
		
		MedicoDto medicoDto = new MedicoDto();
		
		medicoDto.setIdUsuario(22);
		medicoDto.setCedulaProfesional("s-23836");
		
		medicoDao.guardarMedico(medicoDto);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correco funcionamiento del metodo  recuperarMedico()
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 */
	@Test
	public void recuperarMedico() {
		log.debug("Inicio - Test");
		
		MedicoDto medicoDto = null;
		Integer idMedico = 10;
		
		
		medicoDto = medicoDao.recuperarMedico(idMedico);
		
		if(medicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(medicoDto) : \n" + xStream.toXML(medicoDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarPacientesDeMedico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 29/10/2017
	 */
	@Test
	public void recuperarPacientesDeMedico() {
		log.debug("Inicio - Test");
		
		List<MedicoPacientesVo> result = null;
		Integer idMedico = 113;
		
		
		result = medicoDao.recuperarPacientesDeMedico(idMedico);
		
		if(result != null && !result.isEmpty()) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(medicoDto) : \n" + xStream.toXML(result));
		}
		
		log.debug("El médico aun no tiene pacientes");
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarTokenPorIdMedico() de la clase TokenMedicoDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 */
	@Test
	public void recuperarTokenMedicoPorId() {
		log.debug("Inicio - Test");
		
		TokenMedicoDto tokenMedicoDto = null;
		String token = "CAMBIO1234";
		
		tokenMedicoDto = tokenMedicoDao.recuperarToken(token);
		
		if(tokenMedicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(tokenMedicoDto) : \n" + xStream.toXML(tokenMedicoDto));
		}
		
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Guardar Token Medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 */
	@Test
	public void guardarTokenMedico() {
		log.debug("Inicio - Test");
		
		TokenMedicoDto tokenMedicoDto = new TokenMedicoDto();
		
		Integer idMedico = 97;
		String token = "nuevotoken";
		
		tokenMedicoDto.setIdMedico(idMedico);
		tokenMedicoDto.setToken(token);
		
		tokenMedicoDao.guardarToken(tokenMedicoDto);
		
		
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Validar el correcto funcionamiento del medoto borrarToken() de la clase TokenMedicoDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 */
	@Test
	public void borrarTokenMedico() {
		log.debug("Inicio - Test");
		
		String token = "nuevotoken";
		
		
		tokenMedicoDao.borrarToken(token);
		
		
		log.debug("Fin - Test");
	}

}
