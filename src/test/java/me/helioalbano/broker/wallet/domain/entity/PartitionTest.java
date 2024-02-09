package me.helioalbano.broker.wallet.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Partition;
import me.helioalbano.broker.wallet.domain.enums.TransactionType;

public class PartitionTest {
    @Test
    void addFirstTransaction() {
        Partition partition = new Partition(Asset.BRL);
        partition.addTransaction(TransactionType.DEPOSIT, new BigDecimal("1"));
        assertEquals(1, partition.pendingTransactions().size());
    }

    @Test
    void addSecondTransaction() {
        Partition partition = new Partition(Asset.BRL);
        partition.addTransaction(TransactionType.DEPOSIT, new BigDecimal("1"));
        partition.addTransaction(TransactionType.WITHDRAW, new BigDecimal("1"));
        assertEquals(2, partition.pendingTransactions().size());
    }

    @Test
    void testIs() {
        Partition partition = new Partition(Asset.BRL);
        assertTrue(partition.is(Asset.BRL));
    }

    @Test
    void canWithdrawWhenBalanceIsSufficient() {
        Partition partition = new Partition(Asset.BRL, BigDecimal.ZERO);
        partition.addTransaction(TransactionType.DEPOSIT, new BigDecimal("10"));
        assertTrue(partition.canWithdraw(new BigDecimal("9")));
    }

    @Test
    void canWithdrawWhenBalanceIsNotSufficient() {
        Partition partition = new Partition(Asset.BRL, BigDecimal.ZERO);
        partition.addTransaction(TransactionType.DEPOSIT, new BigDecimal("1"));
        assertFalse(partition.canWithdraw(new BigDecimal("2")));
    }

    @Test
    void canWithdrawWhenResultingBalanceIsZero() {
        Partition partition = new Partition(Asset.BRL, new BigDecimal("1"));
        assertTrue(partition.canWithdraw(new BigDecimal("1")));
    }
}
