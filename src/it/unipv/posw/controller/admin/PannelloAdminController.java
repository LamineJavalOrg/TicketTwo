package it.unipv.posw.controller.admin;

import it.unipv.posw.model.gestori.GestoreAdmin;
import it.unipv.posw.view.admin.PannelloAdminFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author gpelle
 */
public class PannelloAdminController {
	
	private PannelloAdminFrame pannelloAdmF;
	private GestoreAdmin model;
	
	public PannelloAdminController(PannelloAdminFrame pannelloAdmF, GestoreAdmin model) {
		this.pannelloAdmF = pannelloAdmF;
		this.model = model;
		
		this.addListeners();
		}
	
	
		private void addListeners() {
			
			// Listener per Configura Sede
			pannelloAdmF.getPannelloAdminView().getBtnConfiguraSede().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					handleConfiguraSede(event);	
				}
			});
			
			
			// Listener per Crea Evento
			pannelloAdmF.getPannelloAdminView().getBtnCreaEvento().setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					handleCreaEvento(event);					
				}
			});		
	}
		
		private void handleConfiguraSede(ActionEvent e) {
			pannelloAdmF.mostraSchermata(pannelloAdmF.getConfiguraSedeView());
			new ConfiguraSedeController(pannelloAdmF.getConfiguraSedeView(), model.getSedeService());
	}

		private void handleCreaEvento(ActionEvent e) {
	}
}
