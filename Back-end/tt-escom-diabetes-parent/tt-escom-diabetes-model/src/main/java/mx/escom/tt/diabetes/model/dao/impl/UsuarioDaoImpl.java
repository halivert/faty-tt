package mx.escom.tt.diabetes.model.dao.impl;


import org.springframework.stereotype.Repository;


import mx.escom.tt.diabetes.model.dao.UsuarioDao;
import mx.escom.tt.diabetes.model.dto.UsuarioDto;
import mx.escom.tt.diabetes.model.hql.UsuarioHqlHelper;
import mx.escom.tt.diabetes.model.hql.QlHelper;

import org.springframework.transaction.annotation.Transactional;

import lombok.extern.apachecommons.CommonsLog;
import lombok.Getter;
import lombok.Setter;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import mx.escom.tt.diabetes.commons.utils.Constants;


@CommonsLog
@Repository("UsuarioDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UsuarioDaoImpl extends HibernateDaoSupport implements UsuarioDao{
	
	@Autowired UsuarioHqlHelper usuarioHqlHelper;
	
	private @Getter @Setter QlHelper ql;

	@Override
	public UsuarioDto recuperarUsuarioPorId(Integer idUsuario) throws RuntimeException{
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		UsuarioDto usuarioDto = null;
		
		{//Validaciones
			if(idUsuario == null){
				msjEx = "El identificador del usuario no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			usuarioDto = getHibernateTemplate().execute(new HibernateCallback<UsuarioDto>() {
				@Override
				public UsuarioDto doInHibernate(Session session) throws HibernateException {
					log.debug("Inicio - Hibernate");
					UsuarioDto usuarioDtoAux = null;
					String queryStr = null;
					queryStr = usuarioHqlHelper.getQuery(UsuarioHqlHelper.RECUPERA_USUARIO_POR_ID);
					
					log.debug("queryStr: "+queryStr);
					Query query = session.createQuery(queryStr);
					//Query query = session.createSQLQuery(queryStr);
					log.debug("idUsuario : " + idUsuario);
					query.setParameter("idUsuario", idUsuario);
					
					usuarioDtoAux = (UsuarioDto) query.uniqueResult();
					log.debug("Fin - Hibernate");
					return usuarioDtoAux;
				}
				
			});
			
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + Constants.SALTO_LINEA + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
			
		log.debug("Fin - Dao");		
		return usuarioDto;
	}

	@Override
	public void registrarNuevoUsuario(UsuarioDto usuarioDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;

		
		{//Validaciones
			if(usuarioDto == null) {
				msjEx = "El identificador del usuario no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			 getHibernateTemplate().execute(new HibernateCallback<Boolean>() {

				@Override
				public Boolean doInHibernate(Session session) throws HibernateException {
					log.debug("Inicio - Hibernate");
			
					getHibernateTemplate().save(usuarioDto);
					getHibernateTemplate().flush();
					
					log.debug("Fin - Hibertane");
					return null;
				}
				
			});
			
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el usuario con id : " + usuarioDto.getIdUsuario() + Constants.SALTO_LINEA + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
	}
	
}
