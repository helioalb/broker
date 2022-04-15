package br.com.mercadolivre.broker.wallet.infra.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void findByCode() {
        String code = repository.create();
        Wallet wallet = repository.findByCode(code);
        assertEquals(code, wallet.getCode());
    }

    @Test
    void persistPendingTransactions() {
        Wallet wallet = repository.getLast();
        wallet.deposit(Asset.BRL, new BigDecimal("1.20"));
        assertDoesNotThrow(() -> repository.persistPendingTransactions(wallet));
    }

    @Test
    void deposit20withdraw19() {
        String code = repository.create();
        Wallet wallet1 = repository.findByCode(code);
        wallet1.deposit(Asset.BRL, new BigDecimal("20"));
        repository.persistPendingTransactions(wallet1);

        Wallet wallet2 = repository.findByCode(code);
        wallet2.withdraw(Asset.BRL, new BigDecimal("19"));

        assertDoesNotThrow(() -> repository.persistPendingTransactions(wallet2));
    }
}
