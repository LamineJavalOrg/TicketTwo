package it.unipv.posw.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	
	public static void mostraInfo(String messaggio) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Operazione completata");
	    alert.setHeaderText(null);
	    alert.setContentText(messaggio);
	    alert.showAndWait();
	}
	
	public static boolean mostraConferma(String messaggio) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conferma Operazione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);

        // Optional gestisce il fatto che l'utente potrebbe chiudere la finestra senza cliccare nulla
        Optional<ButtonType> result = alert.showAndWait();
        
        // Ritorna true solo se l'utente ha cliccato il tasto OK
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
