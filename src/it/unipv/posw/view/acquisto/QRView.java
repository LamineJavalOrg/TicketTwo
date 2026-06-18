package it.unipv.posw.view.acquisto;

import it.unipv.posw.view.IView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class QRView extends VBox implements IView {
    
    private Label lblCodiceQR;

    public QRView() {
     
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        
        this.setSpacing(15); 
        this.setAlignment(Pos.CENTER);

        
        Label lblSuccesso = new Label("Acquisto andato a buon fine!");
        

        Pane qrPane = new Pane();
        qrPane.getStyleClass().add("qr");
        

        lblCodiceQR = new Label("Codice: ----");

        this.getChildren().addAll(lblSuccesso, qrPane, lblCodiceQR);
    }

    public void setCodiceTestuale(String codice) {
        this.lblCodiceQR.setText("Codice: " + codice);
    }

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
}
