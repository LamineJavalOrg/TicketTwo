package it.unipv.posw.model.service.payment;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import it.unipv.posw.model.enums.PaymentType;

/** Factory che restituisce la strategia di pagamento in base al tipo
 * @author rkomi-dev
 */

public class PagamentoStrategyFactory {
	
private static final String STRATEGY = "PagamentoStrategy.";
	
	public static IPagamentoStrategy getStrategia(PaymentType tipo) {
		
		String StrategyClassName;
		IPagamentoStrategy strategy = null;
		
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/properties"));
			StrategyClassName = p.getProperty(STRATEGY + tipo.name());
		
			Constructor<?> c = Class.forName(StrategyClassName).getConstructor();
			strategy = (IPagamentoStrategy) c.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return strategy;
	}

}
