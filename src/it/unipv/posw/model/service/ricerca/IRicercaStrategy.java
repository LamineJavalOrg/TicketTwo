package it.unipv.posw.model.service.ricerca;

import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.RicercaType;

/** Interface per le strategie di ricerca, implementa il pattern Strategy
 * @see RicercaPerArtistaStrategy
 * @see RicercaPerEventoStrategy
 * @author rkomi-dev
 */

public interface IRicercaStrategy {
	
	/**
     * Esegue la ricerca testuale specifica della strategia basandosi su una query
     * @param query Il testo inserito dall'utente per filtrare i risultati
     * @return Una lista di oggetti che corrispondono ai criteri di ricerca
     */
	List<?> ricerca(String query);
	
	/**
     * Restituisce la tipologia di ricerca associata alla strategia corrente.
     * @return Il {@link RicercaType} corrispondente.
     */
	RicercaType getDestinazione();
	
	/**
     * Estrae la stringa testuale da mostrare come suggerimento nell'interfaccia grafica
     * partendo dall'oggetto generico restituito dalla ricerca
     * @param o L'oggetto da cui estrarre l'etichetta
     * @return Il nome dell'artista o dell'evento formattato come stringa
     */
	String getEtichettaSuggerimento(Object o);
	
	/**
     * Azione secondaria eseguita dopo che l'utente ha selezionato un risultato specifico 
     * dalla ricerca iniziale 
     * @param scelta L'oggetto selezionato dall'utente
     * @return La lista finale di {@link Evento} correlati alla scelta effettuata
     */
	List<Evento> eseguiPostRicerca(Object scelta);
	
}
