package it.unipv.posw.view.acquisto;

import it.unipv.posw.model.enums.PaymentType;
import it.unipv.posw.view.IView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author rkomi-dev
 */

public class PagamentoView extends VBox implements IView {
	
	private Label lblTotale;
	private Label lblRiepilogo;
    private Button btnConfermaAcquisto;
    private ComboBox<PaymentType> comboMetodo;

    public PagamentoView() {

        this.setSpacing(10);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");

        lblRiepilogo = new Label("Riepilogo Ordine:");
        this.getChildren().add(lblRiepilogo);


        lblTotale = new Label("Totale da pagare: € 0.00");
        this.getChildren().add(lblTotale);

        this.getChildren().add(new Label("Seleziona metodo di pagamento:"));
        
        comboMetodo = new ComboBox<>();
        comboMetodo.getItems().addAll(PaymentType.values());
        comboMetodo.setValue(PaymentType.PAYPAL);
        this.getChildren().add(comboMetodo);


        btnConfermaAcquisto = new Button("Acquista ora");
        this.getChildren().add(btnConfermaAcquisto);
    }


    
    public Label getLblTotale() {
		return lblTotale;
	}

	public void setTotale(double totale) {
        lblTotale.setText("Totale da pagare: € " + totale);
    }


	public ComboBox<PaymentType> getComboMetodo() {
		return comboMetodo;
	}

	public Button getBtnConfermaAcquisto() {
		return btnConfermaAcquisto;
	}



	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}

}
