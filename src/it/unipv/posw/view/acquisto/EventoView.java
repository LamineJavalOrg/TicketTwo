package it.unipv.posw.view.acquisto;


import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventoView extends VBox implements IView {
 
	private VBox infoBox;
    private Label lblNome;
    private Label lblSceltaTappa;
    private ComboBox<Tappa> comboTappe;
    
    private VBox actionBox;
    private Label lblSettori;
    private ComboBox<Settore> comboSettori;
    private Label lblTipologia;
    private ComboBox<TipologiaBiglietto> comboBiglietti;
    private Label lblQuantita;
    private ComboBox<Integer> comboQuantita;
 
    private Label lblPrezzoBase;
    private Label lblPrezzoScontato;
    private Label lblPrezzoTotale;
    private Label lblDisponibilita;
    private Label lblDescrizioneSconto;
    
    private HBox contenuto;
 
    private Button btnAggiungi;
    private Button btnCarrello;
 
    public EventoView() {
        this.setPadding(new Insets(10));
        this.setSpacing(15);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
 
        
        infoBox = new VBox(8);
        lblNome = new Label();
        lblSceltaTappa = new Label("Seleziona data e sede:");
        comboTappe = new ComboBox<>();
 
        infoBox.getChildren().addAll(lblNome, lblSceltaTappa, comboTappe);
      
 
        actionBox = new VBox(8);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setMinWidth(220);
 
        lblSettori = new Label("Settore:");
        comboSettori = new ComboBox<>();
        comboSettori.setPromptText("Scegli settore");
        comboSettori.setMinWidth(200);
 
        lblTipologia = new Label("Tipologia:");
        comboBiglietti = new ComboBox<>();
        comboBiglietti.setMinWidth(200);
 
        lblQuantita = new Label("Quantità:");
        comboQuantita = new ComboBox<>();
        comboQuantita.setMinWidth(200);
        comboQuantita.setPromptText("");
 
        lblDescrizioneSconto = new Label("");
        lblDescrizioneSconto.setVisible(false);
        lblDescrizioneSconto.setManaged(false);
 
        lblPrezzoBase = new Label("Prezzo: -- €");
 
        lblPrezzoScontato = new Label("");
        lblPrezzoScontato.setVisible(false);
        lblPrezzoScontato.setManaged(false);
 
        lblPrezzoTotale = new Label("TOTALE: -- €");
 
        lblDisponibilita = new Label("Disponibilità: --");
 
        btnAggiungi = new Button("Aggiungi al carrello");
        btnAggiungi.setMinWidth(200);
 
        btnCarrello = new Button("Vai al carrello");
        btnCarrello.setMinWidth(200);
 
        actionBox.getChildren().addAll(
            lblSettori, comboSettori,
            lblTipologia, comboBiglietti,
            lblQuantita, comboQuantita,
            lblPrezzoBase,
            lblPrezzoScontato,
            lblDescrizioneSconto,
            lblPrezzoTotale,
            lblDisponibilita,
            btnAggiungi, btnCarrello
        );
 

        contenuto = new HBox(15, infoBox, actionBox);
        contenuto.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(contenuto);
    }
 
    
  

    public ComboBox<Tappa> getComboTappe() { 
    	return comboTappe; 
    }
    public ComboBox<Settore> getComboSettori() { 
    	return comboSettori; 
    }
    public ComboBox<TipologiaBiglietto> getComboBiglietti() { 
    	return comboBiglietti; 
    }
    public ComboBox<Integer> getComboQuantita() { 
    	return comboQuantita; 
    }
    public Button getBtnAggiungi() { 
    	return btnAggiungi; 
    }
    public Button getBtnCarrello() { 
    	return btnCarrello; 
    }
 
    
    
    @Override
    public Node getNodo() {
        return this;
    }
}
