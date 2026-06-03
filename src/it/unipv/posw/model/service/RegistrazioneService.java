package it.unipv.posw.model.service;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
* @author gpelle
*/

public class RegistrazioneService {
	
	public RegistrazioneService() {
	}

	public boolean registraNuovoCliente(Cliente cliente) {
        
        return MYSQLDAOFactory.getInstance().getClienteDAO().salvaCliente(cliente);
    }
	
	public boolean registraNuovoOrganizzatore(Organizzatore org) {
		
	return MYSQLDAOFactory.getInstance().getOrganizzatoreDAO().salvaOrganizzatore(org);
	
	}
}