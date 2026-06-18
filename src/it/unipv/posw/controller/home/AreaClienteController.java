package it.unipv.posw.controller.home;

import java.util.List;

import it.unipv.posw.model.entities.RiepilogoAcquisto;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.service.AreaClienteService;
import it.unipv.posw.view.home.AreaClienteView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/** 
 * @author rkomi-dev
 */

public class AreaClienteController {
	
	private AreaClienteView areaClienteView;
	private AreaClienteService areaClienteService;
	
	public AreaClienteController(AreaClienteView areaClienteView, AreaClienteService areaClienteService) {
	
		this.areaClienteView = areaClienteView;
		this.areaClienteService = areaClienteService;
		caricaDati();
	}
	
	private void caricaDati() {
    	
		List<RiepilogoAcquisto> acquisti = areaClienteService.storicoAcquisti(SessioneCliente.getInstance().getClienteLoggato().getEmail());
    
		for (RiepilogoAcquisto a : acquisti) {
      
			Button btn = areaClienteView.aggiungiBigliettoAllaLista(a);
			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					areaClienteService.cambioNominativo(a.getId_biglietto(), areaClienteView.getMappaCampiTesto().get(a).getText());
					areaClienteView.getMappaCampiTesto().get(a).clear();
				}
			});
			
			
		}
	
}

}
