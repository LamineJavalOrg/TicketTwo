package it.unipv.posw.model.service;

import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.entities.Utente;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.service.salestrategies.AnzianiSaleStrategy;
import it.unipv.posw.model.service.salestrategies.ISaleStrategy;
import it.unipv.posw.model.service.salestrategies.SaleStrategyFactory;
import it.unipv.posw.model.service.salestrategies.Under30SaleStrategy;

/**
 * @author gpelle
 */
public class EventoService {
	
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
	

	public double calcolaPrezzoFinale(double prezzoBase, Utente u) {
		// aggiunta strategie fisse
	    List<ISaleStrategy> strategie = new ArrayList<>();
	    strategie.add(new Under30SaleStrategy());
	    strategie.add(new AnzianiSaleStrategy());
	    
	    // recupero strategia dinamica dalla Factory ed aggiunta
	    ISaleStrategy strategiaDinamica = SaleStrategyFactory.getInstance().getDiscountStrategy();
	    if (strategiaDinamica != null) {
	        strategie.add(strategiaDinamica);
	    }
	    
	    // calcolo prezzo minimo di tutte le strategie
	    double prezzoMinimo = prezzoBase;
	    
	    for (ISaleStrategy s : strategie) {
	        double prezzoScontato = s.calcolaPrezzoFinale(prezzoBase, u);
	     
	        if (prezzoScontato < prezzoMinimo) {
	            prezzoMinimo = prezzoScontato;
	        }
	    }
	    
	    return prezzoMinimo;
	}
}
