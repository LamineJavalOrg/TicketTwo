package it.unipv.posw.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rkomi-dev
 */

public class Carrello {
	
	private static Carrello instance;
    private List<Biglietto> items;
    
    private Carrello() {
        this.items = new ArrayList<>();
    }

    public static Carrello getInstance() {
        if (instance == null) {
            instance = new Carrello();
        }
        return instance;
    }

    public void aggiungi(Biglietto b) { 
    	items.add(b); 
    }
    
    public List<Biglietto> getItems() { 
    	return items; 
    }
    
    public void svuota() { 
    	items.clear(); 
    }
    
    public double getTotale() {
        double totale = 0;
        for (Biglietto b : items) {
            totale += b.getPrezzo(); 
        }
        return totale;
    }

}
