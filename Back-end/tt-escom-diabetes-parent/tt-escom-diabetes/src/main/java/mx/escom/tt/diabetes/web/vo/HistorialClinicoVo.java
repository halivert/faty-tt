package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class HistorialClinicoVo {
	
	private @Getter @Setter String idHistorialClinico;
	
	private @Getter @Setter String idPaciente;
	
	private @Getter @Setter String fecha;
	
	private @Getter @Setter String peso;
	
	private @Getter @Setter String talla;
	
	private @Getter @Setter String estatura;
	
	private @Getter @Setter String imc;
	
	private @Getter @Setter String lipidos;
	
	private @Getter @Setter String carbohidratos;
	
	private @Getter @Setter String proteinas;
	
	private @Getter @Setter String azucar;
	
	private @Getter @Setter String actividadFisica;
	
	private @Getter @Setter String observaciones;
}
