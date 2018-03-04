package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "REGISTRO_GLUCOSA")
public class RegistroGlucosaDto implements Serializable{

	
	private static final long serialVersionUID = -7425463833081843847L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_REGISTRO_GLUCOSA", unique = true)
	private @Getter @Setter Integer idRegistroGlucosa;
	
	@Column(name = "ID_PACIENTE", unique = true)
	private @Getter @Setter Integer idPaciente;
	
	@Column(name = "FECHA_REGISTRO", unique = true)
	//@Temporal(TemporalType.DATE)
	private @Getter @Setter Timestamp fechaRegistro;
	
	@Column(name = "FECHA_ACTUALIZACION", unique = true)
	//@Temporal(TemporalType.DATE)
	private @Getter @Setter Timestamp fechaActualizacion;

	@Column(name = "AZUCAR", unique = false)
	private @Getter @Setter double azucar;
}
