package it.unipv.posw.view.admin;

import it.unipv.posw.view.IView;
import it.unipv.posw.view.home.PannelloAdminView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * @author gpelle
 */

public class PannelloAdminFrame extends BorderPane implements IView {
	
	private PannelloAdminView pannelloAdminView;
	private ConfiguraSedeView configuraSedeView;
	
	public PannelloAdminFrame() {
		pannelloAdminView = new PannelloAdminView();
		configuraSedeView = new ConfiguraSedeView();
	}
	
	public void mostraSchermata(IView s) {
		this.setCenter(s.getNodo());
	}

	public PannelloAdminView getPannelloAdminView() {
		return pannelloAdminView;
	}

	public ConfiguraSedeView getConfiguraSedeView() {
		return configuraSedeView;
	}
	
	
	@Override
	public Node getNodo() {
		return this;
	}
	
	
	
	
	
	

}
