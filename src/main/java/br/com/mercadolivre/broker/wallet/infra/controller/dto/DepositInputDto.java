package br.com.mercadolivre.broker.wallet.infra.controller.dto;

import lombok.Data;

@Data
public class DepositInputDto {
    private String asset;
    private String amount;
}
