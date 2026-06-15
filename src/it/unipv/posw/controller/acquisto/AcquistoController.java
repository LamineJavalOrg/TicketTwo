package it.unipv.posw.controller.acquisto;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.exception.IndisponibilitàException;
import it.unipv.posw.model.exception.SuperamentoLimiteBigliettiException;
import it.unipv.posw.model.gestori.GestoreAcquisto;
import it.unipv.posw.view.acquisto.AcquistoFrame;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/** Controller che gestisce il flusso del caso d'uso acquisto
 * @author rkomi-dev
 */

public class AcquistoController {
	
	private AcquistoFrame acquistoF;
	private GestoreAcquisto model;

	public AcquistoController(AcquistoFrame acquistoF, GestoreAcquisto model) {

		this.acquistoF = acquistoF;
		this.model = model;
		
		this.inizializzaListeners();
	}
	
	public void inizializzaListeners() {
		
		acquistoF.getEventoView().getBtnAggiungi().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				handleAggiungi(event);
				
			}
		});
		
		acquistoF.getEventoView().getBtnCarrello().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				handleVaiAlCarrello(event);
				
			}
		});
		
	}
	
	
	public void handleAggiungi(ActionEvent e) {
		
		if(!SessioneCliente.getInstance().isLoggato()) {
			AlertView.mostraErrore("Effettua il login per aggiungere al carrello");
			return;
		}
		
		try {
			model.getCarrelloService().aggiungiAlCarrello(0, 0, 0, null, 0, 0);
		}catch (SuperamentoLimiteBigliettiException ex) {
			AlertView.mostraErrore(ex.getMessage());
		}catch (IndisponibilitàException ex) {
			AlertView.mostraErrore(ex.getMessage());
		}
	}
	
	public void handleVaiAlCarrello(ActionEvent e) {
		
		if(!SessioneCliente.getInstance().isLoggato()) {
			AlertView.mostraErrore("Effettua il login per accedere al carrello");
			return;
		}
		
		acquistoF.getcView().svuotaVista();
		for (Biglietto b : model.getCarrello().getItems()) {
            String info = b.getTariffa().getTipob() + " : " + b.getPrezzoAcquisto() + "€";
            acquistoF.getcView().aggiungiRigaBiglietto(info);
        }

		acquistoF.getcView().setTotale("Totale da pagare: " + model.getCarrello().getTotale() + "€");
		acquistoF.mostraSchermata(acquistoF.getcView());
	}

}
