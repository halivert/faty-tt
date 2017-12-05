package mx.escom.tt.diabetes.model.dao;


import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

public interface TokenMedicoDao {
	
	/**
	 * Proposito : Guardar un token de un medico
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param tokenMedicoDto
	 * @throws RuntimeException
	 */
	public void guardarToken(TokenMedicoDto tokenMedicoDto) throws RuntimeException;
	
	/**
	 * Proposito : Recuperar el token
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param idMedico
	 * @throws RuntimeException
	 */
	public TokenMedicoDto recuperarToken(String token) throws RuntimeException;
	
	/**
	 * Proposito : Borrar token de la BD 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 07/11/2017
	 * @param token					-	Token que sera borrado
	 * @throws RuntimeException
	 */
	public void borrarToken(String token) throws RuntimeException;

}
