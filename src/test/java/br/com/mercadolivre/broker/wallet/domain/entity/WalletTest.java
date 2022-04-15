package br.com.mercadolivre.broker.wallet.domain.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.exception.WithdrawNotRealizedException;

public class WalletTest {
    @Test
    void codeWithInvalidLength() {
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> new Wallet("abcd-efgh")
        );

        assertEquals("wallet code invalid", e.getMessage());
    }

    @Test
    void depositInAnInexistentPartion() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        Wallet wallet = new Wallet(code, partitions);
        wallet.deposit(Asset.BRL, new BigDecimal("123.456"));
        assertEquals(1, wallet.numberOfManagedAssets());
    }

    @Test
    void witdrawSucces() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        assertDoesNotThrow(
            () -> wallet.withdraw(Asset.BRL, new BigDecimal("1"))
        );
    }

    @Test
    void witdrawWithException() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        BigDecimal amount = new BigDecimal("3");

        Exception e = assertThrows(WithdrawNotRealizedException.class,
            () -> wallet.withdraw(Asset.BRL, amount));

        assertEquals("insufficient balance", e.getMessage());
    }
}
