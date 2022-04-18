package br.com.mercadolivre.broker.orderbook.vibranium.domain.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;

public class PriceTimePriorityMatcher extends MatcherEngine {

    public PriceTimePriorityMatcher(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void processBid(String walletCode, BigDecimal quantity, BigDecimal price) {
        Bid bid = new Bid(walletCode, quantity, price);
        Ask ask = askRepository.top();

        if (ask != null && bid.comparePriceWith(ask) >= 0) {
            if (bid.compareQuantityWith(ask) == 0) {
                BigDecimal tradeQuantity = ask.getQuantity();
                trade.leftWalletCode(bid.getWalletCode()).leftAmountOut(bid.amountForQuantity(tradeQuantity))
                        .leftAssetOut("BRL").rightWalletCode(ask.getWalletCode()).rightAssetOut("VIB")
                        .rightAmountOut(ask.amountForQuantity(tradeQuantity)).execute();
                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);
            }

            else if (bid.compareQuantityWith(ask) > 0) {
                BigDecimal tradeQuantity = ask.getQuantity();
                trade.leftWalletCode(bid.getWalletCode()).leftAmountOut(bid.amountForQuantity(tradeQuantity))
                        .leftAssetOut("BRL").rightWalletCode(ask.getWalletCode()).rightAssetOut("VIB")
                        .rightAmountOut(ask.amountForQuantity(tradeQuantity)).execute();

                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);

                processBid(walletCode, bid.decreasedQuantityBasedOn(ask), price);
            }

            else if (bid.compareQuantityWith(ask) < 0) {
                BigDecimal tradeQuantity = bid.getQuantity();
                trade.leftWalletCode(bid.getWalletCode()).leftAmountOut(bid.amountForQuantity(tradeQuantity))
                        .leftAssetOut("BRL").rightWalletCode(ask.getWalletCode()).rightAssetOut("VIB")
                        .rightAmountOut(ask.amountForQuantity(tradeQuantity)).execute();
                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);

                askRepository.save(ask.createNewAfterTradeWith(bid));
            }
        } else {
            bidRepository.save(bid);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void processAsk(String walletCode, BigDecimal quantity, BigDecimal price) {
        Ask ask = new Ask(walletCode, quantity, price);;
        Bid bid = bidRepository.top();

        if (bid != null && ask.comparePriceWith(bid) <= 0) {
            if (ask.compareQuantityWith(bid) == 0) {
                BigDecimal tradeQuantity = ask.getQuantity();
                trade.leftWalletCode(bid.getWalletCode())
                    .leftAmountOut(ask.amountForQuantity(tradeQuantity))
                    .leftAssetOut("BRL")
                    .rightWalletCode(ask.getWalletCode())
                    .rightAssetOut("VIB")
                    .rightAmountOut(bid.amountForQuantity(tradeQuantity)).execute();
                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);
            } else if (ask.compareQuantityWith(bid) > 0) {
                BigDecimal tradeQuantity = bid.getQuantity();
                trade.leftWalletCode(bid.getWalletCode()).leftAmountOut(bid.amountForQuantity(tradeQuantity))
                        .leftAssetOut("BRL").rightWalletCode(ask.getWalletCode()).rightAssetOut("VIB")
                        .rightAmountOut(ask.amountForQuantity(tradeQuantity)).execute();

                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);

                processAsk(walletCode, ask.decreasedQuantityBasedOn(bid), price);
            } else if (ask.compareQuantityWith(bid) < 0) {
                BigDecimal tradeQuantity = ask.getQuantity();
                trade.leftWalletCode(bid.getWalletCode())
                    .leftAmountOut(bid.amountForQuantity(tradeQuantity))
                    .leftAssetOut("BRL").rightWalletCode(ask.getWalletCode()).rightAssetOut("VIB")
                    .rightAmountOut(ask.amountForQuantity(tradeQuantity))
                    .execute();
                bid.tradedWith(ask);
                ask.tradedWith(bid);

                bidRepository.save(bid);
                askRepository.save(ask);

                bidRepository.save(bid.createNewAfterTradeWith(ask));
            }
        } else {
            askRepository.save(ask);
        }
    }
}
