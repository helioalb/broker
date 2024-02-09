package me.helioalbano.broker.wallet.infra.controller;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Partition;
import me.helioalbano.broker.wallet.domain.repository.WalletRepository;
import me.helioalbano.broker.wallet.infra.controller.dto.CreateOutput;
import me.helioalbano.broker.wallet.infra.controller.dto.DepositInputDto;
import me.helioalbano.broker.wallet.infra.controller.dto.WithdrawInputDto;
import me.helioalbano.broker.wallet.usecase.createwallet.CreateWallet;
import me.helioalbano.broker.wallet.usecase.deposit.Deposit;
import me.helioalbano.broker.wallet.usecase.deposit.DepositInput;
import me.helioalbano.broker.wallet.usecase.withdraw.Withdraw;
import me.helioalbano.broker.wallet.usecase.withdraw.WithdrawInput;

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

    @GetMapping(path = "{code}")
    public ResponseEntity<Map<Asset, BigDecimal>> balance(@PathVariable String code) {

        Map<Asset, BigDecimal> balance = new EnumMap<>(Asset.class);

        repository.findByCode(code).ifPresentOrElse(wallet -> {
            for(Partition partition : wallet.getPartitions()) {
                balance.put(partition.getAsset(), partition.getBalance());
            }
        }, () -> { throw new IllegalArgumentException("wallet doesn't exists"); });

        return ResponseEntity.status(HttpStatus.OK).body(balance);
    }
}
