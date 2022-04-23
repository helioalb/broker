package br.com.mercadolivre.broker.wallet.domain.service;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.TradeException;

public class TradeService {
    private Wallet leftWallet;
    private Wallet rightWallet;

    public TradeService(Wallet leftWallet, Wallet rightWallet) {
        this.leftWallet = leftWallet.clearTransactions();
        this.rightWallet = rightWallet.clearTransactions();
    }

    public void transfer(Asset leftAssetOut, BigDecimal leftAmountOut, Asset rightAssetOut, BigDecimal rightAmountOut) {
        if (leftAssetOut.equals(rightAssetOut))
            throw new TradeException("left and right asset must be different");
        leftWallet.sendTransfer(leftAssetOut, leftAmountOut);
        rightWallet.receiveTransfer(leftAssetOut, leftAmountOut);
        rightWallet.sendTransfer(rightAssetOut, rightAmountOut);
        leftWallet.receiveTransfer(rightAssetOut, rightAmountOut);
    }

    public Wallet getLeftWallet() {
        return this.leftWallet;
    }

    public Wallet getRightWallet() {
        return this.rightWallet;
    }
}
