package it.unipv.posw.Model.Service;

import it.unipv.posw.Model.Cliente;
import it.unipv.posw.Model.Persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
*/

public class AutenticazioneService {
	
	public AutenticazioneService() {
	}

	public Cliente login(String email, String password) {
	    Cliente cliente = MYSQLDAOFactory.getInstance().getClienteDAO().trovaClientePerEmail(email);
	    
		return cliente;
	}

}
