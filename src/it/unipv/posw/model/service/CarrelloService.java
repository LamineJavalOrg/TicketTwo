package it.unipv.posw.model.service;

import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.exception.IndisponibilitàException;
import it.unipv.posw.model.exception.SuperamentoLimiteBigliettiException;
import it.unipv.posw.model.gestori.GestoreEvento;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/** Classe del model che gestisce il riempimento del carrello
 * @author rkomi-dev
 */

public class CarrelloService {
	
	final int MAX_BIGLIETTI = 5;
	final int PLUS = 10;
	
	public CarrelloService() {
    }
	
	/** Metodo che permette di aggiungere biglietti al carrello
	 * @see Carrello
	 * @see SessioneCliente
	 * @param idEvento L'id dell'evento associato al biglietto
	 * @param idTappa L'id della tappa dell'evento associato al biglietto
	 * @param idSettore L'id del settore
	 * @param tipo La tipologia di biglietto 
	 * @param quantita La quantità di biglietti che si vuole aggiungere
	 * @throws IndisponibilitàException Se la quantità desiderata non è disponibile
	 * @throws SuperamentoLimiteBigliettiException Se si supera il limite di 5 biglietti aggiunti al carrello
	 */
	
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
    		double prezzoBase = b.getTariffa().getPrezzo();
            double prezzoScontato = GestoreEvento.getInstance().getEventoService().calcolaPrezzoFinale(prezzoBase, SessioneCliente.getInstance().getClienteLoggato());

            b.getTariffa().setPrezzo(prezzoScontato);
            
    		Carrello.getInstance().aggiungi(b);
    	}
    	
    }
}
