package br.com.mercadolivre.broker.orderbook.vibranium.domain.service;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.exception.AskException;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.exception.BidException;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.wallet.usecase.trade.Trade;

public abstract class MatcherEngine {
    protected BidRepository bidRepository;
    protected AskRepository askRepository;
    protected Trade trade;

    protected MatcherEngine(RepositoryFactory repositoryFactory) {
        this.bidRepository = repositoryFactory.createBidRepository();
        this.askRepository = repositoryFactory.createAskRepository();
        this.trade = new Trade(repositoryFactory.createWalletRepository());
    }

    public abstract void processBid(String walletCode,
                                    BigDecimal quantity,
                                    BigDecimal price) throws BidException;
    public abstract void processAsk(String walletCode,
                                    BigDecimal quantity,
                                    BigDecimal price) throws AskException;
}
