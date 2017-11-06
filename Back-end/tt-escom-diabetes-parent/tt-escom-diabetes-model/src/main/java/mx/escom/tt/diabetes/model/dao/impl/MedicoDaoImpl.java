package mx.escom.tt.diabetes.model.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.commons.vo.MedicoPacientesVo;
import mx.escom.tt.diabetes.model.dao.MedicoDao;
import mx.escom.tt.diabetes.model.dto.MedicoDto;
import mx.escom.tt.diabetes.model.hql.MedicoQlHelper;
import mx.escom.tt.diabetes.model.hql.QlHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Repository("MedicoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class MedicoDaoImpl implements MedicoDao{

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Autowired MedicoQlHelper medicoQlHelper;
	private @Getter @Setter QlHelper ql;

	@Override
	public void guardarMedico(MedicoDto medictoDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		if(medictoDto == null) {
			throw new RuntimeException();
		}
		
		try{
			sessionFactory.getCurrentSession().save(medictoDto);
			
		}catch(HibernateException  he){
			
			msjEx = "La cédula : " + medictoDto.getCedulaProfesional() + " ya se encuentra en la BD.";
			log.debug("msjEx : " + msjEx);
			throw new RuntimeException(msjEx);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar la información de médico." + ex.getMessage();
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
			//log.debug(ex.getMessage());
		}
		
		log.debug("Fin - Dao");
	}

	@Override
	public MedicoDto recuperarMedico(Integer idMedico) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		MedicoDto result = null;
		
		if(idMedico == null) {
			throw new RuntimeException();
		}
		
		result = (MedicoDto) sessionFactory.getCurrentSession().get(MedicoDto.class, idMedico);
		
		log.debug("Fin - Dao");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MedicoPacientesVo> recuperarPacientesDeMedico(Integer idMedico) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		List<MedicoPacientesVo> result = null;
		String queryStr = null;
		
		if(idMedico == null) {
			throw new RuntimeException();
		}

		queryStr = medicoQlHelper.getQuery(MedicoQlHelper.RECUPERA_PACIENTES_DE_MEDICO_POR_ID_MEDICO);
		log.debug("queryStr : "+ queryStr);
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(MedicoPacientesVo.class));
		log.debug("idMedico : " + idMedico);
		query.setParameter("idMedico", idMedico);
		
		result = query.list();
		
		log.debug("Fin - Dao");
		return result;
	}

	
}
