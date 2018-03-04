package mx.escom.tt.diabetes.model.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
import mx.escom.tt.diabetes.commons.vo.UltimoHistorialClinicoVo;
import mx.escom.tt.diabetes.model.dao.HistorialClinicoDao;
import mx.escom.tt.diabetes.model.dto.HistorialClinicoDto;
import mx.escom.tt.diabetes.model.hql.HistorialClinicoQlHelper;
import mx.escom.tt.diabetes.model.hql.QlHelper;

@CommonsLog
@Repository("HistorialClinicoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class HistorialClinicoDaoImpl implements HistorialClinicoDao{

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Autowired HistorialClinicoQlHelper historialClinicoQlHelper;
	private @Getter @Setter QlHelper ql;
	
	@Override
	public void guardarHistorialClinico(HistorialClinicoDto historialClinicoDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		
		{//Validaciones
			if(historialClinicoDto == null) {
				msjEx = "La información del historial clínico no puede ser nula.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().save(historialClinicoDto);
		
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el historial clínico.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
	}

	@Override
	public HistorialClinicoDto recuperarHistorialClinicoPorId(Integer idHistorialClinico) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		HistorialClinicoDto historialClinicoDto = null;
		String msjEx = null;
		
		{//Validaciones
			if(idHistorialClinico == null){
				msjEx = "El identificador del historial no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			historialClinicoDto = (HistorialClinicoDto) sessionFactory.getCurrentSession().get(HistorialClinicoDto.class, idHistorialClinico);
	
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el historial con id : " + idHistorialClinico + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
		return historialClinicoDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistorialClinicoDto> recuperarListaHistorialClinicoPorIdPaciente(Integer idPaciente) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		List<HistorialClinicoDto>  listHistorialClinicoDto = null;
		String msjEx = null;
		
		try {			
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HistorialClinicoDto.class);
			
			criteria.add(Restrictions.eq("idPaciente", idPaciente));
			
			listHistorialClinicoDto =  criteria.list();
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar el registro de historial clínico para el paciente con id : " + idPaciente + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx);
		}

		log.debug("Fin - Dao");
		return listHistorialClinicoDto;
	}

	@Override
	public void actualizarHistorialClinico(HistorialClinicoDto historialClinicoDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		
		{//Validaciones
			if(historialClinicoDto == null) {
				msjEx = "La información del historial no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().update(historialClinicoDto);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el registro.";
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");		
	}

	@Override
	public UltimoHistorialClinicoVo recuperarUltimoHistorialClinicoPorIdPaciente(Integer idPaciente)
			throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		String queryStr = null;
		UltimoHistorialClinicoVo ultimoHistorialClinicoVo = null;
		
		{//Validaciones
			if(idPaciente == null) {
				msjEx = "El identificador del paciente no debe ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			queryStr = historialClinicoQlHelper.getQuery(HistorialClinicoQlHelper.RECUPERA_ULTIMO_HISTORIAL_CLINICO_POR_ID_PACIENTE);
			log.debug("queryStr : "+ queryStr);
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr).setResultTransformer(Transformers.aliasToBean(UltimoHistorialClinicoVo.class));
			log.debug("idPaciente : " + idPaciente);
			query.setParameter("idPaciente", idPaciente);
			
			ultimoHistorialClinicoVo = (UltimoHistorialClinicoVo) query.uniqueResult();
		}catch(Exception ex) {
			msjEx = Constants.MSJ_EXCEPTION + "al recuperar el registro.";
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		
		log.debug("Fin - Dao");
		return ultimoHistorialClinicoVo;
	}

}
