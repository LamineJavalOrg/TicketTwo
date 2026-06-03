package it.unipv.posw.model.service;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

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
