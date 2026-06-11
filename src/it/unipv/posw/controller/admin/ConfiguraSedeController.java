package it.unipv.posw.controller.admin;

import java.util.ArrayList;
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

    private List<Settore> settoriInAttesa;
    private int capienzaTotale;

    public ConfiguraSedeController(ConfiguraSedeView view, SedeService service) {
        this.view = view;
        this.service = service;
        this.settoriInAttesa = new ArrayList<>();
        this.capienzaTotale = 0;

        inizializzaVista();
        inizializzaListener();
    }

    private void inizializzaVista() {
        view.resetFormSede();
        view.resetCampiSettore();
        view.aggiornaSettoriInAttesa(settoriInAttesa);
        view.aggiornaCapienzaTotale(capienzaTotale);
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
        TipologiaSettore nomeSettore = view.getNomeSettoreSelezionato();
        String prefisso = view.getPrefisso();

        if (nomeSettore == null) {
            AlertView.mostraErrore("Seleziona un tipo di settore.");
            return;
        }
        if (prefisso.isEmpty()) {
            AlertView.mostraErrore("Il prefisso è obbligatorio (es. P, T1, C).");
            return;
        }
        if (esistePrefisso(prefisso)) {
            AlertView.mostraErrore("Esiste già un settore con il prefisso \"" + prefisso + "\". Usane uno diverso.");
            return;
        }

        // Regola di dominio: alcuni settori (es. Parterre) ammettono solo posti
        // non numerati; il tipo viene forzato a prescindere dallo stato della UI.
        TipologiaPosto tipoPosto;
        if (nomeSettore.isSoloNonNumerato()) {
            tipoPosto = TipologiaPosto.NON_NUMERATO;
        } else {
            tipoPosto = view.getTipoPostiSelezionato();
        }

        Settore settore;
        if (TipologiaPosto.NUMERATO == tipoPosto) {
            int file = view.getNumFile();
            int colonne = view.getNumColonne();
            if (file <= 0 || colonne <= 0) {
                AlertView.mostraErrore("File e colonne devono essere maggiori di 0.");
                return;
            }
            int capienza = file * colonne;
            // id_settore e id_sede a 0: verranno assegnati dal DAO al salvataggio
            settore = new Settore(0, 0, nomeSettore, TipologiaPosto.NUMERATO, capienza, file, colonne, prefisso);
        } else {
            int capienza = view.getCapienza();
            if (capienza <= 0) {
                AlertView.mostraErrore("La capienza deve essere maggiore di 0.");
                return;
            }
            settore = new Settore(0, 0, nomeSettore, TipologiaPosto.NON_NUMERATO, capienza, 0, 0, prefisso);
        }

        settoriInAttesa.add(settore);
        capienzaTotale += settore.getCapienza_max();

        view.aggiornaSettoriInAttesa(settoriInAttesa);
        view.aggiornaCapienzaTotale(capienzaTotale);
        view.resetCampiSettore();
    }
    
    
    private void handleSalvaSede(ActionEvent e) {
        String nomeSede = view.getNomeSede();
        String indirizzo = view.getIndirizzo();

        if (nomeSede.isEmpty() || indirizzo.isEmpty()) {
            AlertView.mostraErrore("Nome e indirizzo della sede non possono essere vuoti.");
            return;
        }
        if (settoriInAttesa.isEmpty()) {
            AlertView.mostraErrore("Aggiungi almeno un settore prima di salvare.");
            return;
        }

        Sede sede = new Sede(0, nomeSede, indirizzo, new ArrayList<>(settoriInAttesa));

        try {
            service.configuraSede(sede, settoriInAttesa);
            AlertView.mostraInfo("Sede \"" + nomeSede + "\" configurata con successo!");
            resetStato();
            aggiornaListaSedi();
        } catch (SedeEsistenteException ex) {
            AlertView.mostraErrore(ex.getMessage());
        }
    }
    

    /**
     * Applica la regola di dominio sul tipo di posto in base al settore scelto:
     * se il settore ammette solo posti non numerati (es. Parterre) il tipo viene
     * forzato e bloccato, altrimenti la scelta resta libera.
     */
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





    private boolean esistePrefisso(String prefisso) {
        for (Settore s : settoriInAttesa) {
            if (s.getPrefisso().equalsIgnoreCase(prefisso)) {
                return true;
            }
        }
        return false;
    }

    private void resetStato() {
        settoriInAttesa.clear();
        capienzaTotale = 0;
        view.resetFormSede();
        view.aggiornaSettoriInAttesa(settoriInAttesa);
        view.aggiornaCapienzaTotale(capienzaTotale);
    }
}