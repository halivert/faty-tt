package mx.escom.tt.diabetes.commons.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class RegistroGlucosaCommonVo implements Serializable{

	private static final long serialVersionUID = 6195187388878712485L;
	
	@Getter @Setter private Integer ID_REGISTRO_GLUCOSA;
	@Getter @Setter private Integer ID_PACIENTE;
	@Getter @Setter private Timestamp FECHA_REGISTRO;
	@Getter @Setter private Timestamp FECHA_ACTUALIZACION;	
	@Getter @Setter private double AZUCAR;
}
