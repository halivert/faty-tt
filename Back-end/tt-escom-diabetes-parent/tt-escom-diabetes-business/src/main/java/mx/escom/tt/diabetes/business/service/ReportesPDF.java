package mx.escom.tt.diabetes.business.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
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
		//JRXmlDataSource xmlDataSource = null;
		if(StringUtils.isEmpty(urlJasper)) {
			msjEx="La ruta del reporte jasper no puede ser nula o vacia.";
			throw new RuntimeException(msjEx);
		}
		
		if(collectionDataSource != null) {
			ds = new JRBeanCollectionDataSource(collectionDataSource);
			 /*try {
				  xmlDataSource =	new JRXmlDataSource("");
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
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
			log.error("NullPointerException ::: " + ex.getCause());			
			throw new RuntimeException(ex.getMessage());
		}catch (JRException e) {
			log.error("JRException ::: " + e.getCause());			
			throw new RuntimeException(e.getMessage());
		}catch(RuntimeException rtExc) { 
			log.error("RuntimeException ::: " + rtExc.getCause());			
			throw new RuntimeException(rtExc.getMessage());
		}catch(Exception ex) {
			log.error("Exception ::: " + ex.getCause());
			msjEx = Constants.MSJ_EXCEPTION + "crear la dieta en formato PDF";
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Service");
		return output;
	}

}
