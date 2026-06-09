package it.unipv.posw.model.service.ricerca;

import java.lang.reflect.Constructor;
import java.util.Properties;

import it.unipv.posw.model.enums.RicercaType;

import java.io.FileInputStream;

/**
 * @author rkomi-dev
 */

public class RicercaFactory {
	
private static final String STRATEGY = "Ricerca.";
	
	public static IRicercaStrategy getRicercaStrategy(RicercaType tipo) {
		
		String StrategyClassName;
		IRicercaStrategy strategy = null;
		
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/properties"));
			StrategyClassName = p.getProperty(STRATEGY + tipo.name());
		
			Constructor<?> c = Class.forName(StrategyClassName).getConstructor();
			strategy = (IRicercaStrategy) c.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return strategy;
		
	}

}
