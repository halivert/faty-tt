package mx.escom.tt.diabetes.model.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import lombok.Getter;
import lombok.Setter;
import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@Repository("IndividuoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IndividuoDaoImpl implements IndividuoDao {

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Override
	public void guardarIndividuo(IndividuoDto individuoDto) throws RuntimeException {
		sessionFactory.getCurrentSession().save(individuoDto);
	}

	@Override
	public void borrarIndividuo(Integer idIndividuo) throws RuntimeException {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(IndividuoDto.class, idIndividuo)
				);
	}
	
	@Override
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws RuntimeException {
		IndividuoDto individuoDto = (IndividuoDto) sessionFactory.getCurrentSession().get(IndividuoDto.class, idIndividuo); 
		return individuoDto;
	}

	@Override
	public void actualizarIndividuo(IndividuoDto individuoDto) throws RuntimeException {
		sessionFactory.getCurrentSession().update(individuoDto);
	}
  
}
