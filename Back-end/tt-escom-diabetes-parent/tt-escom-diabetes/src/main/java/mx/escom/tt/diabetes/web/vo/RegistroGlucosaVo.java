package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class RegistroGlucosaVo {

	private @Getter @Setter String idRegistroGlucosa;
	
	private @Getter @Setter String idPaciente;
	
	private @Getter @Setter String fechaRegistro;
	
	private @Getter @Setter String fechaActualizacion;
	
	private @Getter @Setter String azucar;
}
