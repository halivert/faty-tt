package mx.escom.tt.diabetes.model.dao;

import org.hibernate.SessionFactory;

import mx.escom.tt.diabetes.model.dto.IndividuoDto;

public interface IndividuoDao {
	public void guardarIndividuo(IndividuoDto individuoDto) throws Exception;
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws Exception;
	public void borrarIndividuo(Integer idIndividuo) throws Exception;
	public void actualizarIndividuo(IndividuoDto individuoDto) throws Exception;
}
