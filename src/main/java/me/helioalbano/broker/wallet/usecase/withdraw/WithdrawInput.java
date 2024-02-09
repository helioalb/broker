package me.helioalbano.broker.wallet.usecase.withdraw;

import java.math.BigDecimal;

import lombok.Getter;
import me.helioalbano.broker.common.enums.Asset;

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
