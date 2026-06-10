package it.unipv.posw.view.home;

import it.unipv.posw.view.IView;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author rkomi-dev
 */

public class HomeView extends VBox implements IView {
   
    public HomeView() {
    	
    	this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("background");

    }

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}



	

    
    
}
