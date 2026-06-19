package it.unipv.posw.view.home;

import it.unipv.posw.view.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * @author gpelle
 */
public class AutenticazioneOrgView extends VBox implements IView {
	
	private TextField txtEmail;
	private PasswordField txtPassword;
	private Button btnLogin;
	private Label lblTitolo;
	private Label lblEmail;
	private Label lblPassword;
	 
	 public AutenticazioneOrgView() {
		 
		 this.setPadding(new Insets(20));
	     this.setSpacing(10);
	     this.setAlignment(Pos.CENTER);
	     this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
	     this.getStyleClass().add("sfondopred");
	     
		 txtEmail = new TextField();
		 txtPassword = new PasswordField();
		 btnLogin = new Button("Login");
		 
		 txtEmail.setMaxWidth(300);
	     txtPassword.setMaxWidth(300);
	     btnLogin.setMinWidth(120);
	     
	     lblTitolo = new Label("Autenticazione organizzatore");
	     lblTitolo.getStyleClass().add("titolo");
	     lblEmail = new Label("Email:");
	     lblPassword = new Label("Password");
	        
		 this.getChildren().addAll(
			lblTitolo,
			lblEmail, txtEmail,
			lblPassword, txtPassword,
			btnLogin);
	 }

	 public TextField getTxtEmail() {
		 return txtEmail;
	 }

	 public PasswordField getTxtPassword() {
		 return txtPassword;
	 }

	 public Button getBtnLogin() {
		 return btnLogin;
	 }

	 @Override
	 public Node getNodo() {
		return this;
	 }
	 

	 
	 

}
