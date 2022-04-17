package br.com.mercadolivre.broker.orderbook.vibranium.domain.entity;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public abstract class Intent {
    protected String walletCode;
    protected BigDecimal quantity;
    protected BigDecimal price;
    protected String tradedWith;

    protected Intent(String walletCode, BigDecimal quantity, BigDecimal price) {
        this.walletCode = walletCode;
        this.quantity = quantity;
        this.price = price;
    }

    public int comparePriceWith(Intent intent) {
        return this.price.compareTo(intent.getPrice());
    }

    public int compareQuantityWith(Intent intent) {
        return this.quantity.compareTo(intent.getQuantity());
    }

    public BigDecimal amountForQuantity(BigDecimal quantity) {
        if (quantity.compareTo(this.quantity) > 0)
            throw new IllegalArgumentException("this quantity is not available");
        return quantity.multiply(price);
    }

    public void tradedWith(Intent other) {
        if (this.walletCode.equals(other.getWalletCode()))
            throw new IllegalArgumentException("trade with itself is not permitted");
        this.tradedWith = other.getWalletCode();
    }

    public BigDecimal decreasedQuantityBasedOn(Intent other) {
        if (this.compareQuantityWith(other) < 0) {
            String error = "decrease cannot result in a negative quantity";
            throw new IllegalStateException(error);
        }
        return this.quantity.subtract(other.getQuantity());
    }

}
