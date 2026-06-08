package it.unipv.posw.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrazioneView extends VBox implements IView {
    
    private TextField txtNome;
    private TextField txtCognome;
    private TextField txtEmail;
    private TextField txtOrganizzazione;
    private PasswordField txtPassword;
    private DatePicker dateNascita;
    private Button btnRegistratiC;
    private Button btnRegistratiR;

    public RegistrazioneView() {
    	
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("sfondopred");
        
    	txtNome = new TextField();
    	txtCognome = new TextField();
    	txtEmail = new TextField();
    	txtOrganizzazione = new TextField();
    	txtPassword = new PasswordField();
    	dateNascita = new DatePicker();
    	btnRegistratiC = new Button("Registrati come cliente");
    	btnRegistratiR = new Button("Registrati come organizzatore");
    	
    	
	    btnRegistratiC.setMinWidth(120);
	    btnRegistratiR.setMinWidth(120);
    	
        this.getChildren().addAll(
            new Label("Nome:"), txtNome,
            new Label("Cognome:"), txtCognome,
            new Label("Email:"), txtEmail,
            new Label("Password:"), txtPassword,
            new Label("Data di Nascita:"), dateNascita,
            new Label("Nome organizzazione (solo per organizzatori):"), txtOrganizzazione,
            btnRegistratiC, btnRegistratiR
        );
        
    }

	public TextField getTxtNome() {
		return txtNome;
	}

	public TextField getTxtCognome() {
		return txtCognome;
	}

	public TextField getTxtEmail() {
		return txtEmail;
	}

	public TextField getTxtOrganizzazione() {
		return txtOrganizzazione;
	}

	public PasswordField getTxtPassword() {
		return txtPassword;
	}

	public DatePicker getDateNascita() {
		return dateNascita;
	}

	public Button getBtnRegistratiC() {
		return btnRegistratiC;
	}

	public Button getBtnRegistratiR() {
		return btnRegistratiR;
	}

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}
    
    
}