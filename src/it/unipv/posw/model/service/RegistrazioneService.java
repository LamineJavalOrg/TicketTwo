package it.unipv.posw.model.service;

import java.time.LocalDate;

import it.unipv.posw.model.entities.Cliente;
import it.unipv.posw.model.entities.Organizzatore;
import it.unipv.posw.model.exception.DataNascitaException;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** Classe del model che gestisce la registrazione dell'account
* @author rkomi-dev
* @author gpelle
*/

public class RegistrazioneService {
	
	public RegistrazioneService() {
	}
	
	/** Metodo che permette la registrazione di un nuovo cliente nel sistema
	 * @see Cliente
	 * @param cliente Il cliente che vuole registrarsi
	 * @return Cliente registrato
	 * @throws EmptyFieldException Se uno o più campi sono vuoti
	 * @throws WrongEmailFormatException Se l'email inserita non contiene '@'
	 * @throws EmailEsistenteException Se l'email inserita è associata ad un Cliente già registrato
	 * @throws DataNascitaException Se la data di nascita inserita è futura rispetto a quella corrente
	 */
	
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