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
	private PagamentoView pView;
	private QRView qrView;

	public AcquistoFrame() {
		
		eventiPerArtistaView = new EventiPerArtistaView();
		eventoView = new EventoView();
		cView = new CarrelloView();
		pView = new PagamentoView();
		qrView = new QRView();

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
	
	
	public PagamentoView getpView() {
		return pView;
	}

	public QRView getQrView() {
		return qrView;
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
