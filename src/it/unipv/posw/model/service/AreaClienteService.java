package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.entities.RiepilogoAcquisto;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** 
 * @author rkomi-dev
 */

public class AreaClienteService {
	
	public AreaClienteService() {
		
	}
	
	public List<RiepilogoAcquisto> storicoAcquisti(String email) {
		
		return MYSQLDAOFactory.getInstance().getRiepilogoAcquistoDAO().getBigliettiAcquistati(email);
	}
	
	public void cambioNominativo(int id_biglietto, String nominativo) {
		
		MYSQLDAOFactory.getInstance().getBigliettoDAO().cambiaNominativo(id_biglietto, nominativo);
		
	}
}
