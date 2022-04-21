package br.com.mercadolivre.broker.wallet.usecase.createwallet;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class CreateWallet {

    private WalletRepository repository;

    public CreateWallet(WalletRepository repository) {
        this.repository = repository;
    }

    public String execute() {
        return repository.create();
    }

}
