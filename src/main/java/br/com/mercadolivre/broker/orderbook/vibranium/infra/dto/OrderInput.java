package br.com.mercadolivre.broker.orderbook.vibranium.infra.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public abstract class OrderInput {
    private static final int UUID_LENGTH = 36;

    @Size(min = UUID_LENGTH, max = UUID_LENGTH)
    @NotNull(message = "walletCode must be sent")
    protected String walletCode;

    @Min(0)
    @NotNull(message = "quantity must be sent")
    protected BigDecimal quantity;

    @Min(0)
    @NotNull(message = "price must be sent")
    protected BigDecimal price;

    protected OrderInput(String walletCode, BigDecimal quantity, BigDecimal price) {
        this.walletCode = walletCode;
        this.quantity = quantity;
        this.price = price;
    }

    protected OrderInput() {}
}

