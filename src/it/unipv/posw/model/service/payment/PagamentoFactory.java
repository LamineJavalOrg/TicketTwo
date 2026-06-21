package it.unipv.posw.model.service.payment;

import java.lang.reflect.Constructor;
import java.util.Properties;
import java.io.FileInputStream;

/** Factory che mi restituisce l'adapter per il metodo di pagamento,
 * usa file properties di configurazione
 * @author rkomi-dev
 */

public class PagamentoFactory {
	
	private static final String P_PROPERTYNAME = "PayPal.adapter.class.name";
	private static final String M_PROPERTYNAME = "Mastercard.adapter.class.name";
	
	/**
	 * Metodo che mi restutisce l'adapter, usa file properties
	 * @param pay Classe che implementa l'interface {@link IPayPal}
	 * @return L'adapter {@link PayPalAdapter}
	 */
	public static PayPalAdapter getPayPalAdapter(IPayPal pay) {
		
		String PayPalAdaptClassName;
		PayPalAdapter padapter = null;
	
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/properties"));
			PayPalAdaptClassName = p.getProperty(P_PROPERTYNAME);
		
			Constructor<?> c = Class.forName(PayPalAdaptClassName).getConstructor(IPayPal.class);
			padapter = (PayPalAdapter)c.newInstance(pay);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	return padapter;
	}
	
	/**
	 * Metodo che mi restutisce l'adapter, usa file properties
	 * @param mast Classe che implementa l'interface {@link IMastercard}
	 * @return L'adapter {@link MastercardAdapter}
	 */
	public static MastercardAdapter getMastercardAdapter(IMastercard mast) {
		
		String MastercardAdaptClassName;
		MastercardAdapter madapter = null;
	
		try {
			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream("properties/properties"));
			MastercardAdaptClassName = p.getProperty(M_PROPERTYNAME);
		
			Constructor<?> c = Class.forName(MastercardAdaptClassName).getConstructor(IMastercard.class);
			madapter = (MastercardAdapter)c.newInstance(mast);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	return madapter;
	}

}
