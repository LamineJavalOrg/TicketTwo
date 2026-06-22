package it.unipv.posw.view.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Classe di utilità preposta alla gestione e alla visualizzazione delle 
 * finestre di avviso (errore, informazione, conferma).
 * @author gpelle
 */
public class AlertView {
	
	/**
	 * Visualizza una finestra di avviso di errore.
     * Interrompe temporaneamente l'interfaccia grafica costringendo l'utente a prendere visione
     * del messaggio di fallimento prima di poter riprendere l'interazione con l'applicazione.
	 * @param messaggio Il testo descrittivo dell'errore da mostrare all'utente.
	 */
	public static void mostraErrore(String messaggio) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
	
	/**
	 * Visualizza una finestra di avviso informativo.
     * Viene utilizzata per notificare all'utente il corretto completamento di 
     * un'operazione di business.
	 * @param messaggio Il testo descrittivo dell'informazione da mostrare all'utente.
	 */
	public static void mostraInfo(String messaggio) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Operazione completata");
	    alert.setHeaderText(null);
	    alert.setContentText(messaggio);
	    alert.showAndWait();
	}
	
	/**
	 * Visualizza una finestra di richiesta per ricevere una conferma esplicita 
	 * da parte dell'utente. 
	 * La finestra mostra i pulsanti standard "OK" e "Annulla". 
	 * Il metodo intercetta la scelta tramite un contenitore {@link Optional} per gestire 
	 * in modo sicuro anche l'eventuale chiusura improvvisa della finestra.
	 * @param messaggio Il testo di avviso legato all'azione da confermare
	 * @return true se l'utente clicca esplicitamente sul tasto "OK", false se clicca 
	 * su "Annulla" o chiude il pop-up.
	 */
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
