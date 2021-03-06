package mx.escom.tt.diabetes.web.service.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.vo.ValoresNutrimentalesDietaVo;
import mx.escom.tt.diabetes.model.dto.DietaDto;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.web.facade.DietaFacade;
import mx.escom.tt.diabetes.web.facade.HistorialClinicoFacade;
import mx.escom.tt.diabetes.web.facade.PacienteFacade;
import mx.escom.tt.diabetes.web.facade.RegistroGlucosaFacade;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoListVo;
import mx.escom.tt.diabetes.web.vo.HistorialClinicoVo;
import mx.escom.tt.diabetes.web.vo.PacienteVo;
import mx.escom.tt.diabetes.web.vo.RegistroGlucosaVo;
import mx.escom.tt.diabetes.web.vo.RespuestaErrorVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
import mx.escom.tt.diabetes.web.vo.UltimoHistorialFacadeVo;

@CommonsLog
@RestController
@RequestMapping(value = "/ceres/pacientes")
public class PacienteController {
	
	@Autowired PacienteFacade pacienteFacade;
	@Autowired HistorialClinicoFacade historialClinicoFacade;
	@Autowired RegistroGlucosaFacade registroGlucosaFacade; 
	@Autowired DietaFacade dietaFacade;

	
	/**
	 * 
	 * Proposito: Metodo para recuperar datos de un Paciente
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 17/10/2017
	 * @param idPaciente 					- Identificador del paciente
	 * @return ResponseEntity<UsuarioDto> 	- Objeto que contiene la informacion del usuario
	 */
	@RequestMapping(value = "/{idPaciente}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> buscarPacientePorId(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		PacienteDto pacienteDto = null;
		RespuestaVo respuestaError=null;
		
		try {
			pacienteDto = pacienteFacade.recuperarPacientePorId(idPaciente);	
			result = new ResponseEntity<PacienteDto>(pacienteDto, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaError = new RespuestaVo();
			respuestaError.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaVo>(respuestaError, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "/{idPaciente}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> cambiarMedicoPaciente(@RequestBody PacienteVo pacienteVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaVo respuestaVo = null;
		
		try {
			respuestaVo = pacienteFacade.cambiarMedicoPaciente(pacienteVo.getIdPaciente(), pacienteVo.getCodigoMedico());
			result = new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaVo = new RespuestaVo();
			respuestaVo.setRespuesta("ERROR");
			respuestaVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaVo>(respuestaVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito :  Recuperar lista de historial clinico de un paciente
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPaciente
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/historialclinico", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperaListaHistorialClinicoPorIdPaciente(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<HistorialClinicoListVo> listHistorialClinicoListVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			
			listHistorialClinicoListVo = historialClinicoFacade.recuperarListaHistorialClinicoPorPaciente(idPaciente);
			result = new ResponseEntity <List<HistorialClinicoListVo>>(listHistorialClinicoListVo,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 18/02/2018
	 * @param idPacienteStr
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/ultimoHistorialclinico", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarUltimoHistorialPorIdPaciente(@PathVariable("idPaciente") String idPacienteStr) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		UltimoHistorialFacadeVo ultimoHistorialFacadeVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			
			ultimoHistorialFacadeVo = historialClinicoFacade.recuperarUltimoHistorialClinicoPorIdPaciente(idPacienteStr);
			result = new ResponseEntity <UltimoHistorialFacadeVo>(ultimoHistorialFacadeVo,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito :  Recuperar un historial clinico por medio de su Identificador.
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param idPaciente			-	Identificador del paciente.
	 * @param idHistorialClinico	-	Identificador del historial clinico a buscar.
	 * @return ResponseEntity		-	Respuesta de la solicitud
	 */
	@RequestMapping(value = "/{idPaciente}/historialclinico/{idHistorialClinico}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarHistorialClinicoPorId(@PathVariable("idPaciente") String idPaciente, @PathVariable("idHistorialClinico") String idHistorialClinico) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		HistorialClinicoVo historialClinicoVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			
			historialClinicoVo = historialClinicoFacade.recuperarHistorialClinicoPorId(idHistorialClinico);
			result = new ResponseEntity <HistorialClinicoVo>(historialClinicoVo,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "/{idPaciente}/historialclinico/{idHistorialClinico}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> actualizarHistorialClinicoPorId(@PathVariable("idPaciente") String idPaciente, @PathVariable("idHistorialClinico") String idHistorialClinico, @RequestBody HistorialClinicoVo historialClinicoVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaErrorVo respuestaErrorVo = null;
		RespuestaVo respuesta = null;
		
		try {
			
			historialClinicoVo.setIdHistorialClinico(idHistorialClinico);
			historialClinicoVo.setIdPaciente(idPaciente);
			
			respuesta = historialClinicoFacade.actualizarHistorialClinico(historialClinicoVo);
			
			result = new ResponseEntity <RespuestaVo>(respuesta,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Guardar un historial clinico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/11/2017
	 * @param historialClinicoVo		-	Informacion del historial clinico que se quiere guardar.
	 * @return ResponseEntity<?>		-	Respuesta de la solicitud.
	 */
	@RequestMapping(value = "/historialclinico", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarHistorialClinico(@RequestBody HistorialClinicoVo historialClinicoVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaVo respuesta = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			log.debug("historialClinicoVo : " + historialClinicoVo);
			respuesta = historialClinicoFacade.guardarHistorialClinico(historialClinicoVo);
			result = new ResponseEntity <RespuestaVo>(respuesta,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}	
	
	/**
	 * Proposito : Guardar un registro de glucosa en la base de datos
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param idPaciente
	 * @param registroGlucosaVo
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> guardarRegistroGlucosa(@PathVariable("idPaciente") String idPaciente, @RequestBody RegistroGlucosaVo registroGlucosaVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaVo respuesta = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			log.debug("idPaciente : " + idPaciente);
			log.debug("fechaRegistro : " + registroGlucosaVo.getFechaRegistro());
			log.debug("azucar : " + registroGlucosaVo.getAzucar());
			
			registroGlucosaVo.setIdPaciente(idPaciente);			
			respuesta = registroGlucosaFacade.guardaRegistroGlucosa(registroGlucosaVo);
			result = new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Actualizar un registro de glucosa
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 04/02/2018
	 * @param idPaciente
	 * @param idRegistroGlucosa					-	Identificador del registro de glucosa que se quiere actualizar
	 * @param registroGlucosaVo					-	Objeto que se va a actualizar
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa/{idRegistroGlucosa}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> actulizarRegistroGlucosa(@PathVariable("idPaciente") String idPaciente,@PathVariable("idRegistroGlucosa") String idRegistroGlucosa ,@RequestBody RegistroGlucosaVo registroGlucosaVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RespuestaVo respuesta = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			registroGlucosaVo.setIdPaciente(idPaciente);
			registroGlucosaVo.setIdRegistroGlucosa(idRegistroGlucosa);
			
			respuesta = registroGlucosaFacade.actualizaRegistroGlucosa(registroGlucosaVo);
			result = new ResponseEntity<RespuestaVo>(respuesta, HttpStatus.OK);
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar los registros de glucosa asociados a un apciente
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @param idPaciente					-	Identificador del paciente
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarRegistroGlucosaPorIdPaciente(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<RegistroGlucosaVo> registroGlucosaVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			registroGlucosaVo = registroGlucosaFacade.recuperaRegistroGlucosaPorIdPaciente(idPaciente);
			
			result = new ResponseEntity<List<RegistroGlucosaVo>>(registroGlucosaVo, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar registros de azucar asociados a un paciente, limitando el numero de la lista
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 14/04/2018
	 * @param idPaciente					-	Identificador del paciente
	 * @param limiteRegistro				-	Numero maximo que se va a recuperar 
	 * @return	ResponseEntity<List<RegistroGlucosaVo>>	- Lista con los registros recuperados
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa/limiteRegistro/{limiteRegistro}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperaNRegistroGlucosa(@PathVariable("idPaciente") String idPaciente, @PathVariable("limiteRegistro") String limiteRegistro) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<RegistroGlucosaVo> registroGlucosaVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			registroGlucosaVo = registroGlucosaFacade.recuperaNRegistroGlucosa(idPaciente, limiteRegistro);
			
			result = new ResponseEntity<List<RegistroGlucosaVo>>(registroGlucosaVo, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar una lista de registros de glucosa entre dos fechas
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 15/04/2018
	 * @param idPaciente						-	Identificador del paciente del que se recuperaran lso registros de glucosa
	 * @param fechaInicio					-	Fecha inicio del periodo
	 * @param fechaFin						-	Fecha fin del periodo 
	 * @return ResponseEntity<List<RegistroGlucosaVo>>	-	Registros recuperados de la busqueda
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa/fechaInicio/{fechaInicio}/fechaFin/{fechaFin}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperaListaRegistroGlucosaPorFiltros(@PathVariable("idPaciente") String idPaciente, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFin") String fechaFin) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<RegistroGlucosaVo> registroGlucosaVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			registroGlucosaVo = registroGlucosaFacade.recuperaListaRegistroGlucosaPorFiltros(idPaciente, fechaInicio, fechaFin);
			
			result = new ResponseEntity<List<RegistroGlucosaVo>>(registroGlucosaVo, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar un registro de glucosa por id del registro
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 05/02/2018
	 * @param idPaciente
	 * @param idRegistroGlucosa
	 * @return
	 */
	@RequestMapping(value = "/{idPaciente}/registroglucosa/{idRegistroGlucosa}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarRegistroGlucosaPorId(@PathVariable("idPaciente") String idPaciente, @PathVariable("idRegistroGlucosa") String idRegistroGlucosa) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		RegistroGlucosaVo registroGlucosaVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		String msjEx = null;
		
		try {
			registroGlucosaVo = registroGlucosaFacade.recuperaRegistroGlucosaPorId(idRegistroGlucosa);
			
			if(!registroGlucosaVo.getIdPaciente().equals(idPaciente)) {
				msjEx = "No se encontr� el registro para el paciente con id : " + idPaciente;
				throw new Exception(msjEx);
			}
			
			result = new ResponseEntity<RegistroGlucosaVo>(registroGlucosaVo, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar la informacion completa de una dieta por medio de su identificador
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 30/03/2018
	 * @param idPaciente						-	Identificador del paciente
	 * @param idDieta							-	Identificador de la dieta
	 * @return ResponseEntity<DietaDto>			-	Objeto con la informacion completa de una dieta
	 */
	@RequestMapping(value = "/{idPaciente}/dietas/{idDieta}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarDietaPorId(@PathVariable("idPaciente") String idPaciente, @PathVariable("idDieta") String idDieta) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		DietaDto dietaDto = null;
		RespuestaErrorVo respuestaErrorVo = null;
		RespuestaVo respuestaVo = null;
		
		try {
			dietaDto  = dietaFacade.recuperarDietaPorId(idDieta);
			result = new ResponseEntity<DietaDto>(dietaDto, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaVo = new RespuestaVo();
			respuestaVo.setRespuesta("ERROR");
			respuestaVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	/**
	 * Proposito : Recuperar una lista de dietas por medio del identificador del paciente, las dietas solo tendran informacion general
	 * 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 31/03/2018
	 * @param idPaciente						-	Identificador del paciente
	 * @return ResponseEntity<List<DietaDto>>	-	Objecto con la lista de dietas, error en caso contrario
	 */
	@RequestMapping(value = "/{idPaciente}/dietas", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> recuperarDietaPorIdPaciente(@PathVariable("idPaciente") String idPaciente) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		List<DietaDto> dietaDtoList = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {
			dietaDtoList  = dietaFacade.recuperaDietaPorIdPaciente(idPaciente);
			result = new ResponseEntity<List<DietaDto>>(dietaDtoList, HttpStatus.OK);
			
		}catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result=new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
	
	@RequestMapping(value = "/{idPaciente}/valoresnutrimentales", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> calcularValoresNutrimentalesDieta(@RequestBody PacienteVo pacienteVo) {
		log.debug("Inicio - Controller");
		
		ResponseEntity<?> result = null;
		ValoresNutrimentalesDietaVo valoresNutrimentalesDietaVo = null;
		RespuestaErrorVo respuestaErrorVo = null;
		
		try {

			
			
			valoresNutrimentalesDietaVo  = dietaFacade.calcularValoresNutrimentalesDieta(pacienteVo);

			result = new ResponseEntity <ValoresNutrimentalesDietaVo>(valoresNutrimentalesDietaVo,HttpStatus.OK);
			
		} catch (Exception ex) {
			respuestaErrorVo = new RespuestaErrorVo();
			respuestaErrorVo.setRespuesta("ERROR");
			respuestaErrorVo.setMensaje(ex.getMessage());
			result = new ResponseEntity<RespuestaErrorVo>(respuestaErrorVo, HttpStatus.OK);
		}
		log.debug("Fin - Controller");
		return result;
	}
}
