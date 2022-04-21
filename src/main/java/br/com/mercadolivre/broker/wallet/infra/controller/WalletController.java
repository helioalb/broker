package br.com.mercadolivre.broker.wallet.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.controller.dto.CreateOutput;
import br.com.mercadolivre.broker.wallet.infra.controller.dto.DepositInputDto;
import br.com.mercadolivre.broker.wallet.infra.controller.dto.WithdrawInputDto;
import br.com.mercadolivre.broker.wallet.usecase.createwallet.CreateWallet;
import br.com.mercadolivre.broker.wallet.usecase.deposit.Deposit;
import br.com.mercadolivre.broker.wallet.usecase.deposit.DepositInput;
import br.com.mercadolivre.broker.wallet.usecase.deposit.DepositOutput;
import br.com.mercadolivre.broker.wallet.usecase.withdraw.Withdraw;
import br.com.mercadolivre.broker.wallet.usecase.withdraw.WithdrawInput;
import br.com.mercadolivre.broker.wallet.usecase.withdraw.WithdrawOutput;

@RestController
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletRepository repository;

    @PostMapping
    public ResponseEntity<CreateOutput> createWallet() {
        CreateOutput output = new CreateOutput();
        output.setId(new CreateWallet(repository).execute());
        return ResponseEntity.ok(output);
    }

    @PostMapping(path = "/{id}/deposit")
    public DepositOutput deposit(@RequestBody DepositInputDto deposit,
                                 @PathVariable String id) {
        try {
            DepositInput input =
                new DepositInput(id, deposit.getAsset(), deposit.getAmount());
            return new Deposit(repository).execute(input);
        } catch(Exception e) {
            return new DepositOutput().withError(e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/withdraw")
    public WithdrawOutput withdraw(@RequestBody WithdrawInputDto withdraw,
                                   @PathVariable String id) {
        try {
            WithdrawInput input =
                new WithdrawInput(id, withdraw.getAsset(), withdraw.getAmount());
            return new Withdraw(repository).execute(input);
        } catch(Exception e) {
            return new WithdrawOutput().withError(e.getMessage());
        }
    }
}
