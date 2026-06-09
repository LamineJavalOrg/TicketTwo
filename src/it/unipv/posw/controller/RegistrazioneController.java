package it.unipv.posw.controller;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.DataNascitaException;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.service.RegistrazioneService;
import it.unipv.posw.view.AlertView;
import it.unipv.posw.view.RegistrazioneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author rkomi-dev
 * @author gpelle
 */

public class RegistrazioneController {
	
    private RegistrazioneView view;
    private RegistrazioneService rService;

    public RegistrazioneController(RegistrazioneView view) {
        this.view = view;
        this.rService = new RegistrazioneService();

        this.view.getBtnRegistratiC().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRegistrazioneCliente(event);
            }
        });
        
        this.view.getBtnRegistratiO().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRegistrazioneOrganizzatore(event);
            }
        });
    }

    private void handleRegistrazioneCliente(ActionEvent e) {

        Cliente c = new Cliente(
            view.getTxtNome().getText(),
            view.getTxtCognome().getText(),
            view.getDateNascita().getValue(),
            view.getTxtEmail().getText(),
            view.getTxtPassword().getText()
        );

        try {
        	rService.registraNuovoCliente(c);
        } catch (EmptyFieldException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        } catch (WrongEmailFormatException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        } catch (EmailEsistenteException ex) {
        	AlertView.mostraErrore(ex.getMessage());		
        } catch (DataNascitaException ex) {
			AlertView.mostraErrore(ex.getMessage());
		}
       
    }
    
    private void handleRegistrazioneOrganizzatore(ActionEvent e) {

        Organizzatore o = new Organizzatore(
        		view.getTxtNome().getText(), 
        		view.getTxtCognome().getText(),
                view.getDateNascita().getValue(),
                view.getTxtEmail().getText(),
                view.getTxtPassword().getText(), 
        		view.getTxtOrganizzazione().getText());

        try {
        	rService.registraNuovoOrganizzatore(o);
        }catch (EmptyFieldException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        }catch (WrongEmailFormatException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        }catch (EmailEsistenteException ex) {
        	AlertView.mostraErrore(ex.getMessage());
		} catch (DataNascitaException ex) {
			AlertView.mostraErrore(ex.getMessage());
		}
       
    }
}