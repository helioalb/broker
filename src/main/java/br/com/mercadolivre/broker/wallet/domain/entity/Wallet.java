package br.com.mercadolivre.broker.wallet.domain.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;

public class Wallet {

    private String code;
    private Set<Partition> partitions;

    public Wallet(String code) {
        setCode(code);
        this.partitions = new HashSet<>();
    }

    public Wallet(String code, Set<Partition> partitions) {
        setCode(code);
        this.partitions = partitions;
    }

    private static final int UUID_LENGTH = 36;

    private void setCode(String code) {
        if (code.length() != UUID_LENGTH)
            throw new IllegalArgumentException("wallet code invalid");
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void deposit(Asset asset, BigDecimal amount) {
        Partition partition = findPartitionByAsset(asset);
        partition.addTransaction(TransactionType.DEPOSIT, amount);
    }

    private Partition findPartitionByAsset(Asset asset) {
        for (Partition partition : partitions) {
            if (partition.is(asset)) return partition;
        }
        Partition newPartition = new Partition(asset);
        this.partitions.add(newPartition);
        return newPartition;
    }

    public Set<Partition> getPartitions() {
        return Collections.unmodifiableSet(this.partitions);
    }

    public int numberOfManagedAssets() {
        return partitions == null || partitions.isEmpty() ? 0 : partitions.size();
    }

}
