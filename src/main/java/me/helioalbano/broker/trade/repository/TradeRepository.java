package me.helioalbano.broker.trade.repository;

import me.helioalbano.broker.trade.domain.entity.Trade;

public interface TradeRepository {

    Trade save(Trade trade);

}
