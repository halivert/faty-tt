package mx.escom.tt.diabetes.web.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.MedicoAppService;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.business.service.ReportesPDF;
import mx.escom.tt.diabetes.business.service.TokenMedicoAppService;
import mx.escom.tt.diabetes.business.service.UsuarioAppService;
import mx.escom.tt.diabetes.business.vo.DietaReporteVo;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.utils.NumberHelper;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import mx.escom.tt.diabetes.web.vo.DietaReportePDFVo;
import mx.escom.tt.diabetes.web.vo.RespuestaVo;
import mx.escom.tt.diabetes.web.vo.UsuarioLoginVo;
import mx.escom.tt.diabetes.web.vo.UsuarioVo;

@Service
@CommonsLog
public class UsuarioFacade extends NumberHelper{
	
	@Qualifier("FormatoFechaNacimiento")
	@Autowired private SimpleDateFormat formatoFechaNacimiento;
	
	@Autowired UsuarioAppService usuarioAppService;
	@Autowired MedicoAppService medicoAppService;
	@Autowired PacienteAppService pacienteAppService;
	@Autowired TokenMedicoAppService tokenMedicoAppService;
	@Autowired ReportesPDF reportesPDF;
	
	
	
	/**
	 * Proposito :  Recuperar la informacion de un usuario por su Id
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param idUsuarioStr			-	Identificador dle usuario a buscar
	 * @return UsuarioDto			-	Informacion del usuario
	 */
	public UsuarioVo recuperarUsuarioPorId(String idUsuarioStr) {
		log.debug("Inicio - Facade");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		Integer idUsuario = null;
		UsuarioVo usuarioVo = null;
		PacienteDto pacienteDto = null;
		MedicoDto medicoDto = null;
		
		{//Validaciones 
			if(idUsuarioStr == null || idUsuarioStr.trim().equals("")) {
				msjEx="El identificador del usuario no puede ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {		
			idUsuario = new Integer(idUsuarioStr);
			usuarioDto = usuarioAppService.recuperarUsuarioPorId(idUsuario);
			
			medicoDto = medicoAppService.recuperarMedico(idUsuario);
			pacienteDto = pacienteAppService.recuperarPacientePorIdPacienteAppService(idUsuario);
			
			{//Se transforma el DTO al VO			
				usuarioVo = new UsuarioVo();
				
				usuarioVo.setNombre(usuarioDto.getNombre().trim());
				usuarioVo.setApellidoPaterno(usuarioDto.getApellidoPaterno());
				usuarioVo.setApellidoMaterno(usuarioDto.getApellidoMaterno());
				usuarioVo.setEmail(usuarioDto.getEmail().trim());
				usuarioVo.setFechaNacimiento(formatoFechaNacimiento.format(usuarioDto.getFechaNacimiento()));
				
				String[] input = String.valueOf(usuarioDto.getFechaNacimiento()).split(" ");
				LocalDate dob = LocalDate.parse(input[0]);
				Integer edad = getAge(dob);
				usuarioVo.setEdad(String.valueOf(edad));
				usuarioVo.setSexo(usuarioDto.getSexo());
				
				if(pacienteDto != null) {
					usuarioVo.setCodigoMedico(pacienteDto.getIdMedico().toString());
					usuarioVo.setIdRol(Constants.ID_ROL_PACIENTE);
				}
				else if(medicoDto != null) {
					usuarioVo.setCedulaProfesional(medicoDto.getCedulaProfesional().trim());
					usuarioVo.setIdRol(Constants.ID_ROL_MEDICO);
				}
			}
			
		}catch(RuntimeException rtExc) { 
			throw new RuntimeException(rtExc.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Facade");
		return usuarioVo; 
	}
	
	/**
	 * Proposito : Verificar la existencia de un usuario en la BD para poder iniciar sesion
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param UsuarioLoginVo				-	VO con el email y la contraseña del usuario
	 * @return
	 * @throws RuntimeException
	 */
	public RespuestaVo login(UsuarioLoginVo usuarioVo) throws RuntimeException{
		log.debug("Incio - Facade");
		RespuestaVo respuestaVo = null;
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		{//Validaciones 
			if(StringUtils.isEmpty(usuarioVo.getEmail())) {
				msjEx = "Verficar correo electrónico.";
				throw new RuntimeException(msjEx);
			}
			if(StringUtils.isEmpty(usuarioVo.getKeyword())) {
				msjEx = "Contraseña incorrecta.";
				throw new RuntimeException(msjEx);
			}
		}
		try{
			String encoedKeyword = encodeKeyword(usuarioVo.getKeyword());
			
			usuarioDto = usuarioAppService.recuperarPorEmailYKeyword(usuarioVo.getEmail(), encoedKeyword);
		
			if(usuarioDto == null) {
				throw new RuntimeException("Correo electrónico o contraseña incorrectos.");
			}
			
			
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIdUsuario(usuarioDto.getIdUsuario().toString());
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("Inicio de sesión correcto");
		
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	/**
	 * Proposito : Retorna una respuesta al concluir la verificacion de un token
	 * 
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 13/05/2018
	 * @param token						-	Token que se desea verificar
	 * @return
	 * @throws RuntimeException
	 */
	public RespuestaVo validaTokenReestablecerPassword(String token) throws RuntimeException{
		log.debug("Incio - Facade");
		RespuestaVo respuestaVo = null;
		String[] response = null;
		String email = null;
		String idUsuario = null;
		try{
			email = usuarioAppService.validaTokenReestablecerPassword(token);
		
			if(StringUtils.isEmpty(email)) {
				throw new RuntimeException("Ocurrió un error al procesar la ultima petición.");
			}
			response = email.split("-");
			
			email = response[0];
			idUsuario = response[1];
			log.debug("idUsuario : " + idUsuario);
			respuestaVo = new RespuestaVo();
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje(email);
			respuestaVo.setIdUsuario(idUsuario);
		
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	public RespuestaVo reestablecerPassword(UsuarioVo usuarioVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		RespuestaVo respuestaVo = null; 
		String msjEx = null;
		String idUsuarioStr = usuarioVo.getIdUsuario();
		String passwordStr = usuarioVo.getKeyword();
		try {
			if(StringUtils.isEmpty(idUsuarioStr)) {
				throw new RuntimeException("El identificador del usuario no puede ser nulo o vacío.");
			}
			if(!StringUtils.isNumeric(idUsuarioStr)) {
				throw new RuntimeException("El identificador del usuario no puede tener letras.");
			}
			if(StringUtils.isEmpty(passwordStr)) {
				throw new RuntimeException("La contraseña no puede ser nula o vacía.");
			}
			String encriptedPassword = encodeKeyword(passwordStr);
			usuarioAppService.reestablecerPasswordAppService(Integer.valueOf(idUsuarioStr), encriptedPassword);
			
			respuestaVo = new RespuestaVo();
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("La contraseña se actualizó correctamente");
		}catch(RuntimeException rtExc) { 
			throw new RuntimeException(rtExc.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "recuperar actualizar la contraseña";
			throw new RuntimeException(msjEx,ex);
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
	}
	
	
	public byte[] generarDietaPDF(DietaReportePDFVo dietaReportePDFVo) throws RuntimeException{
		log.debug("Inicio - Facade");
		byte[] response = null;
		String msjEx = null;
		Map<String, Object> parameters;
		List<DietaReporteVo> collectionDataSource = new ArrayList<DietaReporteVo>();
		
		try {
			parameters = new HashMap<>();
			parameters.put("nombrePaciente", dietaReportePDFVo.getNombrePaciente());
			parameters.put("edadPaciente", dietaReportePDFVo.getEdadPaciente());
			parameters.put("estaturaPaciente", dietaReportePDFVo.getEstaturaPaciente());
			parameters.put("pesoPaciente", dietaReportePDFVo.getPesoPaciente());
			parameters.put("get", dietaReportePDFVo.getGastoET());
			
			for(int i = 0; i< 5; i++) {
				DietaReporteVo dietaReporteVo = new DietaReporteVo();
				dietaReporteVo.setAlimentosDesayuno("alimentos : " + i);
				log.debug(i);
				collectionDataSource.add(dietaReporteVo);
			}
			
			response = reportesPDF.crearReporte(Constants.PLANTILLA_JASPER_DIETA,null,parameters, collectionDataSource);
			
		}catch(RuntimeException rtExc) {
			throw new RuntimeException(rtExc.getMessage());
		}catch(Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "generar la dieta en formato PDF";
			throw new RuntimeException(msjEx,ex);
		}

		log.debug("Fin - Facade");
		return response;
	}
	
	/**
	 * 
	 * Proposito : Guardar un usuario y su informacion con base en su rol
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 02/11/2017
	 * @param usuarioVo
	 * @return
	 */
	public RespuestaVo guardarUsuario(UsuarioVo usuarioVo) {
		log.debug("Inicio - Facade");
		
		RespuestaVo result=null;
		TokenMedicoDto tokenMedicoDto = null;
		String msjError=null;
		UsuarioDto usuarioDto = null;
		MedicoDto medicoDto = null;
		//Date fechaNacimiento = null;
		String msjEx = null;
		PacienteDto pacienteDto = null;
		Integer idUsuario = null;
		
		{//VALIDACIONES
			if(usuarioVo==null) {
				msjError="La información del usuario no puede ser nula.";
				throw new RuntimeException(msjError);
			}
			
			if(StringUtils.isEmpty(usuarioVo.getNombre())) {
				msjError="El nombre del paciente" + Constants.ERROR_FORMATO_NULO_VACIO;
				throw new RuntimeException(msjError);
			}
			
			if(StringUtils.isEmpty(usuarioVo.getApellidoPaterno()) || StringUtils.isEmpty(usuarioVo.getApellidoMaterno())) {
				msjError="El apellido del paciente" + Constants.ERROR_FORMATO_NULO_VACIO;
				throw new RuntimeException(msjError);
			}
			
			if(!StringUtils.isEmpty(usuarioVo.getCedulaProfesional())) {
				if(!StringUtils.isNumeric(usuarioVo.getCedulaProfesional())){
					msjError="La cédula profesional" + Constants.ERROR_FORMATO_NUMERICO;
					throw new RuntimeException(msjError);
				}
			}
			
			
		
		}
		try{
			{//Se arma el DTO de usuario con la informacion del VO
				usuarioDto = new UsuarioDto();
				
				usuarioDto.setNombre(usuarioVo.getNombre() != null ? usuarioVo.getNombre().trim() : Constants.CADENA_VACIA);
				usuarioDto.setApellidoPaterno(usuarioVo.getApellidoPaterno() != null ? usuarioVo.getApellidoPaterno().trim() : Constants.CADENA_VACIA);
				usuarioDto.setApellidoMaterno(usuarioVo.getApellidoMaterno() != null ? usuarioVo.getApellidoMaterno().trim() : Constants.CADENA_VACIA);
				usuarioDto.setEmail(usuarioVo.getEmail() != null ? usuarioVo.getEmail().trim() : Constants.CADENA_VACIA);
				
				String password = encodeKeyword(usuarioVo.getKeyword());
				
				if(StringUtils.isEmpty(password)) {
					msjError="Ocurrió un error al guardar la información, intente nuevamente";
					throw new RuntimeException(msjError);
				}
				usuarioDto.setKeyword(password);
				if(isNumeric(usuarioVo.getSexo())) {
					usuarioDto.setSexo(usuarioVo.getSexo().equals("1") ? "Masculino" : "Femenino");
				}else {
					usuarioDto.setSexo(usuarioVo.getSexo() != null ? usuarioVo.getSexo() : Constants.CADENA_VACIA);
				}
				
				if(usuarioVo.getFechaNacimiento() != null && !usuarioVo.getFechaNacimiento().trim().isEmpty()) {
					
					usuarioDto.setFechaNacimiento(formatoFechaNacimiento.parse(usuarioVo.getFechaNacimiento()));
				}
			}
			
			log.debug("usuarioVo.getIdRol() : " + usuarioVo.getIdRol());
			//Si el nuevo usuario es MEDICO
			if(usuarioVo.getIdRol().equals("1")) {
				log.debug("es medico");
				medicoDto = new MedicoDto();
				{//SE ARMA EL DTO PARA GUARDAR UN MEDICO
					medicoDto.setCedulaProfesional(usuarioVo.getCedulaProfesional() != null ? usuarioVo.getCedulaProfesional().trim() : Constants.CADENA_VACIA);
					
				}
			}
			
			//Si el nuevo usuario es PACIENTE
			else if(usuarioVo.getIdRol().equals("0")){
				log.debug("espaciente");
				pacienteDto = new PacienteDto();
				
				//Se recupera el idMedico con  su token
				tokenMedicoDto = tokenMedicoAppService.recuperarTokenAppService(usuarioVo.getCodigoMedico());
				
				{//SE ARMA EL DTO PARA GUARDAR UN PACIENTE
					pacienteDto.setIdMedico(tokenMedicoDto.getIdMedico() != null ? new Integer(tokenMedicoDto.getIdMedico()) : null);
				}
				
			}
			
			idUsuario = usuarioAppService.guardarUsuario(usuarioDto, medicoDto, pacienteDto);
			
			result = new RespuestaVo();
			
			result.setIdUsuario(idUsuario.toString());
			result.setRespuesta("OK");
			result.setMensaje("La información se guardó con éxito.");
			
			//Si el usuario es paciente se borra el token que uso
			if(usuarioVo.getIdRol().equals("0")){
				log.debug("borrar TOKEN");
				
				tokenMedicoAppService.borrarTokenAppService(usuarioVo.getCodigoMedico());
			}
			
		}catch(ParseException pEx) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la fecha de nacimiento.";
			throw new RuntimeException(msjEx);
		}
		catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la información del usuario.";
			throw new RuntimeException(msjEx);
		}
	
		
		log.debug("Fin - Facade");
		return result;
	}
	
	
	// Returns age given the date of birth
    public int getAge(LocalDate dob) {
        LocalDate curDate = LocalDate.now();
        return Period.between(dob, curDate).getYears();
    }
	
    
    public String encodeKeyword(String password) {
    		log.debug("Inicia - Facade");
    	    String md5Hex = DigestUtils.sha256Hex(password.getBytes()).toUpperCase();
    	    log.debug("md5Hex : " + md5Hex);
    		log.debug("Fin - Facade");
    		return md5Hex;
    }

}
