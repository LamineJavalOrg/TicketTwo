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
 * @author gpelle
 */
public class EventoService {
	
	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		return costruisciSconti().calcolaPrezzoFinale(prezzoBase, u);
	}

	/* restituisce nome della strategia di sconto applicata.
	   Il composite memorizza la vincente solo dopo aver calcolato il prezzo */
	public String getNomeStrategiaApplicata(double prezzoBase, Utente u) {
		CompositeSaleStrategy contenitoreSconti = costruisciSconti();
		contenitoreSconti.calcolaPrezzoFinale(prezzoBase, u);
		return contenitoreSconti.getNomeStrategy();
	}

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
	
	
	public Tariffa getTariffaSpecifica(int idTappa, int idSettore, TipologiaBiglietto tipo) {
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .getTariffaCompleta(idTappa, idSettore, tipo);
    }

	
	public List<TipologiaBiglietto> getTipologiePerSettore(int idTappa, int idSettore) {
        return MYSQLDAOFactory.getInstance()
                              .getTariffaDAO()
                              .trovaTipologieTappaSettore(idTappa, idSettore);
    }
	
	public List<Tappa> getTappePerEvento(int idEvento) {
        return MYSQLDAOFactory.getInstance()
                              .getTappaDAO()
                              .trovaTappePerEvento(idEvento);
    }
}
