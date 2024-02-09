package me.helioalbano.broker.trade.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;

import me.helioalbano.broker.trade.domain.entity.Trade;
import me.helioalbano.broker.trade.infra.repository.dao.TradeDao;
import me.helioalbano.broker.trade.repository.TradeRepository;

public class TradeRepositoryMongo implements TradeRepository {

    @Autowired
    private TradeDao dao;

    @Override
    public Trade save(Trade trade) {
        return dao.save(trade);
    }

}
