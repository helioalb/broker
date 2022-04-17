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
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

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
        String leftWalletCode = repository.create();
        Wallet leftWallet = repository.findByCode(leftWalletCode);
        leftWallet.deposit(Asset.BRL, new BigDecimal("1"));
        leftWallet.deposit(Asset.VIB, new BigDecimal("1"));
        repository.persistPendingTransactions(leftWallet);

        String rightWalletCode = repository.create();
        Wallet rightWallet = repository.findByCode(rightWalletCode);
        rightWallet.deposit(Asset.BRL, new BigDecimal("1"));
        rightWallet.deposit(Asset.VIB, new BigDecimal("1"));
        repository.persistPendingTransactions(rightWallet);

        Wallet leftWalletAfter = repository.findByCode(leftWalletCode);
        Wallet rightWalletAfter = repository.findByCode(rightWalletCode);

        BigDecimal leftWalletAfterBRL = leftWalletAfter.findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal leftWalletAfterVIB = leftWalletAfter.findPartitionByAsset(Asset.VIB).getBalance();
        BigDecimal rightWalletAfterBRL = rightWalletAfter.findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal rightWalletAfterVIB = rightWalletAfter.findPartitionByAsset(Asset.VIB).getBalance();

        assertEquals(new BigDecimal("1.0000"), leftWalletAfterBRL);
        assertEquals(new BigDecimal("1.0000"), leftWalletAfterVIB);
        assertEquals(new BigDecimal("1.0000"), rightWalletAfterBRL);
        assertEquals(new BigDecimal("1.0000"), rightWalletAfterVIB);
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

    @Test
    void realize() {
        String leftWalletCode = repository.create();
        Wallet leftWallet = repository.findByCode(leftWalletCode);
        leftWallet.deposit(Asset.BRL, new BigDecimal("1000"));
        leftWallet.deposit(Asset.VIB, new BigDecimal("1000"));
        repository.persistPendingTransactions(leftWallet);

        String rightWalletCode = repository.create();
        Wallet rightWallet = repository.findByCode(rightWalletCode);
        rightWallet.deposit(Asset.BRL, new BigDecimal("1000"));
        rightWallet.deposit(Asset.VIB, new BigDecimal("1000"));
        repository.persistPendingTransactions(rightWallet);

        TradeService trade = new TradeService(leftWallet, rightWallet);
        trade.transfer(Asset.BRL, new BigDecimal("100"), Asset.VIB, new BigDecimal("100"));

        repository.realize(trade);
        Wallet leftWalletAfter = repository.findByCode(leftWalletCode);
        Wallet rightWalletAfter = repository.findByCode(rightWalletCode);

        BigDecimal leftWalletAfterBRL = leftWalletAfter.findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal leftWalletAfterVIB = leftWalletAfter.findPartitionByAsset(Asset.VIB).getBalance();
        BigDecimal rightWalletAfterBRL = rightWalletAfter.findPartitionByAsset(Asset.BRL).getBalance();
        BigDecimal rightWalletAfterVIB = rightWalletAfter.findPartitionByAsset(Asset.VIB).getBalance();

        assertEquals(new BigDecimal("900.0000"), leftWalletAfterBRL);
        assertEquals(new BigDecimal("1100.0000"), leftWalletAfterVIB);
        assertEquals(new BigDecimal("1100.0000"), rightWalletAfterBRL);
        assertEquals(new BigDecimal("900.0000"), rightWalletAfterVIB);
    }
}
