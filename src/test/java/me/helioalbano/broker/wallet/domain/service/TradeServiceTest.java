package me.helioalbano.broker.wallet.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Partition;
import me.helioalbano.broker.wallet.domain.entity.Wallet;
import me.helioalbano.broker.wallet.domain.service.TradeService;

public class TradeServiceTest {
    @Test
    void transfer() {
        Wallet left = buildWallet("leftfb82-6a47-4ccf-a494-201c8df1874b",
                                  "1000", "1000");
        Wallet right = buildWallet("righte82-6a47-4ccf-a494-201c8df1874b",
                                   "1000", "1000");
        TradeService trade = new TradeService(left, right);
        trade.transfer(Asset.BRL, new BigDecimal("10"),
                       Asset.VIB, new BigDecimal("20"));

        assertEquals(
            new BigDecimal("990"),
            trade.getLeftWallet().findPartitionByAsset(Asset.BRL).getBalance());

        assertEquals(
            new BigDecimal("1020"),
            trade.getLeftWallet().findPartitionByAsset(Asset.VIB).getBalance());

        assertEquals(
            new BigDecimal("1010"),
            trade.getRightWallet().findPartitionByAsset(Asset.BRL).getBalance());

        assertEquals(
            new BigDecimal("980"),
            trade.getRightWallet().findPartitionByAsset(Asset.VIB).getBalance());
    }

    private Wallet buildWallet(String leftWalletCode, String amountBRL, String amountVIB) {
        Partition p1 = new Partition(Asset.BRL, new BigDecimal(amountBRL));
        Partition p2 = new Partition(Asset.VIB, new BigDecimal(amountBRL));

        Set<Partition> partitions = new HashSet<>();
        partitions.add(p1);
        partitions.add(p2);

        return new Wallet(leftWalletCode, partitions);
    }
}
