package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class DietaVo {

	private @Getter @Setter String descripcion;
	
	private @Getter @Setter String alimentosDisponibles;
	
	private @Getter @Setter String caloriasDesayuno;
	
	private @Getter @Setter String caloriasC1;
	
	private @Getter @Setter String caloriasComida;
	
	private @Getter @Setter String caloriasC2;
	
	private @Getter @Setter String caloriasCena;
}
