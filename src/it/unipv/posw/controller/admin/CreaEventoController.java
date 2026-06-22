package it.unipv.posw.controller.admin;

import java.time.LocalDate; 
import java.time.LocalDateTime;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.DataTappaDuplicataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.gestori.GestoreAdmin;
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
    private GestoreAdmin model;

    public CreaEventoController(CreaEventoView view, GestoreAdmin model) {
        this.view = view;
        this.model = model;

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.popolaSedi(model.getSedeService().getTutteLeSedi());
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
            model.getCreaEventoService().validaTappa(tappa);
            model.getCreaEventoService().validaDataNonDuplicata(tappa, model.getEvento().getTappe());
        } catch (EmptyFieldException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        } catch (TariffaNonValidaException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        } catch (DataPassataException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        } catch (DataTappaDuplicataException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
		}

        model.getEvento().getTappe().add(tappa);

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
            Evento creato = model.getCreaEventoService().creaEvento(nome, tipo, emailOrg, nomeArtista,
            		model.getEvento().getTappe());
            AlertView.mostraInfo("Evento " + creato.getNome() + " creato con successo ("
                        + creato.getTappe().size() + " tappe)");
            resetStato();
            
        } catch (EmptyFieldException ex) {
            AlertView.mostraErrore(ex.getMessage());
        } catch (EventoException ex) {
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

    // Converte la stringa del prezzo in double
    private double parsePrezzo(String prezzoStr) {
        try {
            return Double.parseDouble(prezzoStr.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    // Svuota le tappe dell'evento del gestore, resetta il form e ricarica la piantina
    private void resetStato() {
    	model.getEvento().getTappe().clear();
        view.resetForm();
        Sede sedeCorrente = view.getSedeSelezionata();
        if (sedeCorrente != null) {
            view.aggiornaPiantina(sedeCorrente.getSettori());
        }
    }
}