package br.com.mercadolivre.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.AskRepositoryDB;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.BidRepositoryDB;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.WalletRepositoryDB;

@SpringBootApplication
public class BrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerApplication.class, args);
	}

    @Bean
    public WalletRepository walletRepository() {
        return new WalletRepositoryDB();
    }

    @Bean
    public AskRepository askRepository() {
        return new AskRepositoryDB();
    }

    @Bean
    public BidRepository bidRepository() {
        return new BidRepositoryDB();
    }
}
