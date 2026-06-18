package it.unipv.posw.controller.home;

import it.unipv.posw.model.entities.Cliente;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.view.home.AutenticazioneView;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author rkomi-dev
 */

public class AutenticazioneController {
	
	private AutenticazioneView view;
	private AutenticazioneService aService;

	public AutenticazioneController(AutenticazioneView view, AutenticazioneService aService) {
	    this.view = view;
	    this.aService = aService;
	    
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
	    	SessioneCliente.getInstance().setClienteLoggato(loggato);
	    	
		}catch (CredenzialiErrateException ex) {
			AlertView.mostraErrore(ex.getMessage());
		} finally {
			view.getTxtEmail().setText("");
	        view.getTxtPassword().setText("");
		}
	}
	
}
