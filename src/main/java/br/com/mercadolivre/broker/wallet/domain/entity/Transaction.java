package br.com.mercadolivre.broker.wallet.domain.entity;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;
import lombok.Getter;

@Getter
public class Transaction {

    private TransactionType type;
    private BigDecimal amount;

    public Transaction(TransactionType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

}
