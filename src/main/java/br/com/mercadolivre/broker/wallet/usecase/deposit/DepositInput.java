package br.com.mercadolivre.broker.wallet.usecase.deposit;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import lombok.Getter;
@Getter
public class DepositInput {

    private String code;
    private Asset asset;
    private BigDecimal amount;

    public DepositInput(String walletCode, Asset asset, BigDecimal amount) {
        this.code = walletCode;
        this.asset = asset;
        this.amount = amount;
    }

}
