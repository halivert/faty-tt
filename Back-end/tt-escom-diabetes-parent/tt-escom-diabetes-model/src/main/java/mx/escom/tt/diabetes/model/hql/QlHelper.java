package mx.escom.tt.diabetes.model.hql;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class QlHelper {
	

	private @Getter @Setter Map<String,String> queries;
	
	public String getQuery(String id){
		log.debug("Inicio");
		log.debug("id: "+id);
		String query = (String)queries.get(id);
		if(query==null){
			throw new NullPointerException(id+" no está registrado.");
		}
		log.debug("Fin");
		return query;
	}
}
