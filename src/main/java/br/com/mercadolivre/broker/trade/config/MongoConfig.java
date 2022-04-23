package br.com.mercadolivre.broker.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mercadolivre.broker.trade.infra.repository.TradeRepositoryMongo;
import br.com.mercadolivre.broker.trade.repository.TradeRepository;

@Configuration
public class MongoConfig {
    @Bean
    public TradeRepository tradeRepository() {
        return new TradeRepositoryMongo();
    }
}
