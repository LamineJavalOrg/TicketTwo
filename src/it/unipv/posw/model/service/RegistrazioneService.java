package it.unipv.posw.model.service;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
* @author gpelle
*/

public class RegistrazioneService {
	
	public RegistrazioneService() {
	}

	public boolean registraNuovoCliente(Cliente cliente) throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException {
		
		if (cliente.getNome().isEmpty() || cliente.getCognome().isEmpty() ||cliente.getEmail().isEmpty()
	        	 ||	cliente.getPassword().isEmpty() || cliente.getData_nascita() == null) {
	            throw new EmptyFieldException();
	        }
	        
	        if(!cliente.getEmail().contains("@")) {
	        	throw new WrongEmailFormatException();
	        }
	        
	        if(MYSQLDAOFactory.getInstance().getClienteDAO().isEmailEsistente(cliente.getEmail())) {
	        	throw new EmailEsistenteException();
	        }
        
        return MYSQLDAOFactory.getInstance().getClienteDAO().salvaCliente(cliente);
    }
	
	public boolean registraNuovoOrganizzatore(Organizzatore org) {
		
	return MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().salvaOrganizzatore(org);
	
	}
}