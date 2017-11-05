package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MEDICO")
public class MedicoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO", unique = true)
	/*@GeneratedValue(generator="gen")  
    @GenericGenerator(name="gen", strategy="foreign",   
    parameters=@Parameter(name="property", value="usuarioDto")) */ 
	private @Getter @Setter Integer idUsuario;
	
	@Column(name = "CEDULA_PROFESIONAL", unique = true)
	private @Getter @Setter String cedulaProfesional;
	
	/*@OneToOne
	@PrimaryKeyJoinColumn
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_USUARIO")
	private @Getter @Setter UsuarioDto usuarioDto;*/
	
	

}
