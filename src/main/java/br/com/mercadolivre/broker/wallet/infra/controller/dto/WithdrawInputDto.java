package br.com.mercadolivre.broker.wallet.infra.controller.dto;

import lombok.Data;

@Data
public class WithdrawInputDto {
    private String asset;
    private String amount;
}
