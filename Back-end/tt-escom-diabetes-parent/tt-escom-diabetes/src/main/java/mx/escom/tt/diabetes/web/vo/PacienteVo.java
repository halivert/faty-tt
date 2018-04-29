package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class PacienteVo {

	private @Getter @Setter String idPaciente;
	
	private @Getter @Setter String codigoMedico;
	
	private @Getter @Setter String edad;
	
	private @Getter @Setter String peso;
	
	private @Getter @Setter String estatura;
	
	private @Getter @Setter String actividad;
	
	private @Getter @Setter String sexo;
	
	private @Getter @Setter String porcentajeLipidos;
	
	private @Getter @Setter String porcentajeCarbohidratos;
	
	private @Getter @Setter String porcentajeProteinas;
	
	@Override
	public String toString() {
		return "edad : " + edad + "\n peso : " + peso + " \n estatura : " + estatura + "\n actividad : " + actividad + "\n sexo : " + sexo;
	}
}

