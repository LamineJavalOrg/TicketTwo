package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.exception.IndisponibilitàException;
import it.unipv.posw.model.exception.SuperamentoLimiteBigliettiException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/**
 * @author rkomi-dev
 */

public class CarrelloService {
	
	final int MAX_BIGLIETTI = 4;
	
	public CarrelloService() {
    }

	public void aggiungiAlCarrello(int idEvento, int idTappa, int idSettore, String tipo, int quantita, double prezzoAcquisto) 
            throws IndisponibilitàException, SuperamentoLimiteBigliettiException {
        
		List<Biglietto> listaBiglietti = MYSQLDAOFactory.getInstance().getBigliettoDAO().getBigliettiDisponibili(idTappa, idSettore, tipo, quantita);
    	
    	int giàInCarrello = 0;
        for (Biglietto b : Carrello.getInstance().getItems()) {
            if (b.getTariffa().getId_evento() == idEvento) {
                giàInCarrello++;
            }
        }
        
        //verifico disponibilità
    	if(listaBiglietti.size() < quantita) {
    		throw new IndisponibilitàException();
    	}
    	
        // Verifico il limite dei 4 biglietti
        if (giàInCarrello + quantita > MAX_BIGLIETTI) {
            throw new SuperamentoLimiteBigliettiException();
        }
        
    	
    	for(Biglietto b: listaBiglietti) {
    		Carrello.getInstance().aggiungi(b);
    	}
    	
    }
}
