package it.unipv.posw.controller.admin;

import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.service.SedeService;
import it.unipv.posw.view.admin.ConfiguraSedeView;
import it.unipv.posw.view.utility.AlertView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author gpelle
 */

public class ConfiguraSedeController {

    private ConfiguraSedeView view;
    private SedeService service;

    private Sede nuovaSede;

    public ConfiguraSedeController(ConfiguraSedeView view, SedeService service) {
        this.view = view;
        this.service = service;
        this.nuovaSede = new Sede();

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.resetFormSede();
        view.resetCampiSettore();
        view.aggiornaSettoriInAttesa(nuovaSede.getSettori());
        view.aggiornaCapienzaTotale(nuovaSede.getCapienzaTotale());
        view.aggiornaCampiPerTipoPosto(view.getTipoPostiSelezionato());
        aggiornaListaSedi();
    }

    private void inizializzaListener() {
        this.view.getBtnAggiungiSettore().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAggiungiSettore(event);
            }
        });

        this.view.getBtnConferma().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSalvaSede(event);
            }
        });

        this.view.getComboTipoPosto().valueProperty().addListener(new ChangeListener<TipologiaPosto>() {
            @Override
            public void changed(ObservableValue<? extends TipologiaPosto> observable, TipologiaPosto oldValue, TipologiaPosto newValue) {
                view.aggiornaCampiPerTipoPosto(newValue);
            }
        });

        this.view.getComboNomeSettore().valueProperty().addListener(new ChangeListener<TipologiaSettore>() {
            @Override
            public void changed(ObservableValue<? extends TipologiaSettore> observable, TipologiaSettore oldValue, TipologiaSettore newValue) {
                aggiornaVincoloTipoPosto(newValue);
            }
        });
    }
    
    
    private void handleAggiungiSettore(ActionEvent e) {
    	try {
            Settore settore = creaSettoreDaInput();
            nuovaSede.aggiungiSettore(settore);
        } catch (IllegalArgumentException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        }
 
        view.aggiornaSettoriInAttesa(nuovaSede.getSettori());
        view.aggiornaCapienzaTotale(nuovaSede.getCapienzaTotale());
        view.resetCampiSettore();
    }
    
    private Settore creaSettoreDaInput() {
        TipologiaSettore nomeSettore = view.getNomeSettoreSelezionato();
        if (nomeSettore == null) {
            throw new IllegalArgumentException("Seleziona un tipo di settore.");
        }
 
        boolean numerato = !nomeSettore.isSoloNonNumerato() && TipologiaPosto.NUMERATO == view.getTipoPostiSelezionato();
 
        if (numerato) {
            return Settore.creaNumerato(nomeSettore, view.getPrefisso(), view.getNumFile(), view.getNumColonne());
        }
        return Settore.creaNonNumerato(nomeSettore, view.getPrefisso(), view.getCapienza());
    }
    
    
    private void handleSalvaSede(ActionEvent e) {
        String nomeSede = view.getNomeSede();
        String indirizzo = view.getIndirizzo();

        if (nomeSede.isEmpty() || indirizzo.isEmpty()) {
            AlertView.mostraErrore("Nome e indirizzo della sede non possono essere vuoti.");
            return;
        }
        if (!nuovaSede.possiedeSettori()) {
            AlertView.mostraErrore("Aggiungi almeno un settore prima di salvare.");
            return;
        }

        nuovaSede.setNome(nomeSede);
        nuovaSede.setIndirizzo(indirizzo);

        try {
            Sede salvata = service.configuraSede(nuovaSede);
            if (salvata == null) {
                AlertView.mostraErrore("Errore durante il salvataggio della sede. Riprova.");
                return;
            }
            AlertView.mostraInfo("Sede \"" + nomeSede + "\" configurata con successo!");
            resetStato();
            aggiornaListaSedi();
        } catch (SedeEsistenteException ex) {
            AlertView.mostraErrore(ex.getMessage());
        }
    }
    

    private void aggiornaVincoloTipoPosto(TipologiaSettore settore) {
        if (settore != null && settore.isSoloNonNumerato()) {
            view.bloccaTipoPosto(TipologiaPosto.NON_NUMERATO);
        } else {
            view.sbloccaTipoPosto();
        }
    }

    private void aggiornaListaSedi() {
        List<Sede> sedi = service.getTutteLeSedi();
        view.aggiornaSediEsistenti(sedi);
    }

    private void resetStato() {
    	nuovaSede = new Sede();
        view.resetFormSede();
        view.aggiornaSettoriInAttesa(nuovaSede.getSettori());
        view.aggiornaCapienzaTotale(nuovaSede.getCapienzaTotale());
    }
}