package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

/**
 * Stategia concreta del Pattern Strategy che applica lo sconto per giovani (Under 30)
 * @author gpelle
 * @see ISaleStrategy
 */

public class Under30SaleStrategy implements ISaleStrategy {
	
	private final static double PERCENTUALE_SCONTO = 0.3;

	/**
	 * Applica lo sconto solo se l'utente è valorizzato e ha meno di 30 anni,
	 * altrimenti restituisce il prezzo base invariato.
	 */
	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		if (u == null) {
	        return prezzoBase;
	    }
		
		if (u.getEta() < 30) {
			double valoreSconto = prezzoBase * PERCENTUALE_SCONTO;
		    double prezzoScontato = prezzoBase - valoreSconto;
		    
		    return prezzoScontato;
		}
		return prezzoBase;
	}

	@Override
	public String getNomeStrategy() {
		return "Sconto Giovani (Under 30)";
	}

}
