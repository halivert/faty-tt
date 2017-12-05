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
	@Column(name = "ID_USUARIO", unique = true)
	private @Getter @Setter Integer idUsuario;
	
	@Column(name = "CEDULA_PROFESIONAL", unique = true)
	private @Getter @Setter String cedulaProfesional;
	

}
