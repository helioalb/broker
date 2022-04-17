package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import lombok.Getter;

@Getter
public class WithdrawInput {

    private String code;
    private Asset asset;
    private BigDecimal amount;

    public WithdrawInput(String walletCode, String asset, String amount) {
        this.code = walletCode;
        this.asset = Asset.valueOf(asset);
        this.amount = new BigDecimal(amount);
    }

}
