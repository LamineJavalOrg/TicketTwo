package it.unipv.posw.controller.admin;

import java.time.LocalDate; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoSenzaTappeException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.service.EventoService;
import it.unipv.posw.model.service.SedeService;
import it.unipv.posw.view.admin.CreaEventoView;
import it.unipv.posw.view.utility.AlertView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author gpelle
 */

public class CreaEventoController {

    private CreaEventoView view;
    private SedeService sedeService;
    private EventoService eventoService;

    private List<Tappa> tappe;

    public CreaEventoController(CreaEventoView view, SedeService sedeService, EventoService eventoService) {
        this.view = view;
        this.sedeService = sedeService;
        this.eventoService = eventoService;
        this.tappe = new ArrayList<>();

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.popolaSedi(sedeService.getTutteLeSedi());
        aggiornaSedeSelezionata();
    }

    private void inizializzaListener() {
        this.view.getBtnAggiungiTappa().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAggiungiTappa(event);
            }
        });

        this.view.getBtnCreaEvento().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCreaEvento(event);
            }
        });

        this.view.getComboSede().valueProperty().addListener(new ChangeListener<Sede>() {
            @Override
            public void changed(ObservableValue<? extends Sede> observable, Sede oldValue, Sede newValue) {
                aggiornaSedeSelezionata();
            }
        });
    }

    private void handleAggiungiTappa(ActionEvent e) {
        Tappa tappa = costruisciTappaDallaView();

        try {
            eventoService.validaTappa(tappa);
        } catch (EmptyFieldException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        } catch (TariffaNonValidaException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        } catch (DataPassataException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        }

        tappe.add(tappa);

        Sede sedeSel = view.getSedeSelezionata();
        LocalDate data = view.getData();
        String riga = String.format("%s - %s %02d:%02d (%d tariffe)",
                sedeSel.getNome(), data, view.getOra(), view.getMinuti(), tappa.getTariffe().size());
        view.aggiungiRigaRiepilogo(riga);
    }
    
    private void handleCreaEvento(ActionEvent e) {
        String nome = view.getNomeEvento();
        String nomeArtista = view.getNomeArtista();
        TipologiaEvento tipo = view.getTipoEvento();
        String emailOrg = SessioneOrganizzatore.getInstance().getOrganizzatoreLoggato().getEmail();

        try {
            Evento creato = eventoService.creaEvento(nome, tipo, emailOrg, nomeArtista, tappe);
            if (creato != null) {
                AlertView.mostraInfo("Evento " + creato.getNome() + " creato con successo ("
                        + creato.getTappe().size() + " tappe)");
                resetStato();
            } else {
                AlertView.mostraErrore("Errore durante il salvataggio dell'evento. Riprova.");
            }
        } catch (EmptyFieldException ex) {
            AlertView.mostraErrore(ex.getMessage());
        } catch (EventoSenzaTappeException ex) {
            AlertView.mostraErrore(ex.getMessage());
        } catch (TariffaNonValidaException ex) {
            AlertView.mostraErrore(ex.getMessage());
        } catch (DataPassataException ex) {
            AlertView.mostraErrore(ex.getMessage());
        }
    }

    private void aggiornaSedeSelezionata() {
        Sede sede = view.getSedeSelezionata();
        if (sede != null) {
            view.aggiornaPiantina(sede.getSettori());
        }
    }

    
    private Tappa costruisciTappaDallaView() {
        Sede sedeSel = view.getSedeSelezionata();
        LocalDate data = view.getData();

        int idSede = (sedeSel != null) ? sedeSel.getId_sede() : 0;
        String nomeSede = (sedeSel != null) ? sedeSel.getNome() : null;
        LocalDateTime dataOra = (data != null) ? data.atTime(view.getOra(), view.getMinuti()) : null;

        Tappa tappa = new Tappa(0, 0, idSede, nomeSede, dataOra);

        for (Settore s : view.getSettoriConfigurati()) {
            TipologiaBiglietto tipo = view.getTipoBiglietto(s);
            double prezzo = parsePrezzo(view.getPrezzo(s));
            int qtaMax = view.getQuantita(s);
            tappa.getTariffe().add(new Tariffa(0, s.getId_settore(), tipo, prezzo, qtaMax, 0));
        }
        return tappa;
    }

    private double parsePrezzo(String prezzoStr) {
        try {
            return Double.parseDouble(prezzoStr.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    
    private void resetStato() {
        tappe = new ArrayList<>();
        view.resetForm();
        Sede sedeCorrente = view.getSedeSelezionata();
        if (sedeCorrente != null) {
            view.aggiornaPiantina(sedeCorrente.getSettori());
        }
    }
}