package br.com.mercadolivre.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.PriceTimePriorityMatcher;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.factory.RepositoryFactoryPostgres;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.AskRepositoryPostgres;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.BidRepositoryPostgres;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.WalletRepositoryPostgres;

@SpringBootApplication
public class BrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerApplication.class, args);
	}

    @Bean
    public WalletRepository walletRepository() {
        return new WalletRepositoryPostgres();
    }

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
