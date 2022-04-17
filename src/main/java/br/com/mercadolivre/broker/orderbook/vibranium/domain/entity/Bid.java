package br.com.mercadolivre.broker.orderbook.vibranium.domain.entity;

import java.math.BigDecimal;

public class Bid extends Intent {

    public Bid(String walletCode, BigDecimal quantity, BigDecimal price) {
        super(walletCode, quantity, price);
    }

    public Bid createNewAfterTradeWith(Ask ask) {
        BigDecimal newQuantity = this.decreasedQuantityBasedOn(ask);
        return new Bid(this.getWalletCode(), newQuantity, this.price);
    }
}
