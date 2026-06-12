package it.unipv.posw.view.ricerca;


import it.unipv.posw.view.IView;
import it.unipv.posw.view.acquisto.AcquistoFrame;
import it.unipv.posw.view.home.RicercaView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * @author rkomi-dev
 */

public class RicercaFrame extends BorderPane implements IView {
	
	private RicercaView ricercaView;

	public RicercaFrame() {
		
		ricercaView = new RicercaView();
	}

	public RicercaView getRicercaView() {
		return ricercaView;
	}
	
	public void mostraSchermata(IView s) {
    	this.setCenter(s.getNodo());
    }

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public AcquistoFrame creAcquistoFrame() {
		return new AcquistoFrame();
	}

}
