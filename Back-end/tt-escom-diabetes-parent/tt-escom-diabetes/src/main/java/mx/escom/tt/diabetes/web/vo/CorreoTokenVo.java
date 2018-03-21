package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class CorreoTokenVo {
	
	private @Getter @Setter String[] destinatario;
	
	private @Getter @Setter String nombreMedico;
	
	private @Getter @Setter String token;

	private @Getter @Setter String remitente;
}
