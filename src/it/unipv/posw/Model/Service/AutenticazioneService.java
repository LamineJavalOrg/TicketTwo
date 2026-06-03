package it.unipv.posw.Model.Service;

import it.unipv.posw.Model.Cliente;
import it.unipv.posw.Model.Organizzatore;
import it.unipv.posw.Model.Exception.CredenzialiErrateException;
import it.unipv.posw.Model.Persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
* @author gpelle
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
	
	public Organizzatore loginOrganizzatore(String email, String password) throws CredenzialiErrateException{
		
		Organizzatore org = MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().trovaOrganizzatorePerEmail(email);
		
		if (org == null || !org.getEmail().equals(email) || !org.getPassword().equals(password)) {
			throw new CredenzialiErrateException();
		}
		return org;
	}

}
