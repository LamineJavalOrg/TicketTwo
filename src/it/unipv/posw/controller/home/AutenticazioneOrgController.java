package it.unipv.posw.controller.home;

import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.view.home.AutenticazioneOrgView;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author gpelle
 */

public class AutenticazioneOrgController {
	
	private AutenticazioneOrgView view;
	private AutenticazioneService aService;
	
	public AutenticazioneOrgController(AutenticazioneOrgView view, AutenticazioneService aService) {
		this.view = view;
		this.aService = aService;
		
		this.view.getBtnLogin().setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                handleLoginOrganizzatore(event);
            }
		});
	}
	
	private void handleLoginOrganizzatore(ActionEvent e) {
		
		try {
			aService.loginOrganizzatore(view.getTxtEmail().getText(), view.getTxtPassword().getText());
					
		}catch (CredenzialiErrateException ex) {
			AlertView.mostraErrore(ex.getMessage());
		}finally {
			view.getTxtEmail().setText("");
	        view.getTxtPassword().setText("");
		}
		
	}
	
	
	
	

}
