package mx.escom.tt.diabetes.model.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.RegistroGlucosaDao;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;

@CommonsLog
@Repository("RegistroGlucosaDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RegistroGlucosaDaoImpl implements RegistroGlucosaDao{

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Override
	public void guardaRegistroGlucosa(RegistroGlucosaDto regGlucosaDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		
		{//Validaciones
			if(regGlucosaDto == null) {
				msjEx = "La información de la glucosa no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().save(regGlucosaDto);
		
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el registro.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
	}

	@Override
	public void actualizaRegistroGlucosa(RegistroGlucosaDto regGlucosaDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		
		{//Validaciones
			if(regGlucosaDto == null) {
				msjEx = "La información de la glucosa no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().update(regGlucosaDto);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el registro.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");		
	}

	@Override
	public RegistroGlucosaDto recuperaRegistroGlucosaPorId(Integer idRegistroGlucosa) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		RegistroGlucosaDto result = null;
	
		if(idRegistroGlucosa == null) {
			throw new RuntimeException();
		}
		result = (RegistroGlucosaDto) sessionFactory.getCurrentSession().get(RegistroGlucosaDto.class, idRegistroGlucosa);
		
		log.debug("Fin - Dao");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroGlucosaDto> recuperaListaRegistroGlucosaPorIdPaciente(Integer idPaciente) throws RuntimeException {
		List<RegistroGlucosaDto> resultList = null;
		
		if(idPaciente == null) {
			throw new RuntimeException();
		}
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegistroGlucosaDto.class);
		
		criteria.add(Restrictions.eq("idPaciente", idPaciente));
		criteria.addOrder(Order.asc("idRegistroGlucosa"));
		
		resultList = criteria.list();
		
		log.debug("Fin - Dao");
		return resultList;
	}

	@Override
	public List<RegistroGlucosaDto> recuperaListaRegistroGlucosaPorFiltros() throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
