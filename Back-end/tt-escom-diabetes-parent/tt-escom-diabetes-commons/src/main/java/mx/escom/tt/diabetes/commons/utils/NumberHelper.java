package mx.escom.tt.diabetes.commons.utils;

public class NumberHelper {
	
	
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
