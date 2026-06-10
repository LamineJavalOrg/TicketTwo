package it.unipv.posw.controller;

import it.unipv.posw.model.gestori.GestoreHome;
import it.unipv.posw.view.MainFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainController {
	
	private MainFrame mainF;
	private GestoreHome model;
	
	public MainController(MainFrame mainF, GestoreHome model) {
		
		this.mainF = mainF;
		this.model = model;
		
		mainF.mostraSchermata(mainF.gethView());
		new RicercaController(mainF.getrView(), model.getStrategyDefault());
		
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
	     
	   // Listener per l'item Login organizzatore
	    mainF.getItemLoginOrg().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleLoginOrganizzatore(event);
				
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
		 new AutenticazioneController(mainF.getaView(), model.getAutenticazioneService());
	 }
	 
	 private void handleLoginOrganizzatore(ActionEvent e) {
		 mainF.mostraSchermata(mainF.getAorView());
		 mainF.setVisibilitaBarraRicerca(false);
		 new AutenticazioneOrgController(mainF.getAorView());
	 }
	    
	 private void handleRegistrazione(ActionEvent e) {
		 mainF.mostraSchermata(mainF.getRegView());
		 mainF.setVisibilitaBarraRicerca(false);
		 new RegistrazioneController(mainF.getRegView(), model.getRegistrazioneService());
	 }
	 
	
	    
}


