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

@Entity
@Table(name = "ALIMENTOS")
public class AlimentoDto implements Serializable{

	private static final long serialVersionUID = 3856294053442400272L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ALIMENTO", unique = true)
	private @Getter @Setter Integer idAlimento;
	
	@Column(name = "TIPO_ALIMENTO", unique = true)
	private @Getter @Setter String tipoAlimento;
	
	@Column(name = "ALIMENTO", unique = true)
	private @Getter @Setter String alimento;
	
	@Column(name = "CANTIDAD", unique = true)
	private @Getter @Setter String cantidad;
	
	@Column(name = "UNIDAD", unique = true)
	private @Getter @Setter String unidad;
	
	@Column(name = "PESO_BGR", unique = true)
	private @Getter @Setter Integer pesoBgr;
	
	@Column(name = "PESO_NGR", unique = true)
	private @Getter @Setter Integer pesoNgr;
	
	@Column(name = "ENERGIA", unique = false)
	private @Getter @Setter double energia;
	
	@Column(name = "PROTEINA", unique = false)
	private @Getter @Setter double proteina;
	
	@Column(name = "LIPIDOS", unique = false)
	private @Getter @Setter double lipidos;
	
	@Column(name = "CARBOHIDRATOS", unique = false)
	private @Getter @Setter double carbohidratos;
	
	@Column(name = "FIBRA_GR", unique = false)
	private @Getter @Setter double fibraGr;
	
	@Column(name = "VITAMINA_A", unique = false)
	private @Getter @Setter double vitaminaA;
	
	@Column(name = "ACIDO_ASCORBICO", unique = false)
	private @Getter @Setter double acidoAscorbico;
	
	@Column(name = "ACIDO_FOLICO", unique = false)
	private @Getter @Setter double acidoFolico;
	
	@Column(name = "HIERRO_NO", unique = false)
	private @Getter @Setter double hierroNo;
	
	@Column(name = "POTASIO", unique = false)
	private @Getter @Setter double potasio;
	
	@Column(name = "AZUCAR_GR", unique = false)
	private @Getter @Setter double azucarGr;
	
	@Column(name = "CARGA_GL", unique = true)
	private @Getter @Setter String cargaGl;
	
	@Column(name = "AZUCAR_PE", unique = true)
	private @Getter @Setter String azucarPe;
	
	@Column(name = "COLESTEROL", unique = true)
	private @Getter @Setter String colesterol;
	
	@Column(name = "AG_SATURADOS", unique = true)
	private @Getter @Setter String agSaturados;
	
	@Column(name = "AG_MSATURADOS", unique = true)
	private @Getter @Setter String agMsaturados;
	
	@Column(name = "AG_PSATURADOS", unique = true)
	private @Getter @Setter String agPsaturados;
	
	@Column(name = "CALCIO", unique = true)
	private @Getter @Setter String calcio;
	
	@Column(name = "SELENIO", unique = true)
	private @Getter @Setter String selenio;
	
	@Column(name = "FOSFORO", unique = true)
	private @Getter @Setter String fosforo;
	
	@Column(name = "POTASIO_P", unique = true)
	private @Getter @Setter String potasioP;
	
	@Column(name = "HIERRO", unique = true)
	private @Getter @Setter String hierro;
	
	@Column(name = "SODIO", unique = true)
	private @Getter @Setter String sodio;
		
}
