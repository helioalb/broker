package br.com.mercadolivre.broker.wallet.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;

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
}
