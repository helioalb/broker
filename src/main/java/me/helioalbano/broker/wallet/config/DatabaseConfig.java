package me.helioalbano.broker.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.helioalbano.broker.wallet.domain.repository.WalletRepository;
import me.helioalbano.broker.wallet.infra.repository.WalletRepositoryPostgres;

@Configuration
public class DatabaseConfig {

    @Bean
    public WalletRepository walletRepository() {
        return new WalletRepositoryPostgres();
    }

}
