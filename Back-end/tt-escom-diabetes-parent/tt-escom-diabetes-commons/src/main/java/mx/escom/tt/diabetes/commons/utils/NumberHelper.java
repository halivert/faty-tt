package mx.escom.tt.diabetes.commons.utils;

public class NumberHelper {
	
	/**
	 * Proposito : Verificar si una cadena tiene caracteres no numericos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param str			-	Cadena que se evaluara
	 * @return boolean		-	True si la cadena tiene solo numeros False en caso contrario
	 */
	public static boolean isNumeric(String str){  
	  try  
	  {  
	    @SuppressWarnings("unused")
		double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe){  
	    return false;  
	  }  
	  
	  return true;  
	}
	
}
