package it.unipv.posw.model.service;

import it.unipv.posw.model.entities.Cliente;
import it.unipv.posw.model.entities.Organizzatore;
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
	    
	    System.out.println("login effettuato con successo");
		return cliente;
	}
	
	public Organizzatore loginOrganizzatore(String email, String password) throws CredenzialiErrateException{
		
		Organizzatore org = MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().trovaOrganizzatorePerEmail(email);
		
		if (org == null || !org.getEmail().equals(email) || !org.getPassword().equals(password)) {
			throw new CredenzialiErrateException();
		}
		System.out.println("login organizzatore effettuato con successo");
		return org;
	}

}
