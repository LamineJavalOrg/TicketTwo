package it.unipv.posw.model.service;

import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.exception.IndisponibilitàException;
import it.unipv.posw.model.exception.SuperamentoLimiteBigliettiException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/**
 * @author rkomi-dev
 */

public class CarrelloService {
	
	final int MAX_BIGLIETTI = 5;
	final int PLUS = 10;
	
	public CarrelloService() {
    }

	public void aggiungiAlCarrello(int idEvento, int idTappa, int idSettore, TipologiaBiglietto tipo, int quantita) 
            throws IndisponibilitàException, SuperamentoLimiteBigliettiException {
        
		List<Biglietto> listaBiglietti = MYSQLDAOFactory.getInstance().getBigliettoDAO().getBigliettiDisponibili(idTappa, idSettore, tipo, quantita + PLUS);
    	
		int giàInCarrelloPerEvento = 0;
        List<Integer> idBigliettiNelCarrello = new ArrayList<>();
        

        for (Biglietto b : Carrello.getInstance().getItems()) {
            if (b.getTariffa().getId_evento() == idEvento) {
                giàInCarrelloPerEvento++;
            }
            idBigliettiNelCarrello.add(b.getId_biglietto()); 
        }
        
        if (giàInCarrelloPerEvento + quantita > MAX_BIGLIETTI) {
            throw new SuperamentoLimiteBigliettiException();
        }
        
        // prendo solo quelli non ancora nel carrello
        List<Biglietto> bigliettiFiltrati = new ArrayList<>();
        for (Biglietto b : listaBiglietti) {
            if (!idBigliettiNelCarrello.contains(b.getId_biglietto())) {
                bigliettiFiltrati.add(b);
            }
            // se viene raggiunta la quantità richiesta stoppo
            if (bigliettiFiltrati.size() == quantita) {
                break;
            }
        }
        
        // verifico disponibilità finale
        if (bigliettiFiltrati.size() < quantita) {
            throw new IndisponibilitàException();
        }
        
    	
    	for(Biglietto b: bigliettiFiltrati) {
    		Carrello.getInstance().aggiungi(b);
    	}
    	
    }
}
