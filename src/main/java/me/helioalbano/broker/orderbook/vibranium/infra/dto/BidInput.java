package me.helioalbano.broker.orderbook.vibranium.infra.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BidInput extends OrderInput implements Serializable {
    public BidInput(String walletCode, BigDecimal quantity, BigDecimal price) {
        super(walletCode, quantity, price);
    }

    public BidInput() {
        super();
    }
}
