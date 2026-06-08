package it.unipv.posw.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author gpelle
 */
public class AlertView {
	public static void mostraErrore(String messaggio) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
