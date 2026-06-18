package it.unipv.posw.model.service.salestrategies;

import java.util.ArrayList;

import it.unipv.posw.model.entities.Utente;

/**
 * @author gpelle
 */

public class CompositeSaleStrategy implements ISaleStrategy {
	private ArrayList<ISaleStrategy> sale_str;
	private ISaleStrategy strategiaVincente;

    public CompositeSaleStrategy() {
    	this.sale_str = new ArrayList<ISaleStrategy>();
    }
    
    public void addStrategy(ISaleStrategy strategy) {
        sale_str.add(strategy);
    }
 
    public ArrayList<ISaleStrategy> getStrategy() {
        return sale_str;
    }
	
	
	@Override
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		double prezzoFinale = prezzoBase;
		strategiaVincente = null;
		
		for (ISaleStrategy s : sale_str) {
			double prezzoScontato = s.calcolaPrezzoFinale(prezzoBase, u);
			
			if(prezzoScontato < prezzoFinale) {
				prezzoFinale = prezzoScontato;
				strategiaVincente = s;
			}
		}
		return prezzoFinale;
	}


	@Override
	public String getNomeStrategy() {
        if (strategiaVincente != null) {
            return strategiaVincente.getNomeStrategy();
        }
        return "Prezzo Standard"; // Caso in cui nessuno sconto è applicabile
    }
}
