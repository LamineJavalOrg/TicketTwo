package it.unipv.posw.view.home;

import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author gpelle
 */
public class PannelloAdminView extends VBox implements IView {
	private Label lblBenvenuto;
	private Button btnConfiguraSede;
    private Button btnCreaEvento;
    private Button btnArOrg;
    
    public PannelloAdminView() {
    	
    	this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        
        lblBenvenuto = new Label("Area Organizzatore");
        lblBenvenuto.getStyleClass().add("titolo");
        btnConfiguraSede = new Button("Configura Nuova Sede");
        btnCreaEvento = new Button("Crea Nuovo Evento");
        btnArOrg = new Button("Area riservata");
        
        this.getChildren().addAll(
        		lblBenvenuto,
        		btnConfiguraSede,
        		btnCreaEvento,
        		btnArOrg
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
	
	public Button getBtnArOrg() {
		return btnArOrg;
	}

	
	@Override
	public Node getNodo() {
		return this;
	}

}
