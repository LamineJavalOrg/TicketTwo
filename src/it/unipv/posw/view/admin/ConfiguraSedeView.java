package it.unipv.posw.view.admin;

import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author gpelle
 */

public class ConfiguraSedeView extends VBox implements IView {

	private Label lblTitolo;
	
    private Button btnAggiungiSettore;
    private Button btnConferma;

    private ComboBox<TipologiaSettore> comboNomeSettore;
    private ComboBox<TipologiaPosto> comboTipoPosto;
    private TextField txtPrefisso;
    private Spinner<Integer> spinnerFile;
    private Spinner<Integer> spinnerColonne;
    private Spinner<Integer> spinnerCapienza;

    private TextField txtNomeSede;
    private TextField txtIndirizzo;
    private VBox listaSettori;
    private VBox listaSedi;
    private TextField txtCapienzaTotale;
    
    private ScrollPane scrollSettori;
    private ScrollPane scrollSedi;
    
    private Label lblSezione1;
    private GridPane gridSettore;
    private Label lblSettAgg;
    
    private Label lblSezione2;
    private GridPane gridSede;
    private Label lblSediEs;
    
    private HBox bottoneFondo;

    public ConfiguraSedeView() {
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        lblTitolo = new Label("CONFIGURA NUOVA SEDE");
        lblTitolo.getStyleClass().add("titolo");
        lblTitolo.setMaxWidth(Double.MAX_VALUE);
        lblTitolo.setAlignment(Pos.CENTER); 
        
        btnAggiungiSettore = new Button("Aggiungi Settore");
        
        btnConferma = new Button("Salva Sede");
        btnConferma.setPrefWidth(300);

        comboNomeSettore = new ComboBox<>();
        comboNomeSettore.getItems().addAll(TipologiaSettore.values());
        comboNomeSettore.setPromptText("Seleziona settore");

        comboTipoPosto = new ComboBox<>();
        comboTipoPosto.getItems().addAll(TipologiaPosto.values());
        comboTipoPosto.setValue(TipologiaPosto.NUMERATO);

        txtPrefisso = new TextField();
        txtPrefisso.setPromptText("es. PL, T1, CN");

        spinnerFile = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spinnerColonne = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spinnerCapienza = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1));
        spinnerFile.setEditable(true);
        spinnerColonne.setEditable(true);
        spinnerCapienza.setEditable(true);

        txtNomeSede = new TextField();
        txtNomeSede.setPromptText("Inserisci nome sede");

        txtIndirizzo = new TextField();
        txtIndirizzo.setPromptText("Inserisci indirizzo");

        listaSettori = new VBox(6);
        scrollSettori = new ScrollPane(listaSettori);
        scrollSettori.setFitToWidth(true);
        scrollSettori.setPrefHeight(120);

        listaSedi = new VBox(6);
        scrollSedi = new ScrollPane(listaSedi);
        scrollSedi.setFitToWidth(true);
        scrollSedi.setPrefHeight(120);

        txtCapienzaTotale = new TextField();
        txtCapienzaTotale.setEditable(false);
        txtCapienzaTotale.setPromptText("0");

        lblSezione1 = new Label("Aggiungi Settore");
        lblSezione1.getStyleClass().add("titolo");
        gridSettore = new GridPane();
        gridSettore.setHgap(10);
        gridSettore.setVgap(8);
        gridSettore.add(new Label("Tipo settore:"), 0, 0); gridSettore.add(comboNomeSettore, 1, 0);
        gridSettore.add(new Label("Tipo posti:"), 0, 1); gridSettore.add(comboTipoPosto, 1, 1);
        gridSettore.add(new Label("Prefisso:"), 0, 2); gridSettore.add(txtPrefisso, 1, 2);
        gridSettore.add(new Label("File:"), 0, 3); gridSettore.add(spinnerFile, 1, 3);
        gridSettore.add(new Label("Colonne:"), 0, 4); gridSettore.add(spinnerColonne, 1, 4);
        gridSettore.add(new Label("Capienza (in piedi):"), 0, 5); gridSettore.add(spinnerCapienza, 1, 5);
        lblSettAgg = new Label("Settori aggiunti:");

        lblSezione2 = new Label("Dati Sede");
        lblSezione2.getStyleClass().add("titolo");
        gridSede = new GridPane();
        gridSede.setHgap(10);
        gridSede.setVgap(8);
        gridSede.add(new Label("Nome sede:"), 0, 0); gridSede.add(txtNomeSede, 1, 0);
        gridSede.add(new Label("Indirizzo:"), 0, 1); gridSede.add(txtIndirizzo, 1, 1);
        gridSede.add(new Label("Capienza tot:"), 0, 2); gridSede.add(txtCapienzaTotale, 1, 2);
        lblSediEs = new Label("Sedi esistenti:");
        
        bottoneFondo = new HBox(10, btnConferma);
        bottoneFondo.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		lblTitolo,
        		lblSezione1,
        		gridSettore,
            	lblSettAgg,
	            scrollSettori,
	            btnAggiungiSettore,
	            lblSezione2,
	            gridSede,
	            lblSediEs,
	            scrollSedi,
	            bottoneFondo
	            );
    }

    

    
    public Button getBtnAggiungiSettore() { 
    	return btnAggiungiSettore; 
    }
    public Button getBtnConferma() { 
    	return btnConferma; 
    }
    public ComboBox<TipologiaPosto> getComboTipoPosto() { 
    	return comboTipoPosto; 
    }
    public ComboBox<TipologiaSettore> getComboNomeSettore() { 
    	return comboNomeSettore; 
    }
    public TipologiaSettore getNomeSettoreSelezionato() { 
    	return comboNomeSettore.getValue(); 
    }
    public TipologiaPosto getTipoPostiSelezionato() { 
    	return comboTipoPosto.getValue(); 
    }
    public String getPrefisso() { 
    	return txtPrefisso.getText().trim(); 
    }
    public int getNumFile() { 
    	return spinnerFile.getValue(); 
    }
    public int getNumColonne() { 
    	return spinnerColonne.getValue(); 
    }
    public int getCapienza() { 
    	return spinnerCapienza.getValue();
    }
    public String getNomeSede() { 
    	return txtNomeSede.getText().trim();
    }
    public String getIndirizzo() { 
    	return txtIndirizzo.getText().trim(); 
    }

   
    
    
    public void aggiornaSediEsistenti(List<Sede> sedi) {
        listaSedi.getChildren().clear();
        for (Sede s : sedi) {
            listaSedi.getChildren().add(creaRigaSede(s));
        }
    }

    public void aggiornaSettoriInAttesa(List<Settore> settori) {
        listaSettori.getChildren().clear();
        for (Settore s : settori) {
            listaSettori.getChildren().add(creaRigaSettore(s));
        }
    }

    public void aggiornaCapienzaTotale(int capienza) {
        txtCapienzaTotale.setText(String.valueOf(capienza));
    }

    public void resetCampiSettore() {
        comboNomeSettore.setValue(null);
        comboTipoPosto.setValue(TipologiaPosto.NUMERATO);
        txtPrefisso.clear();
        spinnerFile.getValueFactory().setValue(1);
        spinnerColonne.getValueFactory().setValue(1);
        spinnerCapienza.getValueFactory().setValue(1);
    }

    public void resetFormSede() {
        txtNomeSede.clear();
        txtIndirizzo.clear();
        listaSettori.getChildren().clear();
        txtCapienzaTotale.clear();
    }

    

    
    public void aggiornaCampiPerTipoPosto(TipologiaPosto tipo) {
        boolean isNumerato = TipologiaPosto.NUMERATO == tipo;
        spinnerFile.setDisable(!isNumerato);
        spinnerColonne.setDisable(!isNumerato);
        spinnerCapienza.setDisable(isNumerato);
    }

    public void bloccaTipoPosto(TipologiaPosto tipo) {
        comboTipoPosto.setValue(tipo);
        comboTipoPosto.setDisable(true);
    }

    public void sbloccaTipoPosto() {
        comboTipoPosto.setDisable(false);
    }

    private Label creaRigaSettore(Settore s) {
        Label rigaSett = new Label(descriviSettore(s));
        return rigaSett;
    }

    private Label creaRigaSede(Sede s) {
        Label rigaSede = new Label(s.getNome() + " - " + s.getIndirizzo());
        return rigaSede;
    }

    private static String descriviSettore(Settore s) {
        if (s.getTipo() == TipologiaPosto.NUMERATO) {
            return s.getNome_settore() + " [" + s.getPrefisso() + "] · Numerato "
                    + s.getNum_file() + "×" + s.getPosti_per_fila()
                    + " (" + s.getCapienza_max() + " posti)";
        }
        return s.getNome_settore() + " [" + s.getPrefisso() + "] · In piedi ("
                + s.getCapienza_max() + " posti)";
    }

    
    
    @Override
    public Node getNodo() {
        return this;
    }
}