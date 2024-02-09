package me.helioalbano.broker.wallet.domain.entity;

import java.math.BigDecimal;

import lombok.Getter;
import me.helioalbano.broker.wallet.domain.enums.TransactionType;

@Getter
public class Transaction {

    private TransactionType type;
    private BigDecimal amount;

    public Transaction(TransactionType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

}
