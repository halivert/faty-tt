package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class DietaReportePDFVo {
	
	
	private @Getter @Setter String nombrePaciente;
	private @Getter @Setter String edadPaciente;
	private @Getter @Setter String estaturaPaciente;
	private @Getter @Setter String pesoPaciente;
	private @Getter @Setter String gastoET;
	private @Getter @Setter String descripcion;

}
