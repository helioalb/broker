package br.com.mercadolivre.broker.wallet.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.usecase.createwallet.CreateWallet;
import br.com.mercadolivre.broker.wallet.usecase.createwallet.CreateWalletOutput;

@RestController
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletRepository repository;

    @PostMapping
    public CreateWalletOutput createWallet() {
        return new CreateWallet(repository).execute();
    }
}
