package mx.escom.tt.diabetes.web.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.business.service.MedicoAppService;
import mx.escom.tt.diabetes.business.service.PacienteAppService;
import mx.escom.tt.diabetes.business.service.UsuarioAppService;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.utils.NumberHelper;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.dto.PacienteDto;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
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
				msjEx="El identificador del usuario no puede ser nulo o vac�o.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {		
			idUsuario = new Integer(idUsuarioStr);
			usuarioDto = usuarioAppService.recuperarUsuarioPorId(idUsuario);
			
			medicoDto = medicoAppService.recuperarMedico(idUsuario);
			pacienteDto = pacienteAppService.recuperarPaciente(idUsuario);
			
			{//Se transforma el DTO al VO			
				usuarioVo = new UsuarioVo();
				
				usuarioVo.setNombre(usuarioDto.getNombre().trim());
				usuarioVo.setApellidoPaterno(usuarioDto.getApellidoPaterno());
				usuarioVo.setApellidoMaterno(usuarioDto.getApellidoMaterno());
				usuarioVo.setEmail(usuarioDto.getEmail().trim());
				usuarioVo.setFechaNacimiento(formatoFechaNacimiento.format(usuarioDto.getFechaNacimiento()));
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
	 * @param UsuarioLoginVo				-	VO con el email y la contrase�a del usuario
	 * @return
	 * @throws RuntimeException
	 */
	public RespuestaVo login(UsuarioLoginVo usuarioVo) throws RuntimeException{
		log.debug("Incio - Facade");
		RespuestaVo respuestaVo = null;
		UsuarioDto usuarioDto = null;
		
		try{
			usuarioDto = usuarioAppService.recuperarPorEmailYKeyword(usuarioVo.getEmail(), usuarioVo.getKeyword());
		
			if(usuarioDto == null) {
				throw new RuntimeException("Correo electr�nico o contrase�a incorrectos.");
			}
			
			
			respuestaVo = new RespuestaVo();
			
			respuestaVo.setIdUsuario(usuarioDto.getIdUsuario().toString());
			respuestaVo.setRespuesta("OK");
			respuestaVo.setMensaje("Inicio de sesi�n correcto");
		
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		log.debug("Fin - Facade");
		return respuestaVo;
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
		String msjError=null;
		UsuarioDto usuarioDto = null;
		MedicoDto medicoDto = null;
		//Date fechaNacimiento = null;
		String msjEx = null;
		PacienteDto pacienteDto = null;
		Integer idUsuario = null;
		
		{//VALIDACIONES
			if(usuarioVo==null) {
				msjError="La informaci�n del usuario no puede ser nula.";
				throw new RuntimeException(msjError);
			}
		}
		try{
			{//Se arma el DTO de usuario con la informacion del VO
				usuarioDto = new UsuarioDto();
				
				usuarioDto.setNombre(usuarioVo.getNombre() != null ? usuarioVo.getNombre().trim() : Constants.CADENA_VACIA);
				usuarioDto.setApellidoPaterno(usuarioVo.getApellidoPaterno() != null ? usuarioVo.getApellidoPaterno().trim() : Constants.CADENA_VACIA);
				usuarioDto.setApellidoMaterno(usuarioVo.getApellidoMaterno() != null ? usuarioVo.getApellidoMaterno().trim() : Constants.CADENA_VACIA);
				usuarioDto.setEmail(usuarioVo.getEmail() != null ? usuarioVo.getEmail().trim() : Constants.CADENA_VACIA);
				usuarioDto.setKeyword(usuarioVo.getKeyword() != null ? usuarioVo.getKeyword().trim() : Constants.CADENA_VACIA);
				
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
				{//SE ARMA EL DTO PARA GUARDAR UN PACIENTE
					pacienteDto.setIdMedico(usuarioVo.getCodigoMedico() != null ? new Integer(usuarioVo.getCodigoMedico()) : null);
				}
			}
			
			idUsuario = usuarioAppService.guardarUsuario(usuarioDto, medicoDto, pacienteDto);
			
			result = new RespuestaVo();
			
			result.setIdUsuario(idUsuario.toString());
			result.setRespuesta("OK");
			result.setMensaje("La informaci�n se guard� con �xito.");
			
		}catch(ParseException pEx) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la fecha de nacimiento.";
			throw new RuntimeException(msjEx);
		}
		catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}catch (Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "guardar la informaci�n del usuario.";
			throw new RuntimeException(msjEx);
		}
	
		
		log.debug("Fin - Facade");
		return result;
	}
	

}
