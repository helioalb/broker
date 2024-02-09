package me.helioalbano.broker.orderbook.vibranium.domain.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.orderbook.vibranium.domain.entity.Ask;
import me.helioalbano.broker.orderbook.vibranium.domain.entity.Bid;
import me.helioalbano.broker.orderbook.vibranium.domain.entity.Trade;
import me.helioalbano.broker.orderbook.vibranium.domain.factory.RepositoryFactory;

public class PriceTimePriorityMatcher extends MatcherEngine {

    public PriceTimePriorityMatcher(RepositoryFactory repositoryFactory,
                                    TradeSender tradeSender) {
        super(repositoryFactory, tradeSender);
    }

    @Override
    @Transactional
    public void processBid(String walletCode, BigDecimal quantity, BigDecimal price) {
        if (!walletTrade.isAvailableFor(walletCode)) {
            throw new IllegalStateException("wallet " + walletCode + "not exists");
        }

        Bid bid = new Bid(walletCode, quantity, price);
        askRepository.top().ifPresentOrElse(ask -> {
            if (bid.comparePriceWith(ask) < 0) {
                bidRepository.save(bid);
            } else if (bid.compareQuantityWith(ask) == 0) {
                BigDecimal vib = ask.getQuantity();
                BigDecimal brl = ask.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
            } else if (bid.compareQuantityWith(ask) > 0) {
                BigDecimal vib = ask.getQuantity();
                BigDecimal brl = ask.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
                processBid(walletCode, bid.decreasedQuantityBasedOn(ask), price);
            } else if (bid.compareQuantityWith(ask) < 0) {
                BigDecimal vib = bid.getQuantity();
                BigDecimal brl = ask.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
                askRepository.save(ask.createNewAfterTradeWith(bid));
            }
        }, () -> bidRepository.save(bid));
    }

    @Override
    @Transactional
    public void processAsk(String walletCode, BigDecimal quantity, BigDecimal price) {
        if (!walletTrade.isAvailableFor(walletCode)) {
            throw new IllegalStateException("wallet " + walletCode + "not exists");
        }

        Ask ask = new Ask(walletCode, quantity, price);
        bidRepository.top().ifPresentOrElse(bid -> {
            if (ask.comparePriceWith(bid) > 0) {
                askRepository.save(ask);
            } else if (ask.compareQuantityWith(bid) == 0) {
                BigDecimal vib = ask.getQuantity();
                BigDecimal brl = bid.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
            } else if (ask.compareQuantityWith(bid) > 0) {
                BigDecimal vib = bid.getQuantity();
                BigDecimal brl = bid.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
                processAsk(walletCode, ask.decreasedQuantityBasedOn(bid), price);
            } else if (ask.compareQuantityWith(bid) < 0) {
                BigDecimal vib = ask.getQuantity();
                BigDecimal brl = bid.amountForQuantity(vib);
                trade(bid, ask, brl, vib);
                bidRepository.save(bid.createNewAfterTradeWith(ask));
            }
        }, () -> askRepository.save(ask));
    }

    private void trade(Bid bid, Ask ask, BigDecimal amount, BigDecimal quantity) {
        walletTrade.leftWalletCode(bid.getWalletCode())
            .leftAssetOut("BRL")
            .leftAmountOut(amount)
            .rightWalletCode(ask.getWalletCode())
            .rightAssetOut("VIB")
            .rightAmountOut(quantity)
            .execute();

            Trade trade = new Trade(Asset.VIB, bid, ask, quantity, amount);
            bidRepository.delete(bid);
            askRepository.delete(ask);
            tradeSender.send(trade);
    }
}
