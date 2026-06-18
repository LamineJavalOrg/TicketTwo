package it.unipv.posw.controller.home;



import java.util.ArrayList;

import java.util.List;

import it.unipv.posw.controller.acquisto.AcquistoController;
import it.unipv.posw.controller.acquisto.EventoController;
import it.unipv.posw.model.entities.Artista;
import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.RicercaType;
import it.unipv.posw.model.gestori.GestoreAcquisto;
import it.unipv.posw.model.gestori.GestoreEvento;
import it.unipv.posw.model.service.ricerca.IRicercaStrategy;
import it.unipv.posw.model.service.ricerca.RicercaFactory;
import it.unipv.posw.view.acquisto.AcquistoFrame;
import it.unipv.posw.view.ricerca.RicercaFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/** Controller che gestisce ricerca e post-ricerca
 * @author rkomi-dev
 */

public class RicercaController {
	
    private RicercaFrame ricercaF;
    private IRicercaStrategy strategiaAttuale;
    
    public RicercaController(RicercaFrame ricercaF, IRicercaStrategy strategiaAttuale) {
        this.ricercaF = ricercaF;
        this.strategiaAttuale = strategiaAttuale; 

        inizializzaListener();
    }

    private void inizializzaListener() {
 
    	ricercaF.getRicercaView().getComboTipoRicerca().valueProperty().addListener(new ChangeListener<RicercaType>() {
            @Override
            public void changed(ObservableValue<? extends RicercaType> observable, RicercaType oldValue, RicercaType newValue) {
                strategiaAttuale = RicercaFactory.getRicercaStrategy(newValue);
                ricercaF.getRicercaView().nascondiSuggerimenti();
            }
        });

    	ricercaF.getRicercaView().getTxtRicerca().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.trim().length() < 2) {
                	ricercaF.getRicercaView().nascondiSuggerimenti();
                } else {
                    List<?> risultati = strategiaAttuale.ricerca(newValue);
                    
                    if (risultati == null || risultati.isEmpty()) {
                    	ricercaF.getRicercaView().nascondiSuggerimenti();
                    } else {

                        List<String> etichette = new ArrayList<>();
                        for (Object obj : risultati) {
                            etichette.add(strategiaAttuale.getEtichettaSuggerimento(obj));
                        }
                        
                        List<MenuItem> itemsGrafici = ricercaF.getRicercaView().rigeneraPopUpSuggerimenti(etichette);
                        
                        for (int i = 0; i < risultati.size(); i++) {
                            MenuItem item = itemsGrafici.get(i);
                            Object modelloScelto = risultati.get(i); 
                             
                            item.setUserData(modelloScelto);
                            

                            item.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    MenuItem itemCliccato = (MenuItem) event.getSource();
                                    Object scelta = itemCliccato.getUserData();
   
                                    RicercaType tipoAttuale = ricercaF.getRicercaView().getComboTipoRicerca().getValue();

                                    gestisciPostRicerca(tipoAttuale, scelta);
                                    
                                    ricercaF.getRicercaView().getTxtRicerca().clear();
                                    ricercaF.getRicercaView().nascondiSuggerimenti();
                                }
                            });
                        }
                        ricercaF.getRicercaView().mostraPopup();
                    }
                }
            }
        });

    }
    public void gestisciPostRicerca(RicercaType tipo, Object scelta) {
    	
    	AcquistoFrame acquistoFrame = ricercaF.creAcquistoFrame();
    	new AcquistoController(acquistoFrame, GestoreAcquisto.getInstance());
    	
    	switch (tipo) {
		 case PER_EVENTO:
            acquistoFrame.mostraSchermata(acquistoFrame.getEventoView()); 
            Evento eventoScelto = (Evento) scelta;
			GestoreEvento.getInstance().setEvento(eventoScelto);
			new EventoController(acquistoFrame.getEventoView(), GestoreEvento.getInstance());
            
            ricercaF.mostraSchermata(acquistoFrame);

			 break;
		 case PER_ARTISTA:
			
			Artista artista = (Artista) scelta;
			acquistoFrame.getEventiPerArtistaView().getLblTitolo().setText(artista.getNome_darte());
			List<Evento> lista = RicercaFactory.getRicercaStrategy(RicercaType.PER_ARTISTA).eseguiPostRicerca(scelta);
			
			for(Evento e: lista) {
				Button btn = acquistoFrame.getEventiPerArtistaView().aggiungiEventoAllaLista(e);

				btn.setOnAction(new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                   acquistoFrame.mostraSchermata(acquistoFrame.getEventoView());
	                   GestoreEvento.getInstance().setEvento(e);
	                   new EventoController(acquistoFrame.getEventoView(), GestoreEvento.getInstance());
	                }
				});
			}
			
			acquistoFrame.mostraSchermata(acquistoFrame.getEventiPerArtistaView());
            ricercaF.mostraSchermata(acquistoFrame);
            
			break;
		
		
    	}
    }
}