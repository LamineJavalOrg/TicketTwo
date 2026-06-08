package it.unipv.posw.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AutenticazioneView extends VBox implements IView {
	
	 private TextField txtEmail;
	 private PasswordField txtPassword;
	 private Button btnLogin;
	 
	 public AutenticazioneView() {
		 
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
	     
		 this.getChildren().addAll(
			new Label("Autenticazione"),
			new Label("Email:"), txtEmail,
			new Label("Password"), txtPassword,
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
