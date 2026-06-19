package it.unipv.posw.view.home;

import java.util.HashMap;
import java.util.Map;

import it.unipv.posw.model.entities.RiepilogoAcquisto;
import it.unipv.posw.view.IView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/** 
 * @author rkomi-dev
 */

public class AreaClienteView extends VBox implements IView {
	
    private Label titolo;
    private VBox listaContainer;
    private Map<RiepilogoAcquisto, TextField> mappaCampiTesto;
    
    public AreaClienteView() {

    	
    	this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        
        titolo = new Label("I Tuoi Biglietti Acquistati");
        titolo.getStyleClass().add("titolo");
        
        listaContainer = new VBox(10);
        
        mappaCampiTesto = new HashMap<>();
        this.getChildren().addAll(titolo, listaContainer);
    }
    
    public Button aggiungiBigliettoAllaLista(RiepilogoAcquisto b) {
        HBox riga = new HBox(15);
        riga.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(5);
        String testo = String.format("%s | Data: %s | Intestato a: %s | €%.2f",
                b.getNomeEvento(),
                b.getDataOraEvento().toLocalDate(), 
                b.getNominativo(),
                b.getPrezzo()
        );
        info.getChildren().addAll(new Label(testo));
        HBox.setHgrow(info, Priority.ALWAYS);
        
        TextField txtNominativo = new TextField();
        mappaCampiTesto.put(b, txtNominativo);
        
        Button btnCambio = new Button("Cambia Nominativo");
        
        riga.getChildren().addAll(info, txtNominativo, btnCambio);
        listaContainer.getChildren().add(riga); 

        return btnCambio; 
    }
    
	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}

	public Map<RiepilogoAcquisto, TextField> getMappaCampiTesto() {
		return mappaCampiTesto;
	}
	
	public void pulisciListaBiglietti() {
	    listaContainer.getChildren().clear();
	    mappaCampiTesto.clear();
	}
	
}
