package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

/**
 * Strategia concreta del pattern Strategy che non applica alcuno sconto.
 * Funge da comportamento di default quando nessuna promozione è attiva.
 * @author gpelle
 * @see ISaleStrategy
 */

public class NoScontiSaleStrategy implements ISaleStrategy {
	
	private final static double PERCENTUALE_SCONTO = 0;

	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		double valoreSconto = prezzoBase * PERCENTUALE_SCONTO;
	    double prezzoScontato = prezzoBase - valoreSconto;
	    
	    return prezzoScontato;
	}
	
	@Override
	public String getNomeStrategy() {
		return "Prezzo Standard";
	}

}
