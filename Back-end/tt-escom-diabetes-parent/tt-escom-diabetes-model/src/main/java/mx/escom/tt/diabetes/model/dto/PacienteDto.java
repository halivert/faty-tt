package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PACIENTE", unique = true)
	private @Getter @Setter Integer idPaciente;
	
	
	@Column(name = "ID_INDIVIDUO", unique = true)
	private @Getter @Setter Integer idIndividuo;
	
	@Column(name = "ID_MEDICO", unique = true)
	private @Getter @Setter Integer idMedico;
	
	@Column(name = "PESO", unique = false)
	private @Getter @Setter double peso;
	
	@Column(name = "TALLA", unique = false)
	private @Getter @Setter double talla;
	
	@Column(name = "ESTATURA", unique = false)
	private @Getter @Setter double estatura;
	
	@Column(name = "IMC", unique = false)
	private @Getter @Setter double imc;
	
	@Column(name = "LIPIDOS", unique = false)
	private @Getter @Setter double lipidos;
	
	@Column(name = "CARBOHIDRATOS", unique = false)
	private @Getter @Setter double carbohidratos;
	
	@Column(name = "PROTEINAS", unique = false)
	private @Getter @Setter double proteinas;

}
