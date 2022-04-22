package br.com.mercadolivre.broker.wallet.usecase.deposit;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Deposit {

    private WalletRepository repository;

    public Deposit(WalletRepository repository) {
        this.repository = repository;
    }

    public void execute(DepositInput input) {
        this.repository.findByCode(input.getCode()).ifPresent(wallet -> {
            wallet.deposit(input.getAsset(), input.getAmount());
            repository.persistPendingTransactions(wallet);
        });
    }

}
