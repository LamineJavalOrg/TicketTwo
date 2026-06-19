package it.unipv.posw.view.admin;

import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author gpelle
 */

public class ArOrganizzatoreView extends VBox implements IView {

    private Label lblTitolo;

    private Label lblSezioneSede;
    private ComboBox<Sede> comboSede;
    private Button btnEliminaSede;
    private Label lblSediEs;
    private VBox listaSedi;
    private ScrollPane scrollSedi;

    private Label lblSezioneEvento;
    private ComboBox<Evento> comboEvento;
    private Button btnEliminaEvento;
    private Label lblEventiEs;
    private VBox listaEventi;
    private ScrollPane scrollEventi;

    private HBox bottoneFondoSede;
    private HBox bottoneFondoEvento;

    public ArOrganizzatoreView() {
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        lblTitolo = new Label("AREA RISERVATA");
        lblTitolo.getStyleClass().add("titolo");
        lblTitolo.setMaxWidth(Double.MAX_VALUE);
        lblTitolo.setAlignment(Pos.CENTER);


        lblSezioneSede = new Label("Elimina Sede");
        lblSezioneSede.getStyleClass().add("titolo");

        comboSede = new ComboBox<>();
        comboSede.setPromptText("Seleziona sede");
        comboSede.setPrefWidth(300);

        btnEliminaSede = new Button("Elimina Sede");
        btnEliminaSede.setPrefWidth(300);

        lblSediEs = new Label("Sedi esistenti:");
        listaSedi = new VBox(6);
        scrollSedi = new ScrollPane(listaSedi);
        scrollSedi.setFitToWidth(true);
        scrollSedi.setPrefHeight(120);

        bottoneFondoSede = new HBox(10, btnEliminaSede);
        bottoneFondoSede.setAlignment(Pos.CENTER);

        lblSezioneEvento = new Label("Elimina Evento");
        lblSezioneEvento.getStyleClass().add("titolo");

        comboEvento = new ComboBox<>();
        comboEvento.setPromptText("Seleziona evento");
        comboEvento.setPrefWidth(300);
        
        btnEliminaEvento = new Button("Elimina Evento");
        btnEliminaEvento.setPrefWidth(300);

        lblEventiEs = new Label("Eventi esistenti:");
        listaEventi = new VBox(6);
        scrollEventi = new ScrollPane(listaEventi);
        scrollEventi.setFitToWidth(true);
        scrollEventi.setPrefHeight(120);

        bottoneFondoEvento = new HBox(10, btnEliminaEvento);
        bottoneFondoEvento.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
                lblTitolo,
                lblSezioneSede,
                comboSede,
                lblSediEs,
                scrollSedi,
                bottoneFondoSede,
                lblSezioneEvento,
                comboEvento,
                lblEventiEs,
                scrollEventi,
                bottoneFondoEvento
        );
    }

    public Button getBtnEliminaSede() {
        return btnEliminaSede;
    }

    public Button getBtnEliminaEvento() {
        return btnEliminaEvento;
    }

    public ComboBox<Sede> getComboSede() {
        return comboSede;
    }

    public ComboBox<Evento> getComboEvento() {
        return comboEvento;
    }

    public Sede getSedeSelezionata() {
        return comboSede.getValue();
    }

    public Evento getEventoSelezionato() {
        return comboEvento.getValue();
    }

    public void popolaSedi(List<Sede> sedi) {
        comboSede.getItems().setAll(sedi);
        comboSede.setValue(null);

        listaSedi.getChildren().clear();
        for (Sede s : sedi) {
            listaSedi.getChildren().add(creaRigaSede(s));
        }
    }

    public void popolaEventi(List<Evento> eventi) {
        comboEvento.getItems().setAll(eventi);
        comboEvento.setValue(null);

        listaEventi.getChildren().clear();
        for (Evento e : eventi) {
            listaEventi.getChildren().add(creaRigaEvento(e));
        }
    }

    private Label creaRigaSede(Sede s) {
        return new Label(s.getNome() + " - " + s.getIndirizzo());
    }

    private Label creaRigaEvento(Evento e) {
        return new Label(e.getNome() + " - " + e.getTipo());
    }

    
    @Override
    public Node getNodo() {
        return this;
    }
}