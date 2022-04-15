package br.com.mercadolivre.broker.wallet.usecase.deposit;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Deposit {

    private WalletRepository repository;

    public Deposit(WalletRepository repository) {
        this.repository = repository;
    }

    public DepositOutput execute(DepositInput input) {
        Wallet wallet = this.repository.findByCode(input.getCode());
        if (wallet == null) return new DepositOutput().withWalletNotFoundError();

        try {
            wallet.deposit(input.getAsset(), input.getAmount());
            repository.persistPendingTransactions(wallet);
            return new DepositOutput().withSuccess();
        } catch (Exception e) {
            return new DepositOutput().withError(e.getMessage());
        }
    }

}
