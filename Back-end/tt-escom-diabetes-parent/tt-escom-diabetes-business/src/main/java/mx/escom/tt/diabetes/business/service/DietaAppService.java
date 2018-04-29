package mx.escom.tt.diabetes.business.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.vo.ValoresNutrimentalesDietaVo;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.DietaDao;
import mx.escom.tt.diabetes.model.dto.DietaDto;

@CommonsLog
@Service
public class DietaAppService {

	@Autowired
	DietaDao dietaDao;

	@Resource(name = "MapaFactorActividad")
	private Map<String, Double> mapaFactorActividad;
	
	@Resource(name = "MapaIndiceActividadFisica")
	private Map<Integer, String> mapaIndiceActividadFisica;


	/**
	 * Proposito : Guardar la una nueva dieta en la base de datos
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param dietaDto
	 *            - Informacion que se desea guardar
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion del metodo
	 */
	public void guardarDietaAppService(DietaDto dietaDto) throws RuntimeException {
		log.debug("Inicio - Service");
		String msjEx = null;

		{// Validaciones
			if (dietaDto == null) {
				msjEx = "La información de la dieta no puede ser nula o vacía.";
				throw new RuntimeException(msjEx);
			}
		}

		try {
			dietaDao.guardarDieta(dietaDto);
		} catch (RuntimeException rtExc) {
			throw rtExc;
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la dieta.";
			throw new RuntimeException(msjEx, ex);
		}
		log.debug("Fin - Service");
	}

	/**
	 * Proposito : Recuperar una dieta por medio de su identificador
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param idDieta
	 *            - Identificador de la dieta que se desea recuperar
	 * @return DietaDto - Dieta que se recupero de la base de datos
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion del metodo
	 */
	public DietaDto recuperarDietaPorIdAppService(Integer idDieta) throws RuntimeException {
		log.debug("Inicio - Service");

		String msjEx = null;
		DietaDto dietaDto = null;
		{// Validaciones
			if (idDieta == null) {
				msjEx = "El identificador de la dieta no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}

		try {
			dietaDto = dietaDao.recuperarDietaPorId(idDieta);
			if (dietaDto == null) {
				msjEx = "No se encontraron registros.";
				throw new RuntimeException(msjEx);
			}
		} catch (RuntimeException rtExc) {
			throw rtExc;
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx, ex);
		}
		log.debug("Fin - Service");
		return dietaDto;
	}

	/**
	 * Proposito : Recuperar una lista de dietas asociadas a un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 * @param idPaciente
	 *            - Identificador del paciente del que se quiere recuperar las
	 *            dietas
	 * @return List<DietaDto> - Dietas recuperadas
	 * @throws RuntimeException
	 *             - Si ocurre un error durante la ejecucion
	 */
	public List<DietaDto> recuperarDietaPorIdPacienteAppService(Integer idPaciente) throws RuntimeException {
		log.debug("Inicio - Service");

		String msjEx = null;
		List<DietaDto> dietaDtoList = null;

		{// Validaciones
			if (idPaciente == null) {
				msjEx = "El identificador del paciente no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}

		try {
			dietaDtoList = dietaDao.recuperarDietaPorIdPaciente(idPaciente);
			log.debug("dietaDtoList.size() : " + dietaDtoList.size());
			if (dietaDtoList == null || dietaDtoList.size() == 0) {
				msjEx = "No se encontraron dietas asignadas.";
				throw new RuntimeException(msjEx);
			}
		} catch (RuntimeException rtExc) {
			throw rtExc;
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la lista de alimentos";
			throw new RuntimeException(msjEx, ex);
		}
		log.debug("Inicio - Service");
		return dietaDtoList;
	}
	
	/**
	 * Proposito : Calcular los valores nutrimentales que debe consumir un paciente con base en su informacion personal
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 22/04/2018
	 * @param edad							-	Edad del paciente
	 * @param peso							-	Peso del paciente
	 * @param estatura						-	Estatura del paciente
	 * @param actividadFisica				-	Actividad fisica 
	 * @param sexo							-	Sexo del paciente
	 * @param pLipidos						-	Porcentaje de lipidos
	 * @param pCarbohidratos					-	Porcentaje de carbohidratos
	 * @param pProteinas						-	Porcentaje de proteinas
	 * @return ValoresNutrimentalesDietaVo	-	Objeto con la informacion de los valores nutrimentales asignados para armar una dieta
	 * @throws RuntimeException				-	Si ocurre un erro durante la ejecucion del metodo 
	 */
	public ValoresNutrimentalesDietaVo calcularValoresNutrimentalesDietaAppService(Integer edad, Double peso, Double estatura,
			String actividadFisica, Integer sexo, Double pLipidos, Double pCarbohidratos, Double pProteinas)
			throws RuntimeException {
		log.debug("Inicio - Service");
		
		Double ger = null;
		String msjEx = null;
		Double get = null;
		Double klipidos = null;
		Double kCarbohidratos = null;
		Double kProteinas = null;
		Double glipidos = null;
		Double gCarbohidratos = null;
		Double gProteinas = null;
		ValoresNutrimentalesDietaVo valoresNutrimentalesDietaVo = null;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		{// Validaciones
			if (StringUtils.isEmpty(actividadFisica)) {
				msjEx = "La actividad física del paciente no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
			if (!StringUtils.isNumeric(actividadFisica)) {
				msjEx = "El formato de la actividad física es incorrecto, favor de verificar";
				throw new RuntimeException(msjEx);
			}
			if (pLipidos == null) {
				msjEx = "El porcentaje de lípidos no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
			if (pCarbohidratos == null) {
				msjEx = "El porcentaje de carbohidratos no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
			if (pProteinas == null) {
				msjEx = "El porcentaje de proteinas no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
		}
		actividadFisica = mapaIndiceActividadFisica.get(Integer.valueOf(actividadFisica));
		actividadFisica = actividadFisica.replaceAll("\\s+","").toLowerCase();
		// Si es hombre
		if (sexo == 1) {
			actividadFisica = actividadFisica + "m";
		} else if (sexo == 0) {
			actividadFisica = actividadFisica + "f";
		}

		try {
			ger = calcularGER(edad, peso, estatura, sexo);
			get = calcularGET(ger, actividadFisica);

			pLipidos = pLipidos / 100;
			pCarbohidratos = pCarbohidratos / 100;
			pProteinas = pProteinas / 100;

			klipidos = pLipidos * get;
			kCarbohidratos = pCarbohidratos * get;
			kProteinas = pProteinas * get;

			glipidos = klipidos / 9;
			gCarbohidratos = kCarbohidratos / 4;
			gProteinas = kProteinas / 4;
			
			// Se llena el VO
			valoresNutrimentalesDietaVo = new ValoresNutrimentalesDietaVo();
			valoresNutrimentalesDietaVo.setGet(numberFormat.format(get));
			valoresNutrimentalesDietaVo.setGCarbohidratos(numberFormat.format(gCarbohidratos));
			valoresNutrimentalesDietaVo.setGlipidos(numberFormat.format(glipidos));
			valoresNutrimentalesDietaVo.setGProteinas(numberFormat.format(gProteinas));
			valoresNutrimentalesDietaVo.setKCarbohidratos(numberFormat.format(kCarbohidratos));
			valoresNutrimentalesDietaVo.setKlipidos(numberFormat.format(klipidos));
			valoresNutrimentalesDietaVo.setKProteinas(numberFormat.format(kProteinas));
		} catch (RuntimeException rtExc) {
			throw rtExc;
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "calcular los valores nutrimentales de la dieta.";
			throw new RuntimeException(msjEx, ex);
		}

		log.debug("Fin - Service");
		return valoresNutrimentalesDietaVo;
	}
	
	/**
	 * Proposito : Calcular el GER (Gasto energetico en reposo) de un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 22/04/2018
	 * @param edad								-	Edad del paciente
	 * @param peso								-	Peso del paciente
	 * @param estatura							-	Estatura del paciente
	 * @param sexo								-	Sexo del paciente
	 * @return	Double							-	GER calculado
	 * @throws RuntimeException					-	Si ocurre un error durante la ejecucion del metodo
	 */
	public Double calcularGER(Integer edad, Double peso, Double estatura, Integer sexo) throws RuntimeException {
		String msjEx = null;
		Double ger = null;

		{// Validaciones
			if (edad == null) {
				msjEx = "La edad del paciente no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
			if (peso == null) {
				msjEx = "El peso del paciente no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
			if (estatura == null) {
				msjEx = "La estatura del paciente no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
			if (sexo == null) {
				msjEx = "El sexo del paciente no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		try {
			// Si es hombre
			if (sexo == 1) {
				if (edad > 10 && edad <= 18) {
					ger = (17.5 * peso) + 651.0;
				} else if (edad > 18 && edad <= 30) {
					ger = (15.3 * peso) + 679.0;
				} else if (edad > 30 && edad <= 60) {
					ger = (11.6 * peso) + 879.0;
				} else if (edad > 60) {
					ger = (13.5 * peso) + 487.0;
				} else {
					msjEx = "El paciente debe tener más de 10 años.";
					throw new RuntimeException(msjEx);
				}
			}
			// Si es mujer
			else if (sexo == 0) {
				if (edad > 10 && edad <= 18) {
					ger = (12.2 * peso) + 746.0;
				} else if (edad > 18 && edad <= 30) {
					ger = (14.7 * peso) + 496.0;
				} else if (edad > 30 && edad <= 60) {
					ger = (14.7 * peso) + 746.0;
				} else if (edad > 60) {
					ger = (10.5 * peso) + 596.0;
				} else {
					msjEx = "El paciente debe tener más de 10 años.";
					throw new RuntimeException(msjEx);
				}
			}
		} catch (RuntimeException rtExc) {
			throw rtExc;
		} catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "calcular el GER";
			throw new RuntimeException(msjEx, ex);
		}
		return ger;
	}

	/**
	 * Proposito : Calcular el GET (Gasto Energetico Total) para un paciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 22/04/2018
	 * @param ger						-	Gasto energetico en reposo
	 * @param actividadFisica			-	Actividad fisica del paciente
	 * @return Double					-	GET calculado
	 * @throws RuntimeException			-	Si ocurre un error durante la ejecucion del metodo
	 */
	public Double calcularGET(Double ger, String actividadFisica) throws RuntimeException {
		log.debug("Inicio - Service");
		Double get = null;
		String msjEx = null;

		log.debug("mapaFactorActividad.get(actividadFisica) : " + mapaFactorActividad.get(actividadFisica));
		if (mapaFactorActividad.get(actividadFisica) == null) {
			msjEx = "No se encontró el factor de actividad para el paciente.";
			throw new RuntimeException(msjEx);
		}
		get = ger * mapaFactorActividad.get(actividadFisica);

		log.debug("Fin - Service");
		return get;
	}

}
