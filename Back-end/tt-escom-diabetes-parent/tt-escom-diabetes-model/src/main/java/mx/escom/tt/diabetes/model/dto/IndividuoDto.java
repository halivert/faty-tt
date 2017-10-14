/**
 *
 */
package mx.escom.tt.diabetes.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * @author hali
 */
@Entity
@Table(name = "INDIVIDUO")
public class IndividuoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_INDIVIDUO", unique = true)
	private @Getter @Setter Integer idIndividuo;

	@Column(name = "NOMBRE", unique = true)
	private @Getter @Setter String nombre;

	@Column(name = "APELLIDO_PATERNO", unique = true)
	private @Getter @Setter String apellidoPaterno;

	@Column(name = "APELLIDO_MATERNO", unique = true)
	private @Getter @Setter String apellidoMaterno;

	@Column(name = "EMAIL", unique = true)
	private @Getter @Setter String email;

	@Column(name = "KEYWORD", unique = true)
	private @Getter @Setter String keyword;

	@Column(name = "FECHA_NACIMIENTO", unique = true)
	private @Getter @Setter Date fechaNacimiento;

	@Column(name = "ID_SEXO", unique = true)
	private @Getter @Setter Integer idSexo;

	@Column(name = "ROL", unique = true)
	private @Getter @Setter Integer rol;
}

