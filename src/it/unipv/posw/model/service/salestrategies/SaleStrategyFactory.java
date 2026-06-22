package it.unipv.posw.model.service.salestrategies;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * Classe Singleton di tipo Factory adibita all'istanziazione dinamica delle 
 * strategie di sconto variabili.
 * Legge dal file di properties il nome completo della classe strategia e la istanzia
 * via reflection.
 * @author gpelle
 * @see ISaleStrategy
 */

public class SaleStrategyFactory {
	private final String PROPERTYNAME="Sale.strategy.class.name";
	private final String PROPERYPATH="properties/properties";
	private static SaleStrategyFactory instance;
	
	private ISaleStrategy sale_strategy;
	
	/**
	 * Costruttore privato per il Singleton.
	 */
	private SaleStrategyFactory() {}
	
	/**
	 * Restituisce l'unica istanza della factory.
	 * @return L'istanza condivisa della factory
	 */
	public static SaleStrategyFactory getInstance() {
        if (instance == null) {
            instance = new SaleStrategyFactory();
        }
        return instance;
    }
	
	/**
	 * Restituisce la strategia di sconto configurata, istanziandola via reflection alla
	 * prima invocazione e riutilizzandola nelle successive.
	 * @return La strategia di sconto, oppure null in caso di errore di caricamento.
	 */
	public ISaleStrategy getDiscountStrategy() {
				
		if (sale_strategy == null) {

			String DiscountClassName;
			
            try {
         
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream(PROPERYPATH));
				DiscountClassName=p.getProperty(PROPERTYNAME);
				
				Constructor<?> c = Class.forName(DiscountClassName).getConstructor();
				sale_strategy=(ISaleStrategy)c.newInstance();
								
			} catch (Exception e) {
				e.printStackTrace();
				sale_strategy = null;
			}
		}
		return sale_strategy;
	}
}


