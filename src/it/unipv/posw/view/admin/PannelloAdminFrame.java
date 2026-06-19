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
	private CreaEventoView creaEventoView;
	private ArOrganizzatoreView arOrganizzatoreView;
	
	public PannelloAdminFrame() {
		pannelloAdminView = new PannelloAdminView();
		configuraSedeView = new ConfiguraSedeView();
		creaEventoView = new CreaEventoView();
		arOrganizzatoreView = new ArOrganizzatoreView();
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
	
	public CreaEventoView getCreaEventoView() {
		return creaEventoView;
	}
	
	public ArOrganizzatoreView getArOrganizzatoreView() {
		return arOrganizzatoreView;
	}

	
	@Override
	public Node getNodo() {
		return this;
	}
}
