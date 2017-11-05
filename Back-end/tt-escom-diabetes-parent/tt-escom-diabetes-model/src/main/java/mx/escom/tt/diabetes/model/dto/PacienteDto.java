package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Edgar
 *
 */
@Entity
@Table(name="PACIENTE")
public class PacienteDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_USUARIO", unique = true)
	private @Getter @Setter Integer idUsuario;
		
	@Column(name = "ID_MEDICO", unique = true)
	private @Getter @Setter Integer idMedico;
	
	

}
