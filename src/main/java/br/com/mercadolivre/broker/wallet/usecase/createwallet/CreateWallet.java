package br.com.mercadolivre.broker.wallet.usecase.createwallet;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class CreateWallet {

    private WalletRepository repository;

    public CreateWallet(WalletRepository repository) {
        this.repository = repository;
    }

    public CreateWalletOutput execute() {
        Wallet wallet = new Wallet(this.repository.generateCode());
        try {
            String code = repository.create(wallet);
            return new CreateWalletOutput().withCode(code);
        } catch (WalletNotCreatedException e) {
            return new CreateWalletOutput().withError(e.getMessage());
        }
    }

}
