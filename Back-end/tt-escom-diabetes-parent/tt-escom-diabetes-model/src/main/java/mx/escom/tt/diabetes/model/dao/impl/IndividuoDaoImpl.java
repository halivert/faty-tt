package mx.escom.tt.diabetes.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@Repository("IndividuoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IndividuoDaoImpl implements IndividuoDao {

	private SessionFactory sessionFactory;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		
	@Override
	public void guardarIndividuo(IndividuoDto individuoDto) throws Exception {
		this.sessionFactory.getCurrentSession().save(individuoDto);
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
