package mx.escom.tt.diabetes.model.dao;

import mx.escom.tt.diabetes.model.dto.UsuarioDto;

public interface UsuarioDao {
	
	/**
	 * 
	 * Proposito : Metodo para recuperar un Usuario por medio de su id.
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @param idUsuario				- Identificador del Usuario 
	 * @return UsuarioDto			- Objeto con la informacion del usuario. 
	 * @throws RuntimeException		- Si ocurre un error en tiempo de ejecucion.
	 */
	public UsuarioDto recuperarUsuarioPorId(Integer idUsuario) throws RuntimeException;
	
	
	/**
	 * Proposito : Registrar un nuevo Usuario
	 *
	 * @author Edgar, ESCOM
	 * @version 1.0.0, 07/10/2017
	 * @param usuarioDto			- Objeto con la informacion que se debe guardar.
	 * @throws RuntimeException		- Si ocurre un error en tiempo de ejecucion.
	 */
	public void registrarNuevoUsuario(UsuarioDto usuarioDto) throws RuntimeException;

}
