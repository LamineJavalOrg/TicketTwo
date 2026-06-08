package it.unipv.posw.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.DataNascitaException;
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

	public boolean registraNuovoCliente(Cliente cliente) throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
		
		if (cliente.getNome().isEmpty() || cliente.getCognome().isEmpty() ||cliente.getEmail().isEmpty()
	        	 ||	cliente.getPassword().isEmpty() || cliente.getData_nascita() == null) {
	            throw new EmptyFieldException();
	        }
	        
	    if(!cliente.getEmail().contains("@")) {
	        	throw new WrongEmailFormatException();
	        }
	        
	    if (cliente.getData_nascita().isAfter(LocalDate.now())) {
		    throw new DataNascitaException();
		}
	    
	    if(MYSQLDAOFactory.getInstance().getClienteDAO().isEmailEsistente(cliente.getEmail())) {
	        	throw new EmailEsistenteException();
	        }
        
        return MYSQLDAOFactory.getInstance().getClienteDAO().salvaCliente(cliente);
    }
	
	public boolean registraNuovoOrganizzatore(Organizzatore org) throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
		
		if (org.getNome().isEmpty() || org.getCognome().isEmpty() ||org.getEmail().isEmpty()
	        	 ||	org.getPassword().isEmpty() || org.getData_nascita() == null || org.getNome_organizzazione().isEmpty()) {
			throw new EmptyFieldException();
		}
		
		if (!org.getEmail().contains("@")) {
			throw new WrongEmailFormatException();
		}
		
		if (org.getData_nascita().isAfter(LocalDate.now())) {
		    throw new DataNascitaException();
		}
			
		if (MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().isEmailEsistente(org.getEmail())){
			throw new EmailEsistenteException();
		}
	return MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().salvaOrganizzatore(org);
	
	
}
}