package mx.escom.tt.diabetes.commons.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class UltimoHistorialClinicoVo implements Serializable{

	private static final long serialVersionUID = -3251540575891822295L;

	@Getter @Setter private Integer ID_HISTORIAL_CLINICO;
	@Getter @Setter private Integer ID_PACIENTE;
	@Getter @Setter private String 	NOMBRE;
	@Getter @Setter private String 	APELLIDO_PATERNO;
	@Getter @Setter private String 	APELLIDO_MATERNO;
	@Getter @Setter private String 	EMAIL;
	@Getter @Setter private	Date	FECHA_NACIMIENTO;
	@Getter @Setter private	String	SEXO;
	@Getter @Setter private	Timestamp	FECHA;
	@Getter @Setter private	double	PESO;
	@Getter @Setter private	double	TALLA;
	@Getter @Setter private	double	ESTATURA;
	@Getter @Setter private	double	IMC;
	@Getter @Setter private	double	LIPIDOS;
	@Getter @Setter private	double	CARBOHIDRATOS;
	@Getter @Setter private	double	PROTEINAS;
	@Getter @Setter private	double	AZUCAR;
	
}