package mx.escom.tt.diabetes.commons.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class MedicoPacientesVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private Integer ID_PACIENTE;
	@Getter @Setter private Integer ID_INDIVIDUO;
	@Getter @Setter private Integer ID_MEDICO;
	@Getter @Setter private String 	NOMBRE;
	@Getter @Setter private String 	AP_PAT;
	@Getter @Setter private String 	AP_MAT;
	@Getter @Setter private String 	EMAIL;
	@Getter @Setter private	Date	FEC_NAC;
	@Getter @Setter private	double	PESO;
	@Getter @Setter private	double	ESTATURA;
}
