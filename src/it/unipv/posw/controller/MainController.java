package it.unipv.posw.controller;

import it.unipv.posw.view.MainFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainController {
	
	private MainFrame mainF;
	
	public MainController(MainFrame mainF) {
		
		this.mainF = mainF;

		mainF.mostraSchermata(mainF.gethView());
		new RicercaController(mainF.getrView());
		
		this.inizializzaListener();
	}
		
	private void inizializzaListener() {
	        
	   // Listener per l'item Home
	     mainF.getItemHome().setOnAction(new EventHandler<ActionEvent>() {
	    	 @Override
	         public void handle(ActionEvent event) {
	             handleHome(event);
	         }
	     });

	   // Listener per l'item Login
	     mainF.getItemLogin().setOnAction(new EventHandler<ActionEvent>() {
	         @Override
	         public void handle(ActionEvent event) {
	             handleLogin(event);
	         }
	     });

	   // Listener per l'item Registrati
	     mainF.getItemRegistrati().setOnAction(new EventHandler<ActionEvent>() {
	         @Override
	         public void handle(ActionEvent event) {
	            handleRegistrazione(event);
	         }
	    });
	 }
	    
	 private void handleHome(ActionEvent e) {
		 mainF.mostraSchermata(mainF.gethView());
		 mainF.setVisibilitaBarraRicerca(true);
	 }
	    
	 private void handleLogin(ActionEvent e) {
		 mainF.mostraSchermata(mainF.getaView());
		 mainF.setVisibilitaBarraRicerca(false);
		 new AutenticazioneController(mainF.getaView());
	 }
	    
	 private void handleRegistrazione(ActionEvent e) {
		 mainF.mostraSchermata(mainF.getRegView());
		 mainF.setVisibilitaBarraRicerca(false);
		 new RegistrazioneController(mainF.getRegView());
	 }
	    
}


