package it.unipv.posw.controller;

import java.util.ArrayList;

import java.util.List;
import it.unipv.posw.model.service.ricerca.IRicercaStrategy;
import it.unipv.posw.model.service.ricerca.RicercaFactory;
import it.unipv.posw.model.service.ricerca.RicercaType;
import it.unipv.posw.view.RicercaView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.MenuItem;

/**
 * @author rkomi-dev
 */

public class RicercaController {
	
    private RicercaView view;
    private IRicercaStrategy strategiaAttuale;

    public RicercaController(RicercaView view) {
        this.view = view;
        this.strategiaAttuale = RicercaFactory.getRicercaStrategy(RicercaType.PER_EVENTO); 

        inizializzaListener();
    }

    private void inizializzaListener() {
 
        view.getComboTipoRicerca().valueProperty().addListener(new ChangeListener<RicercaType>() {
            @Override
            public void changed(ObservableValue<? extends RicercaType> observable, RicercaType oldValue, RicercaType newValue) {
                strategiaAttuale = RicercaFactory.getRicercaStrategy(newValue);
                view.nascondiSuggerimenti();
            }
        });

        view.getTxtRicerca().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.trim().length() < 2) {
                    view.nascondiSuggerimenti();
                } else {
                    List<?> risultati = strategiaAttuale.ricerca(newValue);
                    
                    if (risultati == null || risultati.isEmpty()) {
                        view.nascondiSuggerimenti();
                    } else {

                        List<String> etichette = new ArrayList<>();
                        for (Object obj : risultati) {
                            etichette.add(strategiaAttuale.getEtichettaSuggerimento(obj));
                        }
                        
                        List<MenuItem> itemsGrafici = view.rigeneraPopUpSuggerimenti(etichette);

                        view.mostraPopup();
                    }
                }
            }
        });
    }
}