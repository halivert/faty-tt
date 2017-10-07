package mx.escom.tt.diabetes.model.dao.impl;


import org.springframework.stereotype.Repository;


import mx.escom.tt.diabetes.model.dao.ClienteDao;
import mx.escom.tt.diabetes.model.dto.ClienteDto;
import mx.escom.tt.diabetes.model.hql.ClienteHqlHelper;
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
@Repository("ClienteDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)

public class ClienteDaoImpl extends HibernateDaoSupport implements ClienteDao{
	
	@Autowired ClienteHqlHelper clienteHqlHelper;
	
	private @Getter @Setter QlHelper ql;

	@Override
	public ClienteDto recuperarClientePorId(String idCliente) throws RuntimeException{
		log.debug("Inicio - Dao");
		
		String msjEx = null;
		ClienteDto clienteDto = null;
		
		{//Validaciones
			if(idCliente == null){
				msjEx = "El identificador del cliente no puede ser nulo.";
				throw new RuntimeException(msjEx);
			}
		}
		
		try {
			clienteDto = getHibernateTemplate().execute(new HibernateCallback<ClienteDto>() {
				@Override
				public ClienteDto doInHibernate(Session session) throws HibernateException {
					log.debug("Inicio - Hibernate");
					ClienteDto clienteDtoAux = null;
					String queryStr = null;
					queryStr = clienteHqlHelper.getQuery(ClienteHqlHelper.RECUPERA_CLIENTE_POR_ID);
					
					log.debug("queryStr: "+queryStr);
					Query query = session.createQuery(queryStr);
					//Query query = session.createSQLQuery(queryStr);
					log.debug("idCliente : " + idCliente);
					query.setParameter("idCliente", idCliente);
					
					clienteDtoAux = (ClienteDto) query.uniqueResult();
					log.debug("Fin - Hibernate");
					return clienteDtoAux;
				}
				
			});
			
		}catch(Exception ex){
			msjEx = Constants.MSJ_EXCEPTION + "el cliente con id : " + idCliente + Constants.SALTO_LINEA + ex.getMessage();
			throw new RuntimeException(msjEx,ex.getCause());
		}
			
		log.debug("Fin - Dao");		
		return clienteDto;
	}
	
}
