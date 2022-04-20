package br.com.mercadolivre.broker.orderbook.vibranium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.PriceTimePriorityMatcher;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.factory.RepositoryFactoryPostgres;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.AskRepositoryPostgres;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.BidRepositoryPostgres;

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
    public MatcherEngine match() {
        return new PriceTimePriorityMatcher(repositoryFactory());
    }

}
