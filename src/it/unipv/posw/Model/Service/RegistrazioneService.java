package it.unipv.posw.Model.Service;

import it.unipv.posw.Model.Cliente;
import it.unipv.posw.Model.Persistence.MYSQLDAOFactory;

/** 
* @author rkomi-dev
*/
public class RegistrazioneService {
	
	public RegistrazioneService() {
	}

	public boolean registraNuovoCliente(Cliente cliente) {
        
        return MYSQLDAOFactory.getInstance().getClienteDAO().salvaCliente(cliente);
        
    }

}
