package it.unipv.posw.controller.acquisto;

import java.util.List;

import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.entities.Utente;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.gestori.GestoreEvento;
import it.unipv.posw.view.acquisto.EventoView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * @author gpelle
 */

public class EventoController {

    private EventoView view;
    private GestoreEvento model;

    private ChangeListener<Tappa> tappaListener;
    private ChangeListener<Settore> settoreListener;
    
	private TipologiaBiglietto tipologiaCorrente;

    public EventoController(EventoView view, GestoreEvento model) {
		this.view = view;
        this.model = model;

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.setTitolo(model.getEvento().getNome());
        view.setTipoEvento(model.getEvento().getTipo().toString());
        view.popolaTappe(model.getEventoService().getTappePerEvento(model.getEvento().getId_evento()));

        

        Tappa iniziale = view.getComboTappe().getValue();
        if (iniziale != null) {
            aggiornaListaSettori(iniziale);
        } else {
            pulisciCampiDipendenti();
        }
    }

    private void inizializzaListener() {
        this.tappaListener = new ChangeListener<Tappa>() {
            @Override
            public void changed(ObservableValue<? extends Tappa> observable, Tappa oldValue, Tappa newValue) {
                if (newValue != null) {
                    aggiornaListaSettori(newValue);
                }
            }
        };
        this.view.getComboTappe().valueProperty().addListener(tappaListener);

        this.settoreListener = new ChangeListener<Settore>() {
            @Override
            public void changed(ObservableValue<? extends Settore> observable, Settore oldValue, Settore newValue) {
                if (newValue != null) {
                    aggiornaListaTipologie(newValue);
                }
            }
        };
        this.view.getComboSettori().valueProperty().addListener(settoreListener);

    }

    
    private <T> void popolaCombo(ComboBox<T> combo, ChangeListener<T> listener, List<T> elementi) {
        if (listener != null) {
            combo.valueProperty().removeListener(listener);
        }
        try {
            combo.getItems().clear();
            if (elementi != null && !elementi.isEmpty()) {
                combo.setItems(FXCollections.observableArrayList(elementi));
                combo.getSelectionModel().selectFirst();
            }
        } finally {
            if (listener != null) {
                combo.valueProperty().addListener(listener);
            }
        }
    }

    
    private void aggiornaListaSettori(Tappa tappa) {
        if (tappa == null) {
            pulisciCampiDipendenti();
            return;
        }
        
        List<Settore> settori = model.getSedeService().getSettoriPerSede(tappa);
        popolaCombo(view.getComboSettori(), settoreListener, settori);

        if (settori != null && !settori.isEmpty()) {
            aggiornaListaTipologie(view.getComboSettori().getValue());
        } else {
            pulisciCampiDipendenti();
        }
    }

    private void aggiornaListaTipologie(Settore settore) {
        Tappa tappa = view.getComboTappe().getValue();
        if (tappa == null || settore == null) {
            resetPrezzo();
            return;
        }

        List<TipologiaBiglietto> tipologie = model.getEventoService().getTipologiePerSettore(
                tappa.getId_tappa(), settore.getId_settore());
        
        if (tipologie != null && !tipologie.isEmpty()) {
            this.tipologiaCorrente = tipologie.get(0);
            view.setTipologiaValore(this.tipologiaCorrente.toString());
            aggiornaPrezzo();
        } else {
            resetPrezzo();
        }
    }
        

    private void aggiornaPrezzo() {
        Tappa tappa = view.getComboTappe().getValue();
        Settore settore = view.getComboSettori().getValue();
        TipologiaBiglietto tipo = this.tipologiaCorrente;

        if (tappa == null || settore == null || tipo == null) {
            resetPrezzo();
            return;
        }

        Tariffa tariffa = model.getEventoService().getTariffaSpecifica(
                tappa.getId_tappa(), settore.getId_settore(), tipo);

        if (tariffa == null) {
            resetPrezzo();
            return;
        }

        Utente utente = SessioneCliente.getInstance().getClienteLoggato();
        double prezzoBase = tariffa.getPrezzo();
        double prezzoFinale = model.getEventoService().calcolaPrezzoFinale(prezzoBase, utente);

        if (prezzoFinale < prezzoBase) {
			String nomeSconto = model.getEventoService().getNomeStrategiaApplicata(prezzoBase, utente);
			view.mostraPrezzoScontato(prezzoBase, prezzoFinale, nomeSconto);
		} else {
			view.mostraPrezzo(prezzoBase);
		}
    }

 
    private void pulisciCampiDipendenti() {
        popolaCombo(view.getComboSettori(), settoreListener, null);
        resetPrezzo();
    }

    private void resetPrezzo() {
    	this.tipologiaCorrente = null;
        view.resetTipologia();
        view.resetPrezzo();
    }    
}