package br.com.mercadolivre.broker.wallet.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.controller.dto.DepositInputDto;
import br.com.mercadolivre.broker.wallet.usecase.createwallet.CreateWallet;
import br.com.mercadolivre.broker.wallet.usecase.createwallet.CreateWalletOutput;
import br.com.mercadolivre.broker.wallet.usecase.deposit.Deposit;
import br.com.mercadolivre.broker.wallet.usecase.deposit.DepositInput;
import br.com.mercadolivre.broker.wallet.usecase.deposit.DepositOutput;

@RestController
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletRepository repository;

    @PostMapping
    public CreateWalletOutput createWallet() {
        return new CreateWallet(repository).execute();
    }

    @PostMapping(path = "/{id}/deposit")
    public DepositOutput deposit(@RequestBody DepositInputDto deposit,
                                 @PathVariable String id) {
        DepositInput input =
            new DepositInput(id, deposit.getAsset(), deposit.getAmount());
        return new Deposit(repository).execute(input);
    }
}
