package br.com.mercadolivre.broker.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.WalletRepositoryPostgres;

@Configuration
public class DatabaseConfig {

    @Bean
    public WalletRepository walletRepository() {
        return new WalletRepositoryPostgres();
    }

}
