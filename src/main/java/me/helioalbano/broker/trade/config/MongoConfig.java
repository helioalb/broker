package me.helioalbano.broker.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.helioalbano.broker.trade.infra.repository.TradeRepositoryMongo;
import me.helioalbano.broker.trade.repository.TradeRepository;

@Configuration
public class MongoConfig {
    @Bean
    public TradeRepository tradeRepository() {
        return new TradeRepositoryMongo();
    }
}
