package me.helioalbano.broker.wallet.usecase.createwallet;

import me.helioalbano.broker.wallet.domain.repository.WalletRepository;

public class CreateWallet {

    private WalletRepository repository;

    public CreateWallet(WalletRepository repository) {
        this.repository = repository;
    }

    public String execute() {
        return repository.create();
    }

}
