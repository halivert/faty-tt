package mx.escom.tt.diabetes.web.facade.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;
import mx.escom.tt.diabetes.web.facade.HistorialClinicoFacade;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoListVo;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/escom/tt/diabetes/commons/xml/commons.application.context.xml",
		"classpath:mx/escom/tt/diabetes/model/xml/model.application.context.xml",
		"classpath:mx/escom/tt/diabetes/business/xml/business.application.context.xml",
		"classpath:mx/escom/tt/diabetes/web/xml/web.application.context.xml"})
public class HistorialClinicoFacadeTestCase {

	@Autowired HistorialClinicoFacade historialClinicoFacade;
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarHistorialClinicoPorId(), de la clase HistorialClinicoFacade.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void recuperarHistorialClinicoPorId() {
		log.debug("Inicio - Test");
		
		HistorialClinicoDto historialClinicoDto = null;
		String idHistorialClinicoStr = "4";
		
		historialClinicoDto = historialClinicoFacade.recuperarHistorialClinicoPorId(idHistorialClinicoStr);

		if(historialClinicoDto != null) {
			XStream xStream = new XStream();
			log.debug("xStream.toXML(historialClinicoDto): \n" + xStream.toXML(historialClinicoDto));
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * 
	 * Proposito : Validar el correcto funcionamiento del metodo recuperarListaHistorialClinicoPorPaciente(), de la clase HistorialClinicoFacade.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void recuperarListaHistorialClinicoPorPaciente() {
		log.debug("Inicio - Test");
		
		List<HistorialClinicoListVo> historialClinicoListVo = null;
		String idPacienteStr = "96";
		
		historialClinicoListVo = historialClinicoFacade.recuperarListaHistorialClinicoPorPaciente(idPacienteStr);

		if(historialClinicoListVo != null) {
			XStream xStream = new XStream();
			
			for (HistorialClinicoListVo historialClinicoVo : historialClinicoListVo) {
				log.debug("xStream.toXML(historialClinicoVo): \n" + xStream.toXML(historialClinicoVo));
			}
		}
		log.debug("Fin - Test");
	}
	
	/**
	 * Proposito : Guardar un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 */
	@Test
	public void guardarHistorialClinico() {
		log.debug("Inicio - Test");
		
		HistorialClinicoVo historialClinicoVo = new HistorialClinicoVo();
		RespuestaVo respuestaVo = null;
		
		String idPaciente = "117";
		String fecha = "1994-11-04 00:00:00";
		String peso = "72.5";
		String talla = "70";
		String estatura = "170";
		String lipidos = "2.44";
		String carbohidratos = "3.4";
		String proteinas = "22.5";
		String azucar = "100";
		String imc = "22";
		
		{//SE ARMA EL VO
			historialClinicoVo.setIdPaciente(idPaciente);
			historialClinicoVo.setFecha(fecha);
			historialClinicoVo.setPeso(peso);
			historialClinicoVo.setTalla(talla);
			historialClinicoVo.setEstatura(estatura);
			historialClinicoVo.setImc(imc);
			historialClinicoVo.setLipidos(lipidos);
			historialClinicoVo.setCarbohidratos(carbohidratos);
			historialClinicoVo.setProteinas(proteinas);
			historialClinicoVo.setAzucar(azucar);		
		}	
		
		
		respuestaVo = historialClinicoFacade.guardarHistorialClinico(historialClinicoVo);
		
		log.debug("Fin - Test");
	}
	
}
