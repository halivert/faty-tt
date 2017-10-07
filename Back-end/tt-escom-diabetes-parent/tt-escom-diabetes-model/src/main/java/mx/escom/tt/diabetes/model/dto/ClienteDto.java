package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="CLIENTE")
public class ClienteDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID_CLIENTE", unique = true, length = 50)
	private @Getter @Setter String idCliente;
	
	@Column(name = "NOMBRE", unique = false, length = 45)
	private @Getter @Setter String nombre;

	@Column(name = "EDAR", unique = false, length = 45)
	private @Getter @Setter int edad;
	
	@Override
	public String toString() {
		return "idCliente : " + idCliente + " nombre : " + nombre + " edad : " + edad;
	}
}
