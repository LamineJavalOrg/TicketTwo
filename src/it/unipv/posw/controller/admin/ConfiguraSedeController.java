package it.unipv.posw.controller.admin;

import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
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
    		Settore settore = service.creaSettore(
    				view.getNomeSettoreSelezionato(),
                    view.getPrefisso(),
                    view.getTipoPostiSelezionato(),
                    view.getNumFile(),
                    view.getNumColonne(),
                    view.getCapienza());
            nuovaSede.aggiungiSettore(settore);
        } catch (SettoreNonValidoException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        }
 
        view.aggiornaSettoriInAttesa(nuovaSede.getSettori());
        view.aggiornaCapienzaTotale(nuovaSede.getCapienzaTotale());
        view.resetCampiSettore();
    }
    
    
    private void handleSalvaSede(ActionEvent e) {
    	nuovaSede.setNome(view.getNomeSede());
        nuovaSede.setIndirizzo(view.getIndirizzo());
 
        try {
            Sede salvata = service.configuraSede(nuovaSede);
            if (salvata == null) {
                AlertView.mostraErrore("Errore durante il salvataggio della sede. Riprova.");
                return;
            }
            AlertView.mostraInfo("Sede " + salvata.getNome() + " configurata con successo!");
            
            resetStato();
            aggiornaListaSedi();
        } catch (EmptyFieldException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        } catch (SedeSenzaSettoriException ex) {
            AlertView.mostraErrore(ex.getMessage());
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