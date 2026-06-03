package it.unipv.posw.Model.Service;

import it.unipv.posw.Model.Cliente;
import it.unipv.posw.Model.Exception.CredenzialiErrateException;
import it.unipv.posw.Model.Persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
*/

public class AutenticazioneService {
	
	public AutenticazioneService() {
	}

	public Cliente loginCliente(String email, String password) throws CredenzialiErrateException {
	    Cliente cliente = MYSQLDAOFactory.getInstance().getClienteDAO().trovaClientePerEmail(email);
	    
	    if(cliente == null || !cliente.getEmail().equals(email) || !cliente.getPassword().equals(password)) {
	    	throw new CredenzialiErrateException();
	    }
		return cliente;
	}

}
