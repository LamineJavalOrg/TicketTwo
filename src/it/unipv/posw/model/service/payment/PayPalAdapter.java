package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class PayPalAdapter implements IPagamentoAdapter {
	
	IPayPal pay;
	
	public PayPalAdapter(IPayPal pay) {
		super();
		this.pay = pay;
	}

	@Override
	public boolean Paga(double importo) {
		pay.pagaPayPal(importo);
		return true;
	}

}
