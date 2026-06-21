package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.entities.RiepilogoAcquisto;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** Classe del model che gestisce l'area riservata del cliente
 * @author rkomi-dev
 */

public class AreaClienteService {
	
	public AreaClienteService() {
		
	}
	
	/**
	 * @see RiepilogoAcquisto
	 * @param email L'email del cliente
	 * @return Una lista di riepilogo dei biglietti acquistati
	 */
	public List<RiepilogoAcquisto> storicoAcquisti(String email) {
		
		return MYSQLDAOFactory.getInstance().getRiepilogoAcquistoDAO().getBigliettiAcquistati(email);
	}
	
	/**
	 * Metodo che permette il cambio nominativo ad un biglietto
	 * @param id_biglietto L'id del biglietto da rinominare
	 * @param nominativo Il nuovo nominativo
	 */
	public void cambioNominativo(int id_biglietto, String nominativo) {
		
		MYSQLDAOFactory.getInstance().getBigliettoDAO().cambiaNominativo(id_biglietto, nominativo);
		
	}
}
