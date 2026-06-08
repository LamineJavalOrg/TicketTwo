package it.unipv.posw.controller;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.service.RegistrazioneService;
import it.unipv.posw.view.RegistrazioneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
    }

    private void handleRegistrazioneCliente(ActionEvent e) {

        Cliente c = new Cliente(
            view.getTxtNome().getText(),
            view.getTxtCognome().getText(),
            view.getDateNascita().getValue(),
            view.getTxtEmail().getText(),
            view.getTxtPassword().getText()
        );

        rService.registraNuovoCliente(c);
       
    }
}