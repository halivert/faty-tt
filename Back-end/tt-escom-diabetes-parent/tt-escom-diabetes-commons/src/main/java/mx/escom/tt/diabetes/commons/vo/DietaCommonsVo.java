package mx.escom.tt.diabetes.commons.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class DietaCommonsVo implements Serializable{

	
	private static final long serialVersionUID = -4179775690068232839L;

	@Getter @Setter private Integer ID_DIETA;
	
	@Getter @Setter private Integer ID_PACIENTE;
	
	@Getter @Setter private Integer ID_MEDICO;
	
	@Getter @Setter private String DESCRIPCION;
	
	@Getter @Setter private Timestamp FECHA_ASIGNACION;
}
