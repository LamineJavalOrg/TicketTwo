package it.unipv.posw.view.acquisto;

import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/** 
 * @author rkomi-dev
 */

public class CarrelloView extends VBox implements IView {
   
    private VBox listaProdotti; 
    private Label lblTotale;
    private Button btnPaga;
    private Label lblTitolo;
    private ScrollPane scroll;

    public CarrelloView() {
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");

        lblTitolo = new Label("Il Tuo Carrello");
        
        scroll = new ScrollPane();
        listaProdotti = new VBox(10); 
        scroll.setContent(listaProdotti);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(300);

        lblTotale = new Label("Totale: 0.00€");
 
        
        btnPaga = new Button("Vai al pagamento");
        
        lblTitolo.getStyleClass().add("titolo");
        lblTotale.getStyleClass().add("titolo");
        
        this.getChildren().addAll(lblTitolo, scroll, lblTotale, btnPaga);
    }

    
    public void aggiungiRigaBiglietto(String testo) {
        Label riga = new Label(testo);
        
        listaProdotti.getChildren().add(riga);
    }

    public void svuotaVista() {
        listaProdotti.getChildren().clear();
    }

    public Button getBtnPaga() { 
    	return btnPaga; 
    }
    
    public void setTotale(String totale) { 
    	lblTotale.setText(totale); 
    }


	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
}