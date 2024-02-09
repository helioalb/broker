package me.helioalbano.broker.wallet.domain.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Partition;
import me.helioalbano.broker.wallet.domain.entity.Wallet;
import me.helioalbano.broker.wallet.domain.exception.SendTransferException;
import me.helioalbano.broker.wallet.domain.exception.WithdrawException;

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
    void witdrawAmountLessThanBalance() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        assertDoesNotThrow(
            () -> wallet.withdraw(Asset.BRL, new BigDecimal("1"))
        );
    }

    @Test
    void witdrawAmountEqualsBalance() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        assertDoesNotThrow(
            () -> wallet.withdraw(Asset.BRL, new BigDecimal("2"))
        );
    }

    @Test
    void witdrawAmountGreaterThanBalance() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        BigDecimal amount = new BigDecimal("3");

        Exception e = assertThrows(WithdrawException.class,
            () -> wallet.withdraw(Asset.BRL, amount));

        assertEquals("insufficient balance", e.getMessage());
    }

    @Test
    void sentTransfer() {
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Set<Partition> partitions = new HashSet<>();
        partitions.add(new Partition(Asset.BRL, new BigDecimal("2")));
        Wallet wallet = new Wallet(code, partitions);
        BigDecimal amount = new BigDecimal("3");

        Exception e = assertThrows(SendTransferException.class,
            () -> wallet.sendTransfer(Asset.BRL, amount));

        assertEquals("insufficient balance", e.getMessage());
    }

    @Test
    void validSequenceOfTransactionsInSamePartition() {
        Wallet wallet = new Wallet("93d1ab2f-968d-4d7f-8050-b92b22c11e64");
        BigDecimal amount = new BigDecimal("10");
        wallet.deposit(Asset.BRL, amount);
        assertDoesNotThrow(() -> wallet.withdraw(Asset.BRL, amount));
    }

    @Test
    void validSequenceOfTransactionsInDifferentPartition() {
        Wallet wallet = new Wallet("93d1ab2f-968d-4d7f-8050-b92b22c11e64");
        wallet.deposit(Asset.BRL, new BigDecimal("100"));
        wallet.deposit(Asset.VIB, new BigDecimal("10"));
        assertDoesNotThrow(() -> wallet.withdraw(Asset.BRL, new BigDecimal("90")));
        assertDoesNotThrow(() -> wallet.withdraw(Asset.VIB, new BigDecimal("9")));
    }

    @Test
    void transferReceived() {
        Wallet wallet = new Wallet("93d1ab2f-968d-4d7f-8050-b92b22c11e64");
        wallet.receiveTransfer(Asset.BRL, new BigDecimal("100"));
        wallet.receiveTransfer(Asset.VIB, new BigDecimal("10"));
        assertDoesNotThrow(() -> wallet.withdraw(Asset.BRL, new BigDecimal("90")));
        assertDoesNotThrow(() -> wallet.withdraw(Asset.VIB, new BigDecimal("9")));
    }
}
