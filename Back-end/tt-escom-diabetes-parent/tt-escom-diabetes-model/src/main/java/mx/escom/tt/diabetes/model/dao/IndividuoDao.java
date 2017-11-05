package mx.escom.tt.diabetes.model.dao;


import java.util.List;

import mx.escom.tt.diabetes.model.dto.IndividuoDto;

public interface IndividuoDao  {
	
	/**
	 * 
	 * Proposito : Metodo para guardar un objeto de tipo IndividuoDto
	 *
	 * @author Hali, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param individuoDto
	 * @throws Exception
	 */
	public void guardarIndividuo(IndividuoDto individuoDto) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : 
	 * @author Hali, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idIndividuo
	 * @return
	 * @throws Exception
	 */
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : Metodo para buscar un individuo con base en su email y keyword 
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 22/10/2017
	 * @param emai
	 * @param keyword
	 * @return
	 * @throws RuntimeException
	 */
	public IndividuoDto recuperarPorEmailYKeyword(String email, String keyword) throws RuntimeException;
	
	/**
	 * 
	 * Proposito : 
	 * @author Hali, ESCOM
	 * @version 1,0,0. 16/10/2017
	 * @param idIndividuo
	 * @throws Exception
	 */
	public void borrarIndividuo(Integer idIndividuo) throws RuntimeException;
	
	public void actualizarIndividuo(IndividuoDto individuoDto) throws RuntimeException;
	
	/**
	 * Proposito : Metodo para recuperar un individuo por su email
	 * @author Edgar, ESCOM
	 * @version 1,0,0. 28/10/2017
	 * @param email
	 * @return
	 * @throws RuntimeException
	 */
	public List<IndividuoDto> recuperarPorEmail(String email) throws RuntimeException;
}
