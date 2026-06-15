package it.unipv.posw.model.gestori;

import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.service.CarrelloService;

/** Classe che contiene il model necessario per AcquistoController
 * @author rkomi-dev
 */

public class GestoreAcquisto {
	
	private static GestoreAcquisto instance;
	private CarrelloService carrelloService;
	private Carrello carrello;
	
	 public static GestoreAcquisto getInstance() {
		 if (instance == null) {
	            instance = new GestoreAcquisto();
	        }
	     return instance;
	 }
	 
	 private GestoreAcquisto() {
		 this.carrelloService = new CarrelloService();
		 this.carrello = Carrello.getInstance();
	 }
	 
	 public CarrelloService getCarrelloService() {
		 return carrelloService;
	 }

	 public Carrello getCarrello() {
		 return carrello;
	 }
	 
	 
	

}
