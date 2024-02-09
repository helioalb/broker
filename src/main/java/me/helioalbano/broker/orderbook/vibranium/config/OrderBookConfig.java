package me.helioalbano.broker.orderbook.vibranium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.helioalbano.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.AskRepository;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.BidRepository;
import me.helioalbano.broker.orderbook.vibranium.domain.service.MatcherEngine;
import me.helioalbano.broker.orderbook.vibranium.domain.service.PriceTimePriorityMatcher;
import me.helioalbano.broker.orderbook.vibranium.domain.service.TradeSender;
import me.helioalbano.broker.orderbook.vibranium.infra.factory.RepositoryFactoryPostgres;
import me.helioalbano.broker.orderbook.vibranium.infra.repository.AskRepositoryPostgres;
import me.helioalbano.broker.orderbook.vibranium.infra.repository.BidRepositoryPostgres;
import me.helioalbano.broker.orderbook.vibranium.infra.service.TradeSenderRabbitmq;

@Configuration
public class OrderBookConfig {
    @Bean
    public AskRepository askRepositoryPostgres() {
        return new AskRepositoryPostgres();
    }

    @Bean
    public BidRepository bidRepositoryPostgres() {
        return new BidRepositoryPostgres();
    }

    @Bean
    public RepositoryFactory repositoryFactory() {
        return new RepositoryFactoryPostgres();
    }

    @Bean
    public TradeSender tradeSender() {
        return new TradeSenderRabbitmq();
    }

    @Bean
    public MatcherEngine match() {
        return new PriceTimePriorityMatcher(repositoryFactory(), tradeSender());
    }

}
