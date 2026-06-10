package it.unipv.posw.view.home;

import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PannelloAdminView extends VBox implements IView {
	private Label lblBenvenuto;
	private Button btnConfiguraSede;
    private Button btnCreaEvento;
    
    public PannelloAdminView() {
    	
    	this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        
        lblBenvenuto = new Label("Area Organizzatore");
        btnConfiguraSede = new Button("Configura Nuova Sede");
        btnCreaEvento = new Button("Crea Nuovo Evento");
        
        this.getChildren().addAll(
        		lblBenvenuto,
        		btnConfiguraSede,
        		btnCreaEvento
        		);
	}

    public void setTestoBenvenuto(String testo) {
        this.lblBenvenuto.setText(testo);
    }
    
	public Button getBtnConfiguraSede() {
		return btnConfiguraSede;
	}

	public Button getBtnCreaEvento() {
		return btnCreaEvento;
	}

	@Override
	public Node getNodo() {
		return this;
	}

}
