package it.unipv.posw.view.acquisto;

import it.unipv.posw.view.IView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * @author rkomi-dev
 */

public class AcquistoFrame extends BorderPane implements IView {
	
	private EventiPerArtistaView eventiPerArtistaView;
	private EventoView eventoView;
	private CarrelloView cView;

	public AcquistoFrame() {
		
		eventiPerArtistaView = new EventiPerArtistaView();
		eventoView = new EventoView();
		cView = new CarrelloView();

	}

	public EventiPerArtistaView getEventiPerArtistaView() {
		return eventiPerArtistaView;
	}

	public EventoView getEventoView() {
		return eventoView;
	}
	

	public CarrelloView getcView() {
		return cView;
	}

	public void mostraSchermata(IView s) {
    	this.setCenter(s.getNodo());
    }

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
