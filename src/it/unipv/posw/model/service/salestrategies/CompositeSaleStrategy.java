package it.unipv.posw.model.service.salestrategies;

import java.util.ArrayList;

import it.unipv.posw.model.entities.Utente;

/**
 * Classe che implementa pattern Composite applicato alle strategie di sconto.
 * Implementa l'interfaccia {@link ISaleStrategy} e agisce come contenitore di 
 * sottostrategie concorrenti.
 * Aggrega un'insieme di strategie, scorre tutte le strategie registrate
 * (foglie) e le valuta restituendo il prezzo più conveniente per l'acquirente.
 * Memorizza inotre la strategia vincente per poterne esporre il nome.
 * @author gpelle
 * @see ISaleStrategy
 */
public class CompositeSaleStrategy implements ISaleStrategy {
	private ArrayList<ISaleStrategy> sale_str;
	private ISaleStrategy strategiaVincente;

	/**
	 * Costruisce un composite vuoto, pronto ad aggregare strategie.
	 */
    public CompositeSaleStrategy() {
    	this.sale_str = new ArrayList<ISaleStrategy>();
    }
    
    /**
     * Aggiunge una strategia all'insieme di quelle valutate.
     * @param strategy La strategia da aggregare
     */
    public void addStrategy(ISaleStrategy strategy) {
        sale_str.add(strategy);
    }
 
    /**
     * Restituisce l'insieme delle strategie aggregate.
     * @return La lista delle strategie figlie
     */
    public ArrayList<ISaleStrategy> getStrategy() {
        return sale_str;
    }
	
	/**
	 * Valuta tutte le strategie aggregate e restituisce il prezzo più basso 
	 * ottenuto, memorizzando la strategia che lo ha prodotto.
	 */
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

	/**
	 * Restituisce il nome della strategia vincente dell'ultimo calcolo, oppure
	 * "Prezzo Standard" se nessuno sconto era applicabile.
	 */
	@Override
	public String getNomeStrategy() {
        if (strategiaVincente != null) {
            return strategiaVincente.getNomeStrategy();
        }
        return "Prezzo Standard";
    }
}
