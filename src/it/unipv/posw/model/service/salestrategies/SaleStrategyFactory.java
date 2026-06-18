package it.unipv.posw.model.service.salestrategies;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * @author gpelle
 */

public class SaleStrategyFactory {
	private final String PROPERTYNAME="Sale.strategy.class.name";
	private final String PROPERYPATH="properties/properties";
	private static SaleStrategyFactory instance;
	
	private ISaleStrategy sale_strategy;
	
	
	private SaleStrategyFactory() {}
	
	public static SaleStrategyFactory getInstance() {
        if (instance == null) {
            instance = new SaleStrategyFactory();
        }
        return instance;
    }
	
	
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


