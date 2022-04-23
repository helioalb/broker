package br.com.mercadolivre.broker.trade.usecase;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.trade.domain.entity.Trade;
import br.com.mercadolivre.broker.trade.repository.TradeRepository;

public class CreateTrade {

    private TradeRepository repository;

    public CreateTrade(TradeRepository repository) {
        this.repository = repository;
    }

    public Trade execute(Asset assetTraded,
                        Asset assetUsedToPay,
                        String buyerWalletCode,
                        String sellerWalletCode,
                        BigDecimal quantity,
                        BigDecimal amount) {
        Trade trade = new Trade(assetTraded,
                                assetUsedToPay,
                                buyerWalletCode,
                                sellerWalletCode,
                                quantity,
                                amount);
        return repository.save(trade);
    }

}
