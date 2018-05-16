package mx.escom.tt.diabetes.web.vo;

import lombok.Getter;
import lombok.Setter;

public class UsuarioVo {
	private @Getter @Setter String idUsuario;
	private @Getter @Setter String nombre;
	private @Getter @Setter String apellidoPaterno;
	private @Getter @Setter String apellidoMaterno;
	private @Getter @Setter String edad;
	private @Getter @Setter String email;
	private @Getter @Setter String keyword;
	private @Getter @Setter String fechaNacimiento;
	private @Getter @Setter String sexo;
	private @Getter @Setter String idRol;
	private @Getter @Setter String cedulaProfesional;
	private @Getter @Setter String codigoMedico;
	
}
