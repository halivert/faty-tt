package mx.escom.tt.diabetes.model.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.DietaCommonsVo;
import mx.escom.tt.diabetes.model.dao.DietaDao;
import mx.escom.tt.diabetes.model.dto.DietaDto;
import mx.escom.tt.diabetes.model.hql.AlimentoQlHelper;

@CommonsLog
@Repository("DietaDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DietaDaoImpl implements DietaDao{
	
	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	private @Getter @Setter
	AlimentoQlHelper ql;
	
	@Override
	public void guardarDieta(DietaDto dietaDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		
		{//Validaciones
			if(dietaDto == null) {
				msjEx = "La información del historial clínico no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().save(dietaDto);
		
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "guardar la dieta.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		log.debug("Fin - Dao");
	}

	@Override
	public DietaDto recuperarDietaPorId(Integer idDieta) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		DietaDto dietaDto = null;
		
		{//Validaciones
			if(idDieta == null) {
				msjEx = "El identificador de la dieta no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			dietaDto = (DietaDto) sessionFactory.getCurrentSession().get(DietaDto.class, idDieta);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar la dieta con id : " + idDieta + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
		return dietaDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DietaDto> recuperarDietaPorIdPaciente(Integer idPaciente) throws RuntimeException {
		log.debug("Inicio - Dao");
		List<DietaDto> dietaDtoList = null;
		String queryStr = null;
		
		if(idPaciente == null) {
			throw new RuntimeException("El identificador del paciente no puede ser nulo.");
		}

		queryStr = ql.getQuery(AlimentoQlHelper.RECUPERA_DIETAS_POR_ID_PACIENTE);
		log.debug("queryStr : "+ queryStr);
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(DietaCommonsVo.class));
		log.debug("idPaciente : " + idPaciente);
		query.setParameter("idPaciente", idPaciente);
		
		dietaDtoList = query.list();
		
		log.debug("Fin - Dao");
		return dietaDtoList;
	}

}
