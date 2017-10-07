package mx.escom.tt.diabetes.model.dao;


import mx.escom.tt.diabetes.model.dto.ClienteDto;

public interface ClienteDao {
	
	public ClienteDto recuperarClientePorId(String idCliente) throws RuntimeException;
	

}
