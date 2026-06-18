package it.unipv.posw.view.acquisto;


import java.util.List;

import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.view.IView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EventoView extends VBox implements IView {
 
	private VBox infoBox;
    private Label lblNome;
    private Label lblTipoEvento;
    private HBox boxTappe;
    private Label lblSceltaTappa;
    private ComboBox<Tappa> comboTappe;
    
    private VBox actionBox;
    private Label lblSettori;
    private ComboBox<Settore> comboSettori;
    private HBox boxSettore;
    
    private Label lblTipologiaTitolo;
    private Label lblTipologiaValore;
    private HBox boxTipologia;
    
    private Label lblQuantita;
    private ComboBox<Integer> comboQuantita;
    private HBox boxQuantita;
 
    private Label lblPrezzoBase;
    private Label lblPrezzoScontato;
    private Label lblDescrizioneSconto;
    
    private BorderPane contenuto;
 
    private Button btnAggiungi;
    private Button btnCarrello;
 
    public EventoView() {
        this.setPadding(new Insets(30));
        this.setSpacing(15);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
 
        
        infoBox = new VBox(12);
        lblNome = new Label();
        lblNome.getStyleClass().add("titolo");
        lblTipoEvento = new Label();
        lblTipoEvento.getStyleClass().add("sottotitolo");
        
        lblSceltaTappa = new Label("Seleziona data e sede:");
        comboTappe = new ComboBox<>();
        boxTappe = new HBox(15);
        boxTappe.setAlignment(Pos.CENTER_LEFT);
        boxTappe.getChildren().addAll(lblSceltaTappa, comboTappe);
        infoBox.getChildren().addAll(lblNome, lblTipoEvento, boxTappe);
      
 
        actionBox = new VBox(20);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setMinWidth(220);
 
        lblSettori = new Label("Settore:");
        comboSettori = new ComboBox<>();
        comboSettori.setPromptText("Scegli settore");
        comboSettori.setMinWidth(200);
        boxSettore = new HBox(15);
        boxSettore.setAlignment(Pos.CENTER_RIGHT);
        boxSettore.getChildren().addAll(lblSettori, comboSettori);
 
        lblTipologiaTitolo = new Label("Tipologia:");
        lblTipologiaValore = new Label("-");
        boxTipologia = new HBox(15); 
        boxTipologia.setAlignment(Pos.CENTER_RIGHT); 
        boxTipologia.setMinWidth(200);
        boxTipologia.getChildren().addAll(lblTipologiaTitolo, lblTipologiaValore);
 
        lblQuantita = new Label("Quantità:");
        comboQuantita = new ComboBox<>();
        comboQuantita.setMinWidth(200);
        comboQuantita.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        comboQuantita.getSelectionModel().selectFirst();
        boxQuantita = new HBox(15);
        boxQuantita.setAlignment(Pos.CENTER_RIGHT);
        boxQuantita.getChildren().addAll(lblQuantita, comboQuantita);
 
        lblDescrizioneSconto = new Label("");
        lblDescrizioneSconto.setVisible(false);
        lblDescrizioneSconto.setManaged(false);
 
        lblPrezzoBase = new Label("Prezzo: -- €");
 
        lblPrezzoScontato = new Label("");
        lblPrezzoScontato.setVisible(false);
        lblPrezzoScontato.setManaged(false);
        lblPrezzoScontato.getStyleClass().add("prezzoscontato"); 
   
        btnAggiungi = new Button("Aggiungi al carrello");
        btnAggiungi.setMinWidth(200);
 
        btnCarrello = new Button("Vai al carrello");
        btnCarrello.setMinWidth(200);
 
        actionBox.getChildren().addAll(
            boxSettore,
            boxTipologia,
            boxQuantita,
            lblPrezzoBase,
            lblPrezzoScontato,
            lblDescrizioneSconto,
            btnAggiungi, btnCarrello
        );
 
        contenuto = new BorderPane();
        contenuto.setTop(infoBox);
        contenuto.setRight(actionBox);
        VBox.setVgrow(contenuto, Priority.ALWAYS);

        this.getChildren().add(contenuto);
    }
 
    

    public void setTitolo(String nome) {
        this.lblNome.setText(nome);
    }
 
    public void setTipoEvento(String testo) {
        this.lblTipoEvento.setText(testo);
    }
    
    public void popolaTappe(List<Tappa> tappe) {
        if (tappe != null && !tappe.isEmpty()) {
            comboTappe.setItems(FXCollections.observableArrayList(tappe));
            comboTappe.getSelectionModel().selectFirst();
        }
    }
    
    public void setTipologiaValore(String testo) {
        this.lblTipologiaValore.setText(testo);
    }
    
    public void setPrezzoBase(double valore) {
        this.lblPrezzoBase.setText(String.format("Prezzo base: %.2f €", valore));
    }
 
    public void setPrezzoScontato(double valore) {
        this.lblPrezzoScontato.setText(String.format("Prezzo scontato: %.2f €", valore));
 
        this.lblDescrizioneSconto.setVisible(true);
        this.lblDescrizioneSconto.setManaged(true);
 
        this.lblPrezzoScontato.setVisible(true);
        this.lblPrezzoScontato.setManaged(true);
    }
 
    public void nascondiAreaSconto() {
        this.lblPrezzoScontato.setVisible(false);
        this.lblPrezzoScontato.setManaged(false);
 
        this.lblDescrizioneSconto.setVisible(false);
        this.lblDescrizioneSconto.setManaged(false);
    }
 

    
    public ComboBox<Tappa> getComboTappe() { 
    	return comboTappe; 
    }
    public ComboBox<Settore> getComboSettori() { 
    	return comboSettori; 
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
 
    
    
    public Label getLblTipologiaValore() {
		return lblTipologiaValore;
	}



	@Override
    public Node getNodo() {
        return this;
    }
}
