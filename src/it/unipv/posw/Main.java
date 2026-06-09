package it.unipv.posw;

import it.unipv.posw.controller.MainController;
import it.unipv.posw.model.gestori.GestoreHome;
import it.unipv.posw.view.MainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		   
		MainFrame view = new MainFrame();
		
	    new MainController(view, GestoreHome.getInstance());

	       Scene scene = new Scene(view, 800, 600);

	       primaryStage.setTitle("TicketTwo");
	       primaryStage.setScene(scene);
	        
	       primaryStage.show();
	    }
	
	
	public static void main(String[] args) {
		launch(args);

	}

	

}
