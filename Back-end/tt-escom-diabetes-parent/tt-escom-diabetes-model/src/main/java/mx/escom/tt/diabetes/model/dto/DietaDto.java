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
@Table(name = "DIETA")
public class DietaDto implements Serializable{
	
	private static final long serialVersionUID = -6554087889516005123L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_DIETA", unique = true)
	private @Getter @Setter Integer idDieta;
	
	@Column(name = "ID_PACIENTE", unique = true)
	private @Getter @Setter Integer idPaciente;
	
	@Column(name = "ID_MEDICO", unique = true)
	private @Getter @Setter Integer idMedico;

	@Column(name = "DESCRIPCION", unique = true)
	private @Getter @Setter String descripcion;

	@Column(name = "ALIMENTOS_DISPONIBLES", unique = false)
	private @Getter @Setter String alimentosDisponibles;
	
	@Column(name = "FECHA_ASIGNACION", unique = false)
	//@Temporal(TemporalType.DATE)
	private @Getter @Setter Timestamp fechaAsignacion;
	
	@Column(name = "CALORIAS_DESAYUNO", unique = false)
	private @Getter @Setter Double caloriasDesayuno;
	
	@Column(name = "CALORIAS_C1", unique = false)
	private @Getter @Setter Double caloriasC1;
	
	@Column(name = "CALORIAS_COMIDA", unique = false)
	private @Getter @Setter Double caloriasComida;
	
	@Column(name = "CALORIAS_C2", unique = false)
	private @Getter @Setter Double caloriasC2;
	
	@Column(name = "CALORIAS_CENA", unique = false)
	private @Getter @Setter Double caloriasCena;
	
	@Override
	public String toString() {
		return "DIETA : [ idDieta : " +idDieta+ " idPaciente : " +idPaciente+" idMedico : " +idMedico+ " descripcion : " +descripcion+" alimentosDisponibles : " + alimentosDisponibles +"]";
	}
}
