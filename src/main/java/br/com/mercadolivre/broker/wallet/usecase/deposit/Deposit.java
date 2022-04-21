package br.com.mercadolivre.broker.wallet.usecase.deposit;

import java.util.Optional;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Deposit {

    private WalletRepository repository;

    public Deposit(WalletRepository repository) {
        this.repository = repository;
    }

    public DepositOutput execute(DepositInput input) {
        Optional<Wallet> wallet = this.repository.findByCode(input.getCode());
        if (wallet.isEmpty()) return new DepositOutput().withWalletNotFoundError();

        try {
            wallet.get().deposit(input.getAsset(), input.getAmount());
            repository.persistPendingTransactions(wallet.get());
            return new DepositOutput().withSuccess();
        } catch (Exception e) {
            return new DepositOutput().withError(e.getMessage());
        }
    }

}
