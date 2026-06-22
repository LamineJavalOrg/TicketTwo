package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

/**
 * Strategia concreta del pattern Strategy che applica la promozione 
 * Black Friday, indipendente dall'utente.
 * @author gpelle
 * @see ISaleStrategy
 */
public class BlackFridaySaleStrategy implements ISaleStrategy {
	
	private final static double PERCENTUALE_SCONTO = 0.4;

	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		double valoreSconto = prezzoBase * PERCENTUALE_SCONTO;
	    double prezzoScontato = prezzoBase - valoreSconto;
	    
	    return prezzoScontato;
	}
	
	@Override
	public String getNomeStrategy() {
		return "Offerta Black Friday";
	}
}