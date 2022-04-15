package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class Withdraw {

    private WalletRepository repository;

    public Withdraw(WalletRepository repository) {
        this.repository = repository;
    }

    public WithdrawOutput execute(WithdrawInput input) {
        Wallet wallet = repository.findByCode(input.getCode());
        try {
            wallet.withdraw(input.getAsset(), input.getAmount());
            return new WithdrawOutput().withSuccess();
        } catch (Exception e) {
            return new WithdrawOutput().withError(e.getMessage());
        }
    }

}
