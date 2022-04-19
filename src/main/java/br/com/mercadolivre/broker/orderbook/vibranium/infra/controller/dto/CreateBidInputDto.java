package br.com.mercadolivre.broker.orderbook.vibranium.infra.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateBidInputDto {
    private String walletCode;
    private BigDecimal quantity;
    private BigDecimal price;
}
