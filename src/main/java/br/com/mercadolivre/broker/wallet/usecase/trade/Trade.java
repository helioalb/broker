package br.com.mercadolivre.broker.wallet.usecase.trade;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

public class Trade {
    private Asset leftAssetOut;
    private BigDecimal leftAmountOut;
    private Asset rightAssetOut;
    private BigDecimal rightAmountOut;
    private WalletRepository repository;
    private Wallet leftWallet;
    private Wallet rightWallet;

    public Trade(WalletRepository repository) {
        this.repository = repository;
    }

    public Trade leftWalletCode(String code) {
        repository.findByCode(code).ifPresent(wallet -> this.leftWallet = wallet);
        return this;
    }

    public Trade rightWalletCode(String code) {
        repository.findByCode(code).ifPresent(wallet -> this.rightWallet = wallet);
        return this;
    }

    public Trade leftAssetOut(String asset) {
        this.leftAssetOut = Asset.valueOf(asset);
        return this;
    }

    public Trade leftAmountOut(BigDecimal amount) {
        this.leftAmountOut = amount;
        return this;
    }

    public Trade rightAssetOut(String asset) {
        this.rightAssetOut = Asset.valueOf(asset);
        return this;
    }

    public Trade rightAmountOut(BigDecimal amount) {
        this.rightAmountOut = amount;
        return this;
    }

    public void execute() {
        TradeService trade = new TradeService(leftWallet, rightWallet);
        trade.transfer(leftAssetOut, leftAmountOut, rightAssetOut, rightAmountOut);
        repository.realize(trade);
    }

    public boolean isAvailableFor(String walletCode) {
        return repository.findByCode(walletCode).isPresent();
    }
}
