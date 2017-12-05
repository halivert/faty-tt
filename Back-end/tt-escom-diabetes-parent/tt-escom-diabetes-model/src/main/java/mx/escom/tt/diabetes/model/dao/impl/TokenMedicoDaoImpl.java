package mx.escom.tt.diabetes.model.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.commons.utils.Constants;
import mx.escom.tt.diabetes.model.dao.TokenMedicoDao;
import mx.escom.tt.diabetes.model.dto.TokenMedicoDto;

@CommonsLog
@Repository("TokenMedicoDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TokenMedicoDaoImpl implements TokenMedicoDao{
	
	private @Getter @Setter
	SessionFactory  sessionFactory;
	
	@Override
	public void guardarToken(TokenMedicoDto tokenMedicoDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		if(tokenMedicoDto == null) {
			throw new RuntimeException();
		}
		
		try{
			sessionFactory.getCurrentSession().save(tokenMedicoDto);
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al guardar el código del médico." + ex.getMessage();
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
		
	}

	@Override
	public TokenMedicoDto recuperarToken(String token) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		TokenMedicoDto tokenMedicoDto = null;
		
		tokenMedicoDto = (TokenMedicoDto) sessionFactory.getCurrentSession().get(TokenMedicoDto.class, token);	
		
		
		log.debug("Fin - Dao");
		return tokenMedicoDto;
	}

	@Override
	public void borrarToken(String token) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		if(token == null || token.isEmpty()) {
			throw new RuntimeException();
		}
		
		try{
			sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().load(TokenMedicoDto.class, token));
		}
		catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "al borrar el código del médico." + ex.getMessage();
			log.debug(ex.getMessage());
			throw new RuntimeException(msjEx,ex.getCause());
		}
		
		log.debug("Fin - Dao");
	}
	
	

}
