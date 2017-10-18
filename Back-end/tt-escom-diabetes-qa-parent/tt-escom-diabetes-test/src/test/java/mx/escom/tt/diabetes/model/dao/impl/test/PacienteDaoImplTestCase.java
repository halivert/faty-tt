package mx.escom.tt.diabetes.model.dao.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.PacienteDao;
import mx.escom.tt.diabetes.model.dto.PacienteDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml" })
public class PacienteDaoImplTestCase {

	@Autowired PacienteDao pacienteDao;
	
	/**
	 * 
	 * Proposito :  Validar el correcto funcionamiento del metodo guardarPaciente, de la clase PacienteDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteDao#recuperarPacientePorIdPaciente(Integer)
	 */
	@Test
	public void guardarPaciente(){
		log.debug("Inicio - Test");
		
		PacienteDto pacienteDto = new PacienteDto();
		
		Integer idIndividuo = 1;
		Integer idMedico = 1;
		double peso = 73.5;
		double talla = 70;
		double estatura = 170;
		double imc = 23.45;
		double carbohidratos = 3.4;
		double proteinas = 2.5;
		
		pacienteDto.setIdIndividuo(idIndividuo);
		pacienteDto.setIdMedico(idMedico);
		pacienteDto.setPeso(peso);		
		pacienteDto.setTalla(talla);		
		pacienteDto.setEstatura(estatura);		
		pacienteDto.setImc(imc);		
		pacienteDto.setCarbohidratos(carbohidratos);		
		pacienteDto.setProteinas(proteinas);
	
		try {
			pacienteDao.guardarPaciente(pacienteDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo  recuperarPacientePorIdPaciente, de la clase PacienteDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteDao#recuperarPacientePorIdPaciente(Integer)
	 */
	@Test
	public void recuperarPaciente() {
		log.debug("Inicio - Test");
		
		PacienteDto pacienteDto = null;
		Integer idPaciente = 2;
		
		pacienteDto = pacienteDao.recuperarPacientePorIdPaciente(idPaciente);
		
		if(pacienteDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(pacienteDto): " + Constants.SALTO_LINEA + xStream.toXML(pacienteDto));
		}
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo eliminarPacientePorIdPaciente, de la clase PacienteDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteDao#eliminarPacientePorIdPaciente(Integer)
	 */
	@Test
	public void eliminarPaciente() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 1;
		
		pacienteDao.eliminarPacientePorIdPaciente(idPaciente);
		
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarPacientePorIdPaciente, de la clase PacienteDao
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @see PacienteDao#actualizarInformacionPaciente(PacienteDto)
	 */
	@Test
	public void actualizarPaciente() {
		log.debug("Inicio - Test");
		
		Integer idPaciente = 2;
		PacienteDto pacienteDto =  null;
		
		pacienteDto = pacienteDao.recuperarPacientePorIdPaciente(idPaciente);
		
		double peso = 12;
		double proteinas = 235;
		
		{//Se actualiza la informacion
			pacienteDto.setPeso(peso);
			pacienteDto.setProteinas(proteinas);
		}
		
		//Se envia el DTO con la nueva informacion
		pacienteDao.actualizarInformacionPaciente(pacienteDto);
		
		
		log.debug("Fin - Test");
	}
	
}
