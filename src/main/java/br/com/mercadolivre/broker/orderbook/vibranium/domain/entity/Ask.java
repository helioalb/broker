package br.com.mercadolivre.broker.orderbook.vibranium.domain.entity;

import java.math.BigDecimal;

public class Ask extends Intent {

    public Ask(String walletCode, BigDecimal quantity, BigDecimal price) {
        super(walletCode, quantity, price);
    }

    public Ask createNewAfterTradeWith(Bid bid) {
        BigDecimal newQuantity = this.decreasedQuantityBasedOn(bid);
        return new Ask(this.getWalletCode(), newQuantity, this.price);
    }

}
