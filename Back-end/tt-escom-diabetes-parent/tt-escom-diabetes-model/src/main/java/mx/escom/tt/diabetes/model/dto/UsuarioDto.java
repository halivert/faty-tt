package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USUARIO")
public class UsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	@Column(name = "ID_USUARIO", unique = true)
	private @Getter @Setter Integer idUsuario;
	
	@Column(name = "NOMBRE", unique = false, length = 50)
	private @Getter @Setter String nombre;
	
	@Column(name = "APELLIDO_PATERNO", unique = false, length = 50)
	private @Getter @Setter String apellidoPaterno;
	
	@Column(name = "APELLIDO_MATERNO", unique = false, length = 50)
	private @Getter @Setter String apellidoMaterno;
	
	@Column(name = "EMAIL", unique = true)
	private @Getter @Setter String email;

	@Column(name = "KEYWORD", unique = true)
	private @Getter @Setter String keyword;

	@Column(name = "FECHA_NACIMIENTO", unique = true)
	private @Getter @Setter Date fechaNacimiento;
	
	@Column(name = "SEXO", unique = false, length = 50)
	private @Getter @Setter String sexo;

	/*@OneToOne(mappedBy="usuarioDto", cascade=CascadeType.ALL)  
	@OneToOne(fetch=FetchType.EAGER,mappedBy="usuarioDto",cascade=CascadeType.ALL)
	private @Getter @Setter MedicoDto medicoDto;*/
	
	

}
