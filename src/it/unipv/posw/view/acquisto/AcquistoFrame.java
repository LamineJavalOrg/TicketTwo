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

	public AcquistoFrame() {
		
		eventiPerArtistaView = new EventiPerArtistaView();
		eventoView = new EventoView();

	}
	

	
	public EventiPerArtistaView getEventiPerArtistaView() {
		return eventiPerArtistaView;
	}

	public EventoView getEventoView() {
		return eventoView;
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
