package br.com.mercadolivre.broker.wallet.infra.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

@SpringBootTest()
public class WalletRepositoryDBTest {

    @Autowired
    private WalletRepository repository;

    @Test
    void create() {
        assertNotNull(repository.create());
    }

    // @Test
    // void persistPendingTransactions() {
    //     Wallet wallet = new Wallet(repository.generateCode())
    // }
}
