package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Withdraw {

    private WalletRepository repository;

    public Withdraw(WalletRepository repository) {
        this.repository = repository;
    }

    public void execute(WithdrawInput input) {
        repository.findByCode(input.getCode()).ifPresentOrElse(wallet -> {
            wallet.withdraw(input.getAsset(), input.getAmount());
            this.repository.persistPendingTransactions(wallet);
        }, () -> { throw new IllegalArgumentException("wallet not found"); });
    }

}
