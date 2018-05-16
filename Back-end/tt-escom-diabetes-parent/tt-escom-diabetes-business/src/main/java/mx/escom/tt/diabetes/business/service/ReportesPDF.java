package mx.escom.tt.diabetes.business.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("ReportesPDF")
@CommonsLog
public class ReportesPDF {
	
	
	
	public byte[] crearReporte(String urlJasper, String urlSubreport, Map<String, Object> parameters, List<?> collectionDataSource) throws RuntimeException{
		log.debug("Inicia - Service");
		
		byte[] output = null;
		String msjEx = null;
		JRBeanCollectionDataSource ds = null;
		InputStream inputStream = null;
		Boolean isDataSource = Boolean.FALSE;
		
		if(StringUtils.isEmpty(urlJasper)) {
			msjEx="La ruta del reporte jasper no puede ser nula o vacia.";
			throw new RuntimeException(msjEx);
		}
		
		if(collectionDataSource != null) {
			ds = new JRBeanCollectionDataSource(collectionDataSource);
			isDataSource = Boolean.TRUE;
		}
		
		
		try {
			inputStream = ReportesPDF.class.getResourceAsStream(urlJasper);
			
			if(inputStream == null) {
				msjEx="No se encontró el reporte para crear el documento.";
				throw new NullPointerException(msjEx);
			}
			
			if(isDataSource) {
				output = JasperRunManager.runReportToPdf(inputStream, parameters, ds);
			}else {
				output = JasperRunManager.runReportToPdf(inputStream, parameters, ds);
			}

			
		}catch(NullPointerException ex) {
			throw new RuntimeException(ex.getMessage());
		}catch (JRException e) {
			throw new RuntimeException(e.getMessage());
		}catch(RuntimeException rtExc) { 
			throw rtExc;
		}catch(Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "crear la dieta en formato PDF";
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return output;
	}

}
