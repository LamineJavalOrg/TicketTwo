package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente; 

/**
 * @author gpelle
 */

public interface ISaleStrategy {
	public double calcolaPrezzoFinale(double prezzoBase, Utente acquirente);
	public String getNomeStrategy();
}
