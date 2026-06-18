package it.unipv.posw.model.service.payment;

/** 
 * @author rkomi-dev
 */

public class MastercardStrategy implements IPagamentoStrategy {

	@Override
	public IPagamento getMetodoPagamento() {
		
		return PagamentoFactory.getMastercardAdapter(new Mastercard());
	}

}
