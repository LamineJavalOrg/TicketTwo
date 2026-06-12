package it.unipv.posw.view.acquisto;


import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author rkomi-dev
 */

public class EventiPerArtistaView extends VBox implements IView {

    private VBox listaContainer;
    private Label lblTitolo;
    private ScrollPane scrollPane;

    public EventiPerArtistaView() {
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");

        lblTitolo = new Label("Eventi per: ");
        

        listaContainer = new VBox(10);
        
        scrollPane = new ScrollPane(listaContainer);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        this.getChildren().addAll(lblTitolo, scrollPane);
    }

    public Button aggiungiEventoAllaLista(Evento e) {
        HBox riga = new HBox(15);
        riga.setAlignment(Pos.CENTER_LEFT);
        

        VBox info = new VBox(5);
        info.getChildren().addAll(new Label(e.getNome()));
        HBox.setHgrow(info, Priority.ALWAYS);


        Button btnVedi = new Button("Visualizza Dettaglio");
        
        riga.getChildren().addAll(info, btnVedi);
        listaContainer.getChildren().add(riga);

        return btnVedi; 
    }

	
	public Label getLblTitolo() {
		return lblTitolo;
	}

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
}
  