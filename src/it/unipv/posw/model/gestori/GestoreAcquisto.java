package it.unipv.posw.model.gestori;

import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.service.AcquistoService;
import it.unipv.posw.model.service.CarrelloService;

/** Classe che contiene il model necessario per AcquistoController
 * @author rkomi-dev
 */

public class GestoreAcquisto {
	
	private static GestoreAcquisto instance;
	private CarrelloService carrelloService;
	private Carrello carrello;
	private AcquistoService acquistoService;
	
	 public static GestoreAcquisto getInstance() {
		 if (instance == null) {
	            instance = new GestoreAcquisto();
	        }
	     return instance;
	 }
	 
	 private GestoreAcquisto() {
		 this.carrelloService = new CarrelloService();
		 this.carrello = Carrello.getInstance();
		 this.acquistoService = new AcquistoService();
	 }
	 
	 public CarrelloService getCarrelloService() {
		 return carrelloService;
	 }

	 public Carrello getCarrello() {
		 return carrello;
	 }

	 public AcquistoService getAcquistoService() {
		 return acquistoService;
	 }
	 
	 
	

}
