package me.helioalbano.broker.orderbook.vibranium.domain.service;

import java.math.BigDecimal;

import me.helioalbano.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.AskRepository;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.BidRepository;
import me.helioalbano.broker.wallet.usecase.trade.Trade;

public abstract class MatcherEngine {
    protected BidRepository bidRepository;
    protected AskRepository askRepository;
    protected Trade walletTrade;
    protected TradeSender tradeSender;

    protected MatcherEngine(RepositoryFactory repositoryFactory,
                            TradeSender tradeSender) {
        this.bidRepository = repositoryFactory.createBidRepository();
        this.askRepository = repositoryFactory.createAskRepository();
        this.walletTrade = new Trade(repositoryFactory.createWalletRepository());
        this.tradeSender = tradeSender;
    }

    public abstract void processBid(String walletCode,
                                    BigDecimal quantity,
                                    BigDecimal price);
    public abstract void processAsk(String walletCode,
                                    BigDecimal quantity,
                                    BigDecimal price);
}
