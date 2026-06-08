package it.unipv.posw.controller;

import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.SessioneOrganizzatore;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.view.AutenticazioneOrgView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AutenticazioneOrgController {
	
	private AutenticazioneOrgView view;
	private AutenticazioneService aservice;
	
	public AutenticazioneOrgController(AutenticazioneOrgView view) {
		this.view = view;
		this.aservice = new AutenticazioneService();
		
		this.view.getBtnLogin().setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                handleLoginOrganizzatore(event);
            }
		});
	}
	
	private void handleLoginOrganizzatore(ActionEvent e) {
		
		try {
			Organizzatore loggato = aservice.loginOrganizzatore(
					view.getTxtEmail().getText(), 
					view.getTxtPassword().getText());
			
			SessioneOrganizzatore.getInstance().svuotaSessione();
			SessioneOrganizzatore.getInstance().setOrganizzatoreLoggato(loggato);
		
		}catch (CredenzialiErrateException ex) {
			ex.printStackTrace();
		}finally {
			view.getTxtEmail().setText("");
	        view.getTxtPassword().setText("");
		}
		
	}
	
	
	
	

}
