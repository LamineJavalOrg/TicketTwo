package it.unipv.posw.model.service;


import java.util.List;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.entities.Utente;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.service.salestrategies.AnzianiSaleStrategy;
import it.unipv.posw.model.service.salestrategies.CompositeSaleStrategy;
import it.unipv.posw.model.service.salestrategies.ISaleStrategy;
import it.unipv.posw.model.service.salestrategies.SaleStrategyFactory;
import it.unipv.posw.model.service.salestrategies.Under30SaleStrategy;

/**
 * Classe del model che incapsula la logica di prezzo dinamico/scontistico e fornisce dati di
 * supporto per la visualizzazione ed acquisto dell'evento.
 * Costruisce un {@link CompositeSaleStrategy} combinando le strategie fisse (Under30, anziani)
 * con l'eventuale strategia dinamica creata tramite {@link SaleStrategyFactory}
 * @author gpelle
 * @see CompositeSaleStrategy
 * @see SaleStrategyFactory
 */
public class EventoService {
	
	/**
	 * Calcola il prezzo finale di un biglietto applicando l'insieme delle strategie di sconto.
	 * @param prezzoBase Prezzo tariffa base inserito in fase di creazione evento
	 * @param u {@link Utente} acquirente 
	 * @return Il prezzo finale scontato.
	 */
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		return costruisciSconti().calcolaPrezzoFinale(prezzoBase, u);
	}

	/**
	 * Restituisce il nome della strategia di sconto che ha generato il prezzo più basso per
	 * l'utente selezionato. 
	 * Il {@link CompositeSaleStrategy} memorizza la vincente solo dopo aver calcolato il prezzo.
	 * @param prezzoBase Prezzo tariffa base inserito in fase di creazione evento
	 * @param u {@link Utente} acquirente
	 * @return Il nome della strategia di sconto applicata
	 */
	public String getNomeStrategiaApplicata(double prezzoBase, Utente u) {
		CompositeSaleStrategy contenitoreSconti = costruisciSconti();
		contenitoreSconti.calcolaPrezzoFinale(prezzoBase, u);
		return contenitoreSconti.getNomeStrategy();
	}

	/**
	 * Costruisce il {@link CompositeSaleStrategy}. Aggiunge le strategie fisse legate all'età
	 * e l'eventuale strategia dinamica configurata via {@link SaleStrategyFactory}
	 * @return Il composite di strategie di sconto
	 */
	private CompositeSaleStrategy costruisciSconti() {
		CompositeSaleStrategy contSconti = new CompositeSaleStrategy();

		// aggiunta strategie fisse
		contSconti.addStrategy(new Under30SaleStrategy());
		contSconti.addStrategy(new AnzianiSaleStrategy());

		// recupero strategia dinamica
		ISaleStrategy strategiaDinamica = SaleStrategyFactory.getInstance().getDiscountStrategy();
		if (strategiaDinamica != null) {
			contSconti.addStrategy(strategiaDinamica);
		}

		return contSconti;
	}
	
	/**
	 * Recupera le informazioni di una  tariffa specifica incrociando i dati 
	 * della tappa, del settore fisico della sede e della tipologia di biglietto.
	 * @param idTappa Identificativo della tappa dell'evento
	 * @param idSettore Identificativo del settore
	 * @param tipo La tipologia del biglietto richiesto
	 * @return La {@link Tariffa} estratta
	 */
	public Tariffa getTariffaSpecifica(int idTappa, int idSettore, TipologiaBiglietto tipo) {
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .getTariffaCompleta(idTappa, idSettore, tipo);
    }

	/**
	 * Recupera le tipologie di biglietto disponibili per la combinazione tappa/settore.
	 * @param idTappa Identificativo della tappa dell'evento
	 * @param idSettore Identificativo del settore 
	 * @return La lista delle tipologie disponibili 
	 */
	public List<TipologiaBiglietto> getTipologiePerSettore(int idTappa, int idSettore) {
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .trovaTipologieTappaSettore(idTappa, idSettore);
    }
	
	/**
	 * Recupera le tappe associate ad un'evento
	 * @param idEvento Identificativo dell'evento
	 * @return La lista delle tappe dell'evento
	 */
	public List<Tappa> getTappePerEvento(int idEvento) {
        return MYSQLDAOFactory.getInstance()
                              .getTappaDAO()
                              .trovaTappePerEvento(idEvento);
    }
}
