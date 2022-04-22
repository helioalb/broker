package br.com.mercadolivre.broker.orderbook.vibranium.infra.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AskInput extends OrderInput implements Serializable {
    public AskInput(String walletCode, BigDecimal quantity, BigDecimal price) {
        super(walletCode, quantity, price);
    }

    public AskInput() {
        super();
    }
}
