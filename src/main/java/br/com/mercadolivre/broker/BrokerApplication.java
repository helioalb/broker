package br.com.mercadolivre.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
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
    public AskRepository askRepository() {
        return new AskRepositoryPostgres();
    }

    @Bean
    public BidRepository bidRepository() {
        return new BidRepositoryPostgres();
    }
}
