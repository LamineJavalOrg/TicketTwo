package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class Mastercard implements IMastercard {
	
	public Mastercard() {
		super();
	}

	@Override
	public boolean pagaMastercard(double importo) {
		System.out.println("Pagato con mastercard");
		return true;
	}

}
