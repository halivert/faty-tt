package mx.escom.tt.diabetes.model.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.IndividuoDao;
import mx.escom.tt.diabetes.model.dto.IndividuoDto;

@CommonsLog
@Repository("IndividuoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IndividuoDaoImpl implements IndividuoDao {

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Override
	public void guardarIndividuoX(IndividuoDto individuoDto) throws RuntimeException {
		sessionFactory.getCurrentSession().save(individuoDto);
	}

	@Override
	public void borrarIndividuoX(Integer idIndividuo) throws RuntimeException {
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

	@Override
	public IndividuoDto recuperarPorEmailYKeyword(String email, String keyword) throws RuntimeException {
		log.debug("Inicio - Dao");
		IndividuoDto individuoDto = null;
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndividuoDto.class);
		
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("keyword", keyword));
		
		individuoDto = (IndividuoDto) criteria.uniqueResult();
		
		log.debug("Fin - Dao");
		return individuoDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndividuoDto> recuperarPorEmail(String email) throws RuntimeException {
		log.debug("Inicio - Dao");
		List<IndividuoDto> individuoDto = null;
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndividuoDto.class);
		
		criteria.add(Restrictions.eq("email", email));
		
		individuoDto = criteria.list();
		
		log.debug("Fin - Dao");
		return individuoDto;
	}
  
}
