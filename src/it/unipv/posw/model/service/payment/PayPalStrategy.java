package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class PayPalStrategy implements IPagamentoStrategy {

	@Override
	public IPagamentoAdapter getMetodoPagamento() {
		
		return PagamentoFactory.getPayPalAdapter(new PayPal());
	}

}
