package me.helioalbano.broker.wallet.usecase.deposit;

import java.math.BigDecimal;

import lombok.Getter;
import me.helioalbano.broker.common.enums.Asset;
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
