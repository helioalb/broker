package br.com.mercadolivre.broker.wallet.usecase.trade;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.exception.TradeException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

public class Trade {
    private WalletRepository walletRepository;
    private String leftWalletId;
    private String rightWalletId;
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
        this.leftWallet = repository.findByCode(code);
        return this;
    }

    public Trade rightWalletCode(String code) {
        this.rightWallet = repository.findByCode(code);
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

    public TradeOutput execute() {
        try {
            TradeService trade = new TradeService(leftWallet, rightWallet);
            trade.transfer(leftAssetOut, leftAmountOut, rightAssetOut, rightAmountOut);
            repository.realize(trade);
            return new TradeOutput().withSuccess();
        } catch (Exception e) {
            return new TradeOutput().withError(e.getMessage());
        }
    }
}
