package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import lombok.Getter;

@Getter
public class WithdrawInput {

    private String code;
    private Asset asset;
    private BigDecimal amount;

    public WithdrawInput(String walletCode, Asset asset, BigDecimal amount) {
        this.code = walletCode;
        this.asset = asset;
        this.amount = amount;
    }

}
