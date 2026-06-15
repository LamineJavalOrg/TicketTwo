package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class PayPal implements IPayPal {
	
	public PayPal() {
		super();
	}

	@Override
	public boolean pagaPayPal(double importo) {
		System.out.println("Pagato con PayPal");
		return true;
	}

}
