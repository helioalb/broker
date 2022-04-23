package br.com.mercadolivre.broker.wallet.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;
import br.com.mercadolivre.broker.wallet.domain.exception.TransactionException;

public class Partition {

    private Asset asset;
    private List<Transaction> transactions;
    private BigDecimal balance;

    public Partition(Asset asset) {
        this.asset = asset;
        setBalance(BigDecimal.ZERO);
    }

    public Partition(Asset asset, BigDecimal balance) {
        this.asset = asset;
        setBalance(balance);
    }

    private void setBalance(BigDecimal balance) {
        balance = (balance != null) ? balance : BigDecimal.ZERO;

        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Negative balance");
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void addTransaction(TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction(type, amount);
        if (this.transactions == null) {
            this.transactions = new ArrayList<>();
        }
        BigDecimal newBalance = calculateNewBalance(type, amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new TransactionException("insufficient balance");
        this.transactions.add(transaction);
        this.balance = newBalance;
    }

    private BigDecimal calculateNewBalance(TransactionType type, BigDecimal amount) {
        if (type.isIncome())
            return this.balance.add(amount);
        return this.balance.subtract(amount);
    }

    public boolean is(Asset asset) {
        return this.asset.equals(asset);
    }

    public List<Transaction> pendingTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }

    public boolean hasPendingTransactions() {
        return this.transactions != null && !this.transactions.isEmpty();
    }

    public Asset getAsset() {
        return this.asset;
    }

    public boolean canWithdraw(BigDecimal amount) {
        return amount.compareTo(this.balance) <= 0;
    }

    public void clearTransactions() {
        this.transactions = null;
    }
}
