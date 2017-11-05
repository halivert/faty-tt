package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class HistorialClinicoListVo {

	private @Getter @Setter String idHistorialClinico;
	
	private @Getter @Setter String idPaciente;
	
	private @Getter @Setter String peso;
	
	private @Getter @Setter String talla;
	
	private @Getter @Setter String fecha;
	
	private @Getter @Setter String azucar;
}
