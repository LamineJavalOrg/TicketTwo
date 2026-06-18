package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente;

public class Under30SaleStrategy implements ISaleStrategy {
	
	private final static double PERCENTUALE_SCONTO = 0.3;

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
