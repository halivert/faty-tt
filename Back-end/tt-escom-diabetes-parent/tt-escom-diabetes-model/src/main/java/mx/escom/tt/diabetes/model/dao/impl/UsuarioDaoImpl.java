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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import mx.escom.tt.diabetes.commons.utils.Constants;


@CommonsLog
@Repository("UsuarioDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UsuarioDaoImpl implements UsuarioDao { 
	
	@Autowired UsuarioHqlHelper usuarioHqlHelper;
	private @Getter @Setter QlHelper ql;

	private @Getter @Setter
	SessionFactory  sessionFactory;
	
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
			usuarioDto = (UsuarioDto) sessionFactory.getCurrentSession().get(UsuarioDto.class, idUsuario);
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con id : " + idUsuario + "\n" + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
			
		log.debug("Fin - Dao");		
		return usuarioDto;
	}

	@Override
	public void guardarUsuario(UsuarioDto usuarioDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;

		{//Validaciones
			if(usuarioDto == null) {
				msjEx = "El identificador del usuario no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {			 
			sessionFactory.getCurrentSession().save(usuarioDto);
		}catch(HibernateException he){
			msjEx = "El correo electrónico : " + usuarioDto.getEmail() + " ya ha sido usado.";
			throw new RuntimeException(msjEx);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el usuario." + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
	}

	@Override
	public UsuarioDto recuperarPorEmailYKeyword(String email, String keyword) throws RuntimeException {
		log.debug("Inicio - Dao");
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		try {			
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsuarioDto.class);
			
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("keyword", keyword));
			
			usuarioDto = (UsuarioDto) criteria.uniqueResult();
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con email : " + email;
			throw new RuntimeException(msjEx);
		}
		log.debug("Fin - Dao");
		return usuarioDto;
	}

	@Override
	public UsuarioDto recuperarPorEmail(String email) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		UsuarioDto usuarioDto = null;
		String msjEx = null;
		try {			
			log.info("email : " + email);
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsuarioDto.class);
			
			criteria.add(Restrictions.eq("email", email));
			
			usuarioDto = (UsuarioDto) criteria.uniqueResult();
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "recuperar el usuario con email : " + email;
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Dao");
		return usuarioDto;
	}

	@Override
	public void reestablecerPassword(UsuarioDto usuarioDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		try {			
			
			sessionFactory.getCurrentSession().update(usuarioDto);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "actualizar la contraseña.";
			throw new RuntimeException(msjEx);
		}
		
		log.debug("Fin - Dao");
		
	}
	
}
