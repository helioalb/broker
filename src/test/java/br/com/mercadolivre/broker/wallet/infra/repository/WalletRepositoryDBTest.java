package br.com.mercadolivre.broker.wallet.infra.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

@SpringBootTest()
public class WalletRepositoryDBTest {

    @Autowired
    private WalletRepository repository;

    @Test
    void create() {
        assertNotNull(repository.create());
    }

    @Test
    void persistPendingTransactions() {
        Wallet wallet = repository.getLast();
        wallet.deposit(Asset.BRL, new BigDecimal("1.20"));
        assertDoesNotThrow(() -> repository.persistPendingTransactions(wallet));
    }

    @Test
    void persistInconsistentPendingTransactions() {
        Wallet wallet = repository.getLast();
        wallet.deposit(Asset.BRL, new BigDecimal("1.20"));
        wallet.withdraw(Asset.BRL, new BigDecimal("1.20"));
        assertDoesNotThrow(() -> repository.persistPendingTransactions(wallet));
    }
}
