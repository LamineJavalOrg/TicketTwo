package it.unipv.posw.controller.home;

import it.unipv.posw.controller.admin.PannelloAdminController;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.gestori.GestoreAdmin;
import it.unipv.posw.model.gestori.GestoreHome;
import it.unipv.posw.view.admin.PannelloAdminFrame;
import it.unipv.posw.view.home.HomeFrame;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HomeController {
	
	private HomeFrame mainF;
	private GestoreHome model;
	
	public HomeController(HomeFrame mainF, GestoreHome model) {
		
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
	    
		// Listener per l'item Area Admin
	    mainF.getItemPanAdm().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleAreaAdmin(event);
				
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
         new AutenticazioneOrgController(mainF.getAorView(), model.getAutenticazioneService());
         }
	 
	    
	 private void handleAreaAdmin(ActionEvent e) {
		 if (!SessioneOrganizzatore.getInstance().isLoggato()) {
			 AlertView.mostraErrore("Effettua il login organizzatore per accedere all'area admin");
			 return;
		 }
		 PannelloAdminFrame panAdm = mainF.creaPannelloAdminFrame();
		 panAdm.mostraSchermata(panAdm.getPannelloAdminView());
		 
		 mainF.mostraSchermata(panAdm);
		 mainF.setVisibilitaBarraRicerca(false);
		 new PannelloAdminController(panAdm, GestoreAdmin.getInstance());
		 }
	 
	 
	 private void handleRegistrazione(ActionEvent e) {
		 mainF.mostraSchermata(mainF.getRegView());
		 mainF.setVisibilitaBarraRicerca(false);
		 new RegistrazioneController(mainF.getRegView(), model.getRegistrazioneService());
	 }
	 
	
	    
}


