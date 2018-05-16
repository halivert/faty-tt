package mx.escom.tt.diabetes.business.service;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class CustomSecurity {

	
	@Qualifier("FormatoFechaNacimiento")
	@Autowired private SimpleDateFormat dmyDateFormat;
	
	/**
	 * Proposito : Crear un token de seguridad para poder acceder a paginas que lo requieran 
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param username						-	Usuario a quien se quiere generar el token 
	 * @return String						-	Token codificado en base 64
	 * @throws RuntimeException				-	Si ocurre un error durante la ejecucion del metodo 
	 */
	public String createSecurityToken(String username) throws RuntimeException{
		log.debug("Inicio - Service");
		
		Date date  = new Date();

		username = username + "-" + dmyDateFormat.format(date);
		byte [] byteArray = username.getBytes();
		
		// encode without padding
		String encoded = Base64.getEncoder().withoutPadding().encodeToString(byteArray);
		
		log.debug("Fin - Service");
		return encoded;
	}
	
	/**
	 * Proposito : Decodificar un codigo de seguridad
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param token						-	Token para decodificarlo
	 * @return							-	Respuesta luego de la decodificacion 
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo
	 */
	public String decodeSecurityToken(String token) throws RuntimeException{
		log.debug("Inicio - Service");
		
		// decode a String
		byte [] byteDecode = Base64.getDecoder().decode(token);
		
		String response = new String(byteDecode);
		
		log.debug("Fin - Service");
		return response;
	}
}
