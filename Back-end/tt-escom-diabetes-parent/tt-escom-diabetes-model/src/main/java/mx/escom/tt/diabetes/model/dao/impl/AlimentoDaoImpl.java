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
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.AlimentoDao;
import mx.escom.tt.diabetes.model.dto.AlimentoDto;

@CommonsLog
@Repository("AlimentoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class AlimentoDaoImpl implements AlimentoDao{

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlimentoDto> recuperarAlimentosPorTipoAlimento(String tipoAlimento) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		List<AlimentoDto> alimentoDtoList = null;
		String msjEx = null;
		{//Validaciones
			if(tipoAlimento == null) {
				msjEx = "El tipo de alimento no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AlimentoDto.class);
			criteria.add(Restrictions.eq("tipoAlimento", tipoAlimento));
			alimentoDtoList =  criteria.list();
			
			/*queryStr = alimentoQlHelper.getQuery(AlimentoQlHelper.RECUPERA_ALIMENTO_POR_TIPO_DE_ALIMENTO);
			log.debug("queryStr : "+ queryStr);
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(AlimentoDto.class));
			log.debug("tipoAlimento : " + tipoAlimento);
			query.setParameter("tipoAlimento", tipoAlimento);
			
			alimentoDtoList = (List<AlimentoDto>) query.uniqueResult();*/
			
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar los alimentos.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
		return alimentoDtoList;
	}

}
