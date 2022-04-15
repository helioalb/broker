package br.com.mercadolivre.broker.wallet.domain.enums;

public enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER_RECEIVED, TRANSFER_SENT;

    public boolean isIncome() {
        return name().equals("DEPOSIT") || name().equals("TRANSFER_RECEIVED");
    }
}
