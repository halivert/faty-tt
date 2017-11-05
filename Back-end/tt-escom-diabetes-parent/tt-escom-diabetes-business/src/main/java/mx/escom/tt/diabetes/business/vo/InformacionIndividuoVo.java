package mx.escom.tt.diabetes.business.vo;

import lombok.Getter;
import lombok.Setter;

public class InformacionIndividuoVo {

	private @Getter @Setter String idIndividuo;
	
	private @Getter @Setter String idPaciente;
	
	private @Getter @Setter String idMedico;
	
	public @Getter @Setter String nombreCompleto;

	public @Getter @Setter String fechaNac;
}
