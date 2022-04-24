package br.com.mercadolivre.broker.wallet.infra.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
import br.com.mercadolivre.broker.wallet.usecase.withdraw.Withdraw;
import br.com.mercadolivre.broker.wallet.usecase.withdraw.WithdrawInput;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("wallets")
@Slf4j
public class WalletController {
    private WalletRepository repository;

    public WalletController(WalletRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<CreateOutput> createWallet() {
        CreateOutput output = new CreateOutput();
        output.setId(new CreateWallet(repository).execute());
        log.info("[WALLET][CREATED] " + output.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping(path = "{id}/deposit")
    public ResponseEntity<Void> deposit(@Valid @RequestBody DepositInputDto in,
                                        @PathVariable String id) {
        DepositInput input = new DepositInput(id, in.getAsset(), in.getAmount());
        new Deposit(repository).execute(input);
        log.info("[DEPOSIT][REALIZED] " + in);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping(path = "{id}/withdraw")
    public ResponseEntity<Void> withdraw(@Valid @RequestBody WithdrawInputDto in,
                                         @PathVariable String id) {
        WithdrawInput input = new WithdrawInput(id, in.getAsset(), in.getAmount());
        new Withdraw(repository).execute(input);
        log.info("[WITHDRAW][REALIZED] " + in);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
