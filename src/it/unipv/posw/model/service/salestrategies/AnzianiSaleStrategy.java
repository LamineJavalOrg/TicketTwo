package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

/**
 * Stategia concreta del Pattern Strategy che applica lo sconto per utenti senior.
 * @author gpelle
 * @see ISaleStrategy
 */

public class AnzianiSaleStrategy implements ISaleStrategy {
	
	private final static double PERCENTUALE_SCONTO = 0.3; 
	private final static double ETA_SOGLIA = 65;

	/**
	 * Applica lo sconto solo se l'utente è valorizzato e ha età maggiore o uguale
	 * alla soglia, altrimenti restituisce il prezzo base invariato.
	 */
	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		if (u == null) {
	        return prezzoBase;
	    }
		
		if (u.getEta() >= ETA_SOGLIA) {
			double valoreSconto = prezzoBase * PERCENTUALE_SCONTO;
		    double prezzoScontato = prezzoBase - valoreSconto;
		    
		    return prezzoScontato;
		}
		return prezzoBase;
	}
	
	@Override
	public String getNomeStrategy() {
		return "Sconto Senior (Over 65)";
	}
}
