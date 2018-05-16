package mx.escom.tt.diebetes.dtree;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.vo.DietaReporteVo;
import mx.escom.tt.diabetes.commons.utils.Constants;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@CommonsLog
public class DecisionTreeExample {

	public byte[] init() throws RuntimeException{
		log.debug("Inicio - PDF");
		
		Map<String, Object> parameters = new HashMap<>();
		byte[] output = null;
		String msjEx = null;
		OutputStream outputStream = null;
		
		parameters.put("nombrePaciente", "Nombre parametro");
		parameters.put("edadPaciente", "24");
		parameters.put("estaturaPaciente", "1.73");
		parameters.put("pesoPaciente", "70");
		List<DietaReporteVo> listaDietaReporteVo = new ArrayList<DietaReporteVo>();
		DietaReporteVo dietaReporteVo = new DietaReporteVo();
		
		
		listaDietaReporteVo.add(dietaReporteVo);
		try {
		    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDietaReporteVo);

		    
			InputStream inputStream = DecisionTreeExample.class.getResourceAsStream("caratula_poliza_rc_obligatorio.jasper");
			//JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters);
			
			output = JasperRunManager.runReportToPdf(inputStream, parameters, ds);
			
			
		} catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - PDF");
		return output;
	}
}
