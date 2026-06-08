package it.unipv.posw;

import it.unipv.posw.controller.MainController;
import it.unipv.posw.view.MainFrame;
import it.unipv.posw.view.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private MainFrame view;
	@Override
	public void start(Stage primaryStage) {
		   
		view = new MainFrame();

	    new MainController(view);

	       Scene scene = new Scene(view, 800, 600);

	       primaryStage.setTitle("TicketTwo");
	       primaryStage.setScene(scene);
	        
	       primaryStage.show();
	    }
	
	
	public static void main(String[] args) {
		launch(args);

	}

	

}
