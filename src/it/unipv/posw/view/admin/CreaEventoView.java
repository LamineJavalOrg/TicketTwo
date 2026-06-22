package it.unipv.posw.view.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class CreaEventoView extends VBox implements IView {
	private Label lblTitolo;
	
    private GridPane gridInfoBase;
    private Label lblEvento;
    private TextField txtNomeEvento;
    private Label lblArtista;
    private TextField txtArtista;
    private Label lblTipoEvento;
    private ComboBox<TipologiaEvento> comboTipoEvento;

    private GridPane gridTappa;
    private Label lblSede;
    private ComboBox<Sede> comboSede;
    private Label lblData;
    private DatePicker datePicker;
    private Spinner<Integer> spinnerOra;
    private Spinner<Integer> spinnerMinuti;
    private HBox boxOrario;
    private Label lblOra;
    private Label lblMinuti;

    private Button btnAggiungiTappa;
    private Button btnCreaEvento;

    private VBox listaPiantina;
    private ScrollPane scrollPiantina;
    private VBox listaRiepilogo;
    private ScrollPane scrollRiepilogo;

    private Label lblSezione1;
    private Label lblSezione2;
    private Label lblSezionePrezzi;
    private Label lblSezioneRiepilogo;
    
    private HBox bottoneFondo;

    private Map<Settore, ComboBox<TipologiaBiglietto>> comboTipiBiglietto = new LinkedHashMap<>();
    private Map<Settore, TextField> campiPrezzo = new LinkedHashMap<>();
    private Map<Settore, Spinner<Integer>> spinnerQuantita = new LinkedHashMap<>();

    public CreaEventoView() {
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        lblTitolo = new Label("CREA EVENTO MULTI-DATA");
        lblTitolo.getStyleClass().add("titolo");
        lblTitolo.setMaxWidth(Double.MAX_VALUE);
        lblTitolo.setAlignment(Pos.CENTER); 
        
        lblEvento = new Label("Nome evento:");
        txtNomeEvento = new TextField();
        txtNomeEvento.setPromptText("Inserisci nome evento");

        lblArtista = new Label("Nome artista:");
        txtArtista = new TextField();
        txtArtista.setPromptText("Inserisci nome artista");

        lblTipoEvento = new Label("Tipologia evento:");
        comboTipoEvento = new ComboBox<>();
        comboTipoEvento.getItems().addAll(TipologiaEvento.values());
        comboTipoEvento.setValue(TipologiaEvento.CONCERTO);

        lblSede = new Label("Sede:");
        comboSede = new ComboBox<>();
        comboSede.setPromptText("Seleziona sede");

        lblData = new Label("Data:");
        datePicker = new DatePicker();
        lblOra = new Label("Ora:");
        lblMinuti = new Label("Minuti:");
        spinnerOra = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 20));
        spinnerMinuti = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spinnerOra.setEditable(true);
        spinnerMinuti.setEditable(true);

        btnAggiungiTappa = new Button("Aggiungi Tappa al Tour");
        
        btnCreaEvento = new Button("Pubblica Evento / Tour");
        btnCreaEvento.setPrefWidth(300);

        listaPiantina = new VBox(10);
        scrollPiantina = new ScrollPane(listaPiantina);
        scrollPiantina.setFitToWidth(true);
        scrollPiantina.setPrefHeight(260);

        listaRiepilogo = new VBox(6);
        scrollRiepilogo = new ScrollPane(listaRiepilogo);
        scrollRiepilogo.setFitToWidth(true);
        scrollRiepilogo.setPrefHeight(120);

        lblSezione1 = new Label("Dati Generali");
        lblSezione1.getStyleClass().add("titolo");

        lblSezione2 = new Label("Configura Tappa (Sede e Data)");
        lblSezione2.getStyleClass().add("titolo");

        lblSezionePrezzi = new Label("Prezzi per i settori della sede selezionata");
        lblSezionePrezzi.getStyleClass().add("titolo");

        lblSezioneRiepilogo = new Label("Riepilogo Tour");
        lblSezioneRiepilogo.getStyleClass().add("titolo");

        boxOrario = new HBox(10,
                lblOra, spinnerOra,
                lblMinuti, spinnerMinuti);
        boxOrario.setAlignment(Pos.CENTER_LEFT);

        gridInfoBase = new GridPane();
        gridInfoBase.setHgap(10);
        gridInfoBase.setVgap(8);
        gridInfoBase.add(lblEvento, 0, 0);      gridInfoBase.add(txtNomeEvento, 1, 0);
        gridInfoBase.add(lblArtista, 0, 1);     gridInfoBase.add(txtArtista, 1, 1);
        gridInfoBase.add(lblTipoEvento, 0, 2);  gridInfoBase.add(comboTipoEvento, 1, 2);;

        gridTappa = new GridPane();
        gridTappa.setHgap(10);
        gridTappa.setVgap(8);
        gridTappa.add(lblSede, 0, 0);           gridTappa.add(comboSede, 1, 0);
        gridTappa.add(lblData, 0, 1);           gridTappa.add(datePicker, 1, 1);
        gridTappa.add(new Label("Orario:"), 0, 2); gridTappa.add(boxOrario, 1, 2);
        
        bottoneFondo = new HBox(10, btnCreaEvento);
        bottoneFondo.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		lblTitolo,
                lblSezione1,
                gridInfoBase,
                lblSezione2,
                gridTappa,
                btnAggiungiTappa,
                lblSezionePrezzi, 
                scrollPiantina,
                lblSezioneRiepilogo, 
                scrollRiepilogo,
                bottoneFondo
                );
    }


    
    public Button getBtnAggiungiTappa() {
        return btnAggiungiTappa;
    }

    public Button getBtnCreaEvento() {
        return btnCreaEvento;
    }

    public ComboBox<Sede> getComboSede() {
        return comboSede;
    }

    public String getNomeEvento() {
        return txtNomeEvento.getText().trim();
    }

    public String getNomeArtista() {
        return txtArtista.getText().trim();
    }

    public TipologiaEvento getTipoEvento() {
        return comboTipoEvento.getValue();
    }

    public Sede getSedeSelezionata() {
        return comboSede.getValue();
    }

    public LocalDate getData() {
        return datePicker.getValue();
    }

    public int getOra() {
        return spinnerOra.getValue();
    }

    public int getMinuti() {
        return spinnerMinuti.getValue();
    }

    // Restituisce i settori per cui è stata generata una riga di configurazione tariffa.
    public List<Settore> getSettoriConfigurati() {
        return new ArrayList<>(comboTipiBiglietto.keySet());
    }

    // Restituisce la tipologia di biglietto scelta per il settore indicato.
    public TipologiaBiglietto getTipoBiglietto(Settore s) {
        return comboTipiBiglietto.get(s).getValue();
    }

    // Restituisce il prezzo inserito per il settore indicato.
    public String getPrezzo(Settore s) {
        return campiPrezzo.get(s).getText().trim();
    }

    // Restituisce la quantità inserita per il settore indicato.
    public int getQuantita(Settore s) {
        return spinnerQuantita.get(s).getValue();
    }


    // Popola la combo delle sedi, selezionando la prima se disponibile.
    public void popolaSedi(List<Sede> sedi) {
        comboSede.getItems().setAll(sedi);
        if (!sedi.isEmpty()) {
            comboSede.setValue(sedi.get(0));
        }
    }

    public void aggiornaPiantina(List<Settore> settori) {
        listaPiantina.getChildren().clear();
        comboTipiBiglietto.clear();
        campiPrezzo.clear();
        spinnerQuantita.clear();
        for (Settore s : settori) {
            listaPiantina.getChildren().add(creaRigaSettore(s));
        }
    }

    public void aggiungiRigaRiepilogo(String testo) {
        listaRiepilogo.getChildren().add(new Label(testo));
    }

    public void resetForm() {
        txtNomeEvento.clear();
        txtArtista.clear();
        comboTipoEvento.setValue(TipologiaEvento.CONCERTO);
        datePicker.setValue(null);
        spinnerOra.getValueFactory().setValue(20);
        spinnerMinuti.getValueFactory().setValue(0);
        listaRiepilogo.getChildren().clear();
    }

    // Crea la riga di configurazione tariffa per un settore (tipologia, prezzo, quantità)
    private VBox creaRigaSettore(Settore s) {
        Label lblNome = new Label(s.getNome_settore() + " (" + s.getPrefisso() + ")");
        lblNome.getStyleClass().add("titolo");

        ComboBox<TipologiaBiglietto> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll(TipologiaBiglietto.values());
        comboTipo.setPromptText("Seleziona tipo");

        TextField txtPrezzo = new TextField();
        txtPrezzo.setPromptText("Inserisci prezzo");
        txtPrezzo.setMaxWidth(120);

        int capienzaMax = s.getCapienza_max();
        Spinner<Integer> spinnerQta =
                new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, capienzaMax, 1));
        spinnerQta.setEditable(true);
        spinnerQta.setMaxWidth(120);

        Label lblMax = new Label("(Max: " + capienzaMax + ")");

        comboTipiBiglietto.put(s, comboTipo);
        campiPrezzo.put(s, txtPrezzo);
        spinnerQuantita.put(s, spinnerQta);

        HBox riga = new HBox(10,
                new Label("Tipo:"), comboTipo,
                new Label("Prezzo:"), txtPrezzo,
                new Label("Quantità"), spinnerQta, lblMax);
        riga.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(6, lblNome, riga);
        box.setPadding(new Insets(10));
        return box;
    }

    
    @Override
    public Node getNodo() {
        return this;
    }
}