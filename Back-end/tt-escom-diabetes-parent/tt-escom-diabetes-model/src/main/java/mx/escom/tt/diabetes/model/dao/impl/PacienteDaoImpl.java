package mx.escom.tt.diabetes.model.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import mx.escom.tt.diabetes.model.dao.PacienteDao;
import mx.escom.tt.diabetes.model.dto.PacienteDto;

@CommonsLog
@Repository("PacienteDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PacienteDaoImpl  implements PacienteDao{
	
	private @Getter @Setter
	SessionFactory  sessionFactory;

	@Override
	public void guardarPaciente(PacienteDto pacienteDto) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		
		if(pacienteDto == null) {
			throw new RuntimeException();
		}
		
		sessionFactory.getCurrentSession().save(pacienteDto);
		
		log.debug("Fin - Dao");
	}

	@Override
	public PacienteDto recuperarPacientePorIdPaciente(Integer idPaciente) throws RuntimeException {
		log.debug("Inicio - Dao");
		
		PacienteDto result = null;
	
		if(idPaciente == null) {
			throw new RuntimeException();
		}
		
		//result = (PacienteDto) sessionFactory.getCurrentSession().load(PacienteDto.class, idPaciente);
		
		result = (PacienteDto) sessionFactory.getCurrentSession().get(PacienteDto.class, idPaciente);
		
		log.debug("Fin - Dao");
		return result;
	}

	@Override
	public void eliminarPacientePorIdPaciente(Integer idPaciente) throws RuntimeException {
		log.debug("Incio - Dao");
		
		if(idPaciente == null) {
			throw new RuntimeException();
		}
		
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(PacienteDto.class, idPaciente));
		
		log.debug("Fin - Dao");
	}

	@Override
	public void actualizarInformacionPaciente(PacienteDto pacienteDto) throws RuntimeException {
		log.debug("Incio - Dao");
		
		if(pacienteDto == null) {
			throw new RuntimeException();
		}
		
		sessionFactory.getCurrentSession().update(pacienteDto);
		

		log.debug("Fin - Dao");
	}

	

	

}
