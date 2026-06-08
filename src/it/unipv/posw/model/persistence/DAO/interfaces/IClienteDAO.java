package it.unipv.posw.model.persistence.DAO.interfaces;

import it.unipv.posw.model.Cliente;

/**
* @author rkomi-dev
*/

public interface IClienteDAO {
	
	boolean salvaCliente(Cliente cliente);
	Cliente trovaClientePerEmail(String email);
	boolean isEmailEsistente(String email);
	boolean deleteCliente(String email);
}
