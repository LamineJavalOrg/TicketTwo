package it.unipv.posw.model.service;

import it.unipv.posw.model.entities.Cliente;
import it.unipv.posw.model.entities.Organizzatore;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** Classe del model che gestisce l'autenticazione
* @author rkomi-dev
* @author gpelle
*/

public class AutenticazioneService {
	
	public AutenticazioneService() {
	}
	
	/** Metodo che permette di effettuare il login al cliente
	 * @see Cliente
	 * @see SessioneCliente
	 * @param email Email del cliente
	 * @param password Password del cliente
	 * @return true se il login viene effettuato correttamente
	 * @throws CredenzialiErrateException Se una o più credenziali sono errate
	 */
	
	public boolean loginCliente(String email, String password) throws CredenzialiErrateException {
	    Cliente cliente = MYSQLDAOFactory.getInstance().getClienteDAO().trovaClientePerEmail(email);
	    
	    if(cliente == null || !cliente.getEmail().equals(email) || !cliente.getPassword().equals(password)) {
	    	throw new CredenzialiErrateException();
	    }
	    SessioneCliente.getInstance().svuotaSessione();
    	SessioneCliente.getInstance().setClienteLoggato(cliente);
	    System.out.println("login effettuato con successo");
	    
		return true;
	}
	
	/**
	 * Metodo che permette di effettuare il login all'organizzatore
	 * @see Organizzatore
	 * @see SessioneOrganizzatore
	 * @param email Email dell'organizzatore
	 * @param password Password dell'organizzatore
	 * @return true se il login viene effettuato correttamente
	 * @throws CredenzialiErrateException Se una o più credenziali sono errate.
	 */
	public boolean loginOrganizzatore(String email, String password) throws CredenzialiErrateException{
		
		Organizzatore org = MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().trovaOrganizzatorePerEmail(email);
		
		if (org == null || !org.getEmail().equals(email) || !org.getPassword().equals(password)) {
			throw new CredenzialiErrateException();
		}
		SessioneOrganizzatore.getInstance().svuotaSessione();
		SessioneOrganizzatore.getInstance().setOrganizzatoreLoggato(org);
		System.out.println("login organizzatore effettuato con successo");
		
		return true;
	}

}
