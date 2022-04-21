package br.com.mercadolivre.broker.orderbook.vibranium.infra.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderInput implements Serializable {
    private String walletCode;
    private BigDecimal quantity;
    private BigDecimal price;
    private String type;

    public OrderInput(String walletCode, BigDecimal quantity, BigDecimal price) {
        this.walletCode = walletCode;
        this.quantity = quantity;
        this.price = price;
    }

}

