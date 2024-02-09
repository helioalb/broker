package me.helioalbano.broker.wallet.infra.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Wallet;
import me.helioalbano.broker.wallet.domain.repository.WalletRepository;
import me.helioalbano.broker.wallet.domain.service.TradeService;

@SpringBootTest()
@ActiveProfiles("test")
public class WalletRepositoryPostgresTest {

    @Autowired
    private WalletRepository repository;

    @Test
    void create() {
        assertNotNull(repository.create());
    }

    @Test
    void findByCode() {
        String code = repository.create();
        Optional<Wallet> wallet = repository.findByCode(code);
        assertTrue(wallet.isPresent());
        assertEquals(code, wallet.get().getCode());
    }

    @Test
    void persistPendingTransactions() {
        String leftWalletCode = repository.create();
        Optional<Wallet> leftWallet = repository.findByCode(leftWalletCode);
        leftWallet.ifPresent(wallet -> {
            wallet.deposit(Asset.BRL, new BigDecimal("1"));
            wallet.deposit(Asset.VIB, new BigDecimal("1"));
            repository.persistPendingTransactions(wallet);
        });

        String rightWalletCode = repository.create();
        Optional<Wallet> rightWallet = repository.findByCode(rightWalletCode);

        rightWallet.ifPresent(wallet -> {
            wallet.deposit(Asset.BRL, new BigDecimal("1"));
            wallet.deposit(Asset.VIB, new BigDecimal("1"));
            repository.persistPendingTransactions(wallet);
        });

        Optional<Wallet> leftWalletAfter = repository.findByCode(leftWalletCode);
        Optional<Wallet> rightWalletAfter = repository.findByCode(rightWalletCode);

        BigDecimal leftWalletAfterBRL = leftWalletAfter.get().findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal leftWalletAfterVIB = leftWalletAfter.get().findPartitionByAsset(Asset.VIB).getBalance();
        BigDecimal rightWalletAfterBRL = rightWalletAfter.get().findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal rightWalletAfterVIB = rightWalletAfter.get().findPartitionByAsset(Asset.VIB).getBalance();

        assertEquals(new BigDecimal("1.0000"), leftWalletAfterBRL);
        assertEquals(new BigDecimal("1.0000"), leftWalletAfterVIB);
        assertEquals(new BigDecimal("1.0000"), rightWalletAfterBRL);
        assertEquals(new BigDecimal("1.0000"), rightWalletAfterVIB);
    }

    @Test
    void deposit20withdraw19() {
        String code = repository.create();
        repository.findByCode(code).ifPresent(wallet -> {
            wallet.deposit(Asset.BRL, new BigDecimal("20"));
            repository.persistPendingTransactions(wallet);
        });
        repository.findByCode(code).ifPresent(wallet -> {
            wallet.withdraw(Asset.BRL, new BigDecimal("19"));
            assertDoesNotThrow(() -> repository.persistPendingTransactions(wallet));
        });
    }

    @Test
    void realize() {
        String leftWalletCode = repository.create();
        String rightWalletCode = repository.create();

        Optional<Wallet> leftWallet = repository.findByCode(leftWalletCode);
        Optional<Wallet> rightWallet = repository.findByCode(rightWalletCode);

        leftWallet.ifPresent(wallet -> {
            wallet.deposit(Asset.BRL, new BigDecimal("1000"));
            wallet.deposit(Asset.VIB, new BigDecimal("1000"));
            repository.persistPendingTransactions(wallet);
        });

        rightWallet.ifPresent(wallet -> {
            wallet.deposit(Asset.BRL, new BigDecimal("1000"));
            wallet.deposit(Asset.VIB, new BigDecimal("1000"));
            repository.persistPendingTransactions(wallet);
        });

        TradeService trade = new TradeService(leftWallet.get(), rightWallet.get());
        trade.transfer(Asset.BRL, new BigDecimal("100"), Asset.VIB, new BigDecimal("100"));

        repository.realize(trade);
        Optional<Wallet> leftWalletAfter = repository.findByCode(leftWalletCode);
        Optional<Wallet> rightWalletAfter = repository.findByCode(rightWalletCode);

        BigDecimal leftWalletAfterBRL = leftWalletAfter.get().findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal leftWalletAfterVIB = leftWalletAfter.get().findPartitionByAsset(Asset.VIB).getBalance();
        BigDecimal rightWalletAfterBRL = rightWalletAfter.get().findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal rightWalletAfterVIB = rightWalletAfter.get().findPartitionByAsset(Asset.VIB).getBalance();

        assertEquals(new BigDecimal("900.0000"), leftWalletAfterBRL);
        assertEquals(new BigDecimal("1100.0000"), leftWalletAfterVIB);
        assertEquals(new BigDecimal("1100.0000"), rightWalletAfterBRL);
        assertEquals(new BigDecimal("900.0000"), rightWalletAfterVIB);
    }
}
