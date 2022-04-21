package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import java.util.Optional;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Withdraw {

    private WalletRepository repository;

    public Withdraw(WalletRepository repository) {
        this.repository = repository;
    }

    public WithdrawOutput execute(WithdrawInput input) {
        Optional<Wallet> wallet = repository.findByCode(input.getCode());
        try {
            // TODO: Refactor code below
            if (wallet.isEmpty())
                throw new IllegalStateException("wallet not exists");
            wallet.get().withdraw(input.getAsset(), input.getAmount());
            this.repository.persistPendingTransactions(wallet.get());
            return new WithdrawOutput().withSuccess();
        } catch (Exception e) {
            return new WithdrawOutput().withError(e.getMessage());
        }
    }

}
