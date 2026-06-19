package it.unipv.posw.controller.home;

import it.unipv.posw.model.entities.Cliente;
import it.unipv.posw.model.entities.Organizzatore;
import it.unipv.posw.model.exception.DataNascitaException;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.service.RegistrazioneService;
import it.unipv.posw.view.home.RegistrazioneView;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author rkomi-dev
 * @author gpelle
 */

public class RegistrazioneController {
	
    private RegistrazioneView view;
    private RegistrazioneService rService;

    public RegistrazioneController(RegistrazioneView view, RegistrazioneService rService) {
        this.view = view;
        this.rService = rService;

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
		} finally {
			view.getTxtNome().clear();
            view.getTxtCognome().clear();
            view.getTxtEmail().clear();
            view.getTxtPassword().clear();
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