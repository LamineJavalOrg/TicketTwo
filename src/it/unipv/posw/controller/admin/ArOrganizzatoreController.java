package it.unipv.posw.controller.admin;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.EventoNonEliminabileException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeNonEliminabileException;
import it.unipv.posw.model.gestori.GestoreAdmin;
import it.unipv.posw.view.admin.ArOrganizzatoreView;
import it.unipv.posw.view.utility.AlertView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author gpelle
 */

public class ArOrganizzatoreController {

    private ArOrganizzatoreView view;
    private GestoreAdmin model;

    public ArOrganizzatoreController(ArOrganizzatoreView view, GestoreAdmin model) {
        this.view = view;
        this.model = model;

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        aggiornaListaSedi();
        aggiornaListaEventi();
    }

    private void inizializzaListener() {
        this.view.getBtnEliminaSede().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleEliminaSede(event);
            }
        });

        this.view.getBtnEliminaEvento().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleEliminaEvento(event);
            }
        });
    }

    private void handleEliminaSede(ActionEvent e) {
        Sede sede = view.getSedeSelezionata();
        if (view.getSedeSelezionata() == null) {
            AlertView.mostraErrore("Seleziona una sede da eliminare.");
            return;
        }

        if (!AlertView.mostraConferma("Vuoi davvero eliminare la sede " + sede.getNome() + "?")) {
            return;
        }

        try {
            boolean eliminata = model.getArOrganizzatoreService().eliminaSede(sede);
            if (eliminata) {
                AlertView.mostraInfo("Sede " + sede.getNome() + " eliminata con successo.");
            } else {
                AlertView.mostraErrore("Eliminazione della sede non riuscita. Riprova.");
            }
            aggiornaListaSedi();
        } catch (SedeException ex) {
            AlertView.mostraErrore(ex.getMessage());
        }
    }

    private void handleEliminaEvento(ActionEvent e) {
        Evento evento = view.getEventoSelezionato();
        if (evento == null) {
            AlertView.mostraErrore("Seleziona un evento da eliminare.");
            return;
        }

        if (!AlertView.mostraConferma("Vuoi davvero eliminare l'evento " + evento.getNome() + "?")) {
            return;
        }

        try {
            boolean eliminato = model.getArOrganizzatoreService().eliminaEvento(evento);
            if (eliminato) {
                AlertView.mostraInfo("Evento " + evento.getNome() + " eliminato con successo.");
            } else {
                AlertView.mostraErrore("Eliminazione dell'evento non riuscita. Riprova.");
            }
            aggiornaListaEventi();
        } catch (EventoException ex) {
            AlertView.mostraErrore(ex.getMessage());
        }
    }

    private void aggiornaListaSedi() {
        String email = SessioneOrganizzatore.getInstance().getOrganizzatoreLoggato().getEmail();
        view.popolaSedi(model.getArOrganizzatoreService().getSediPerOrganizzatore(email));
    }

    private void aggiornaListaEventi() {
        String email = SessioneOrganizzatore.getInstance().getOrganizzatoreLoggato().getEmail();
        view.popolaEventi(model.getArOrganizzatoreService().getEventiPerOrganizzatore(email));
    }
}