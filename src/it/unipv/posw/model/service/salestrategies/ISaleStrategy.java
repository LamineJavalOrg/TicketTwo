package it.unipv.posw.model.service.salestrategies;

import it.unipv.posw.model.entities.Utente; 

/**
 * Interfaccia radice del Pattern Strategy per il calcolo delle promozioni. 
 * Ciascuna strategia concreta (sconti per età, promozioni stagionali, nessuno sconto)
 * applica una propria logica a partire dal prezzo base e, ove rilevante, dai dati
 * dell'acquirente. Questo consente di variare la politica di prezzo senza modificare
 * il codice che la utilizza.
 * @author gpelle
 * @see CompositeSaleStrategy
 * @see SaleStrategyFactory
 */

public interface ISaleStrategy {
	/**
	 * Calcola il prezzo finale a partire dal prezzo base, applicando la specifica
	 * politica di sconto della strategia.
	 * @param prezzoBase Il prezzo di partenza
	 * @param acquirente L'{@link Utente} (può essere usato per sconti basati sull'età)
	 * @return Il prezzo finale calcolato dopo l'applicazione della riduzione.
	 */
	public double calcolaPrezzoFinale(double prezzoBase, Utente acquirente);
	
	/**
	 * Restituisce il nome descrittivo della strategia, mostrabile nella UI.
	 * @return Una stringa contenente il nome della strategia.
	 */
	public String getNomeStrategy();
}
