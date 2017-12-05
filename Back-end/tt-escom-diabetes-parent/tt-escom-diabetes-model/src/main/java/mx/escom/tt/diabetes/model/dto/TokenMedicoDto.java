package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TOKEN_MEDICO")
public class TokenMedicoDto implements Serializable{

private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TOKEN", unique = true)
	private @Getter @Setter String token;
	
	@Column(name = "ID_MEDICO", unique = true)
	private @Getter @Setter Integer idMedico;
	
}
