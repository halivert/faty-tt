package mx.escom.tt.diabetes.model.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.RegistroGlucosaCommonVo;
import mx.escom.tt.diabetes.model.dao.RegistroGlucosaDao;
import mx.escom.tt.diabetes.model.dto.RegistroGlucosaDto;
import mx.escom.tt.diabetes.model.hql.QlHelper;
import mx.escom.tt.diabetes.model.hql.RegistroGlucosaQlHelper;

@CommonsLog
@Repository("RegistroGlucosaDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RegistroGlucosaDaoImpl implements RegistroGlucosaDao{

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Autowired RegistroGlucosaQlHelper registroGlucosaQlHelper;
	private @Getter @Setter QlHelper ql;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroGlucosaCommonVo> recuperaNRegistroGlucosa(Integer idPaciente, Integer limiteRegistro)
			throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		String queryStr = null;
		List<RegistroGlucosaCommonVo> resultList = null;
		
		{//Validaciones
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no debe ser nulo.";
				throw new RuntimeException(msjEx);
			}
			if(limiteRegistro == null) {
				msjEx = "El número máximo de registros no puede ser nulo o vacío.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			queryStr = ql.getQuery(RegistroGlucosaQlHelper.RECUPERA_N_REGISTROS_GLUCOSA);
			log.debug("queryStr : "+ queryStr);
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(RegistroGlucosaCommonVo.class));
			//log.debug("idPaciente : " + idPaciente);
			query.setParameter("idPaciente", idPaciente);
			query.setParameter("limiteRegistro", limiteRegistro);
			
			resultList = query.list();
		}catch(Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar el registro.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		
		log.debug("Fin - Dao");
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroGlucosaCommonVo> recuperaListaRegistroGlucosaPorFiltros(Integer idPaciente, Timestamp fechaInicio,
			Timestamp fechaFin) throws RuntimeException {	
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		String queryStr = null;
		List<RegistroGlucosaCommonVo> resultList = null;
		
		{//Validaciones
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no debe ser nulo.";
				throw new RuntimeException(msjEx);
			}
			if(fechaInicio == null) {
				msjEx = "La fecha de inicio no puede ser nula o vacía.";
				throw new RuntimeException(msjEx);
			}
			if(fechaFin == null) {
				msjEx = "La fecha de fin no puede ser nula o vacía.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			queryStr = ql.getQuery(RegistroGlucosaQlHelper.RECUPERA_REGISTROS_POR_FILTROS);
			log.debug("queryStr : "+ queryStr);
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(RegistroGlucosaCommonVo.class));
			//log.debug("idPaciente : " + idPaciente);
			query.setParameter("idPaciente", idPaciente);
			query.setParameter("fechaInicio", fechaInicio);
			query.setParameter("fechaFin", fechaFin);
			
			resultList = query.list();
		}catch(Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar el registro.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
		return resultList;
	}

	

}
