package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

/**
 * Strategia concreta del pattern Strategy che applica la promozione 
 * di Natale, indipendente dall'utente.
 * @author gpelle
 * @see ISaleStrategy
 */

public class XmasSaleStrategy implements ISaleStrategy{
	
	private final static double PERCENTUALE_SCONTO = 0.2;

	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		double valoreSconto = prezzoBase * PERCENTUALE_SCONTO;
	    double prezzoScontato = prezzoBase - valoreSconto;
	    
	    return prezzoScontato;
	}
	
	@Override
	public String getNomeStrategy() {
		return "Offerta Natale";
	}

}
