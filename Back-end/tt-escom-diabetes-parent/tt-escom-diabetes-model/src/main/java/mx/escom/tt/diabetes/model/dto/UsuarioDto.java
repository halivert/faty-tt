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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USUARIO")
public class UsuarioDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID_USUARIO", unique = true)
	private @Getter @Setter Integer idUsuario;
	
	@Column(name = "NOMBRE", unique = false, length = 45)
	private @Getter @Setter String nombre;

	@Column(name = "FECHA_NAC", unique = false)
	private @Getter @Setter Date fechaNac;
	
	@Override
	public String toString() {
		return "idUsuario : " + idUsuario + " nombre : " + nombre + " fechaNac : " + fechaNac;
	}
}
