package it.unipv.posw.controller;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.SessioneCliente;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.view.AutenticazioneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AutenticazioneController {
	
	private AutenticazioneView view;
	private AutenticazioneService aService;

	public AutenticazioneController(AutenticazioneView view) {
	    this.view = view;
	    this.aService = new AutenticazioneService();
	    
	    this.view.getBtnLogin().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLoginCliente(event);
            }
        });
	}

	private void handleLoginCliente(ActionEvent e) {
	    
		try {
			Cliente loggato = aService.loginCliente(view.getTxtEmail().getText(), view.getTxtPassword().getText());
	    
	    
	    	SessioneCliente.getInstance().svuotaSessione();
	    	SessioneCliente.getInstance().setUtenteLoggato(loggato);
	    
		}catch (CredenzialiErrateException ex) {
			ex.printStackTrace();
		} finally {
			view.getTxtEmail().setText("");
	        view.getTxtPassword().setText("");
		}
	}

}
