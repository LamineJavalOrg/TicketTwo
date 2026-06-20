package it.unipv.posw.controller.admin;

import java.util.List; 

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.SessioneOrganizzatore;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.gestori.GestoreAdmin;
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
    private GestoreAdmin model;

    public ConfiguraSedeController(ConfiguraSedeView view, GestoreAdmin model) {
        this.view = view;
        this.model = model;

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.resetFormSede();
        view.resetCampiSettore();
        view.aggiornaSettoriInAttesa(model.getSede().getSettori());
        view.aggiornaCapienzaTotale(model.getSede().getCapienzaTotale());
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
    		Settore settore = model.getSedeService().creaSettore(
    				view.getNomeSettoreSelezionato(),
                    view.getPrefisso(),
                    view.getTipoPostiSelezionato(),
                    view.getNumFile(),
                    view.getNumColonne(),
                    view.getCapienza());
    		model.getSede().aggiungiSettore(settore);
        } catch (SettoreNonValidoException ex) {
            AlertView.mostraErrore(ex.getMessage());
            return;
        }
 
        view.aggiornaSettoriInAttesa(model.getSede().getSettori());
        view.aggiornaCapienzaTotale(model.getSede().getCapienzaTotale());
        view.resetCampiSettore();
    }
    
    
    private void handleSalvaSede(ActionEvent e) {
    	model.getSede().setNome(view.getNomeSede());
    	model.getSede().setIndirizzo(view.getIndirizzo());
    	model.getSede().setEmail_organizzatore(
        		SessioneOrganizzatore.getInstance().getOrganizzatoreLoggato().getEmail());
 
        try {
            Sede salvata = model.getSedeService().configuraSede(model.getSede());
            AlertView.mostraInfo("Sede " + salvata.getNome() + " configurata con successo!");
            
            resetStato();
            aggiornaListaSedi();
        } catch (EmptyFieldException ex) {
        	AlertView.mostraErrore(ex.getMessage());
        } catch (SedeException ex) {
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
        List<Sede> sedi = model.getSedeService().getTutteLeSedi();
        view.aggiornaSediEsistenti(sedi);
    }

    private void resetStato() {
    	model.setSede(new Sede());
        view.resetFormSede();
        view.aggiornaSettoriInAttesa(model.getSede().getSettori());
        view.aggiornaCapienzaTotale(model.getSede().getCapienzaTotale());
    }
}