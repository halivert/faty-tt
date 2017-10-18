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
	public void guardarIndividuo(IndividuoDto individuoDto) throws Exception {
		sessionFactory.getCurrentSession().save(individuoDto);
	}

	@Override
	public void borrarIndividuo(Integer idIndividuo) throws Exception {
		this.sessionFactory.getCurrentSession().delete(
				this.sessionFactory.getCurrentSession().load(IndividuoDto.class, idIndividuo)
				);
	}
	
	@Override
	public IndividuoDto obtenerIndividuo(Integer idIndividuo) throws Exception {
		return (IndividuoDto) this.sessionFactory.getCurrentSession().get(IndividuoDto.class, idIndividuo);
	}
  
}
