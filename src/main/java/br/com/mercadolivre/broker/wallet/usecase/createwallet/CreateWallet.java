package br.com.mercadolivre.broker.wallet.usecase.createwallet;

import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class CreateWallet {

    private WalletRepository repository;

    public CreateWallet(WalletRepository repository) {
        this.repository = repository;
    }

    public CreateWalletOutput execute() {
        try {
            String code = repository.create();
            return new CreateWalletOutput().withCode(code);
        } catch (WalletNotCreatedException e) {
            return new CreateWalletOutput().withError(e.getMessage());
        }
    }

}
