package br.com.mercadolivre.broker.wallet.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;

public class Partition {

    private Asset asset;
    private List<Transaction> transactions;

    public Partition(Asset asset) {
        this.asset = asset;
    }

    public void addTransaction(TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction(type, amount);
        if (this.transactions == null) {
            this.transactions = new ArrayList<>();
        }
        this.transactions.add(transaction);
    }

    public boolean is(Asset asset) {
        return this.asset.equals(asset);
    }

    public List<Transaction> pendingTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }
}
