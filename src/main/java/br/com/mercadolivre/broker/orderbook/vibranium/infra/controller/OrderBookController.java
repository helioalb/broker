package br.com.mercadolivre.broker.orderbook.vibranium.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.controller.dto.CreateAskInputDto;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.controller.dto.CreateBidInputDto;
import br.com.mercadolivre.broker.orderbook.vibranium.usecase.CreateAsk;
import br.com.mercadolivre.broker.orderbook.vibranium.usecase.createbid.CreateBid;
import br.com.mercadolivre.broker.orderbook.vibranium.usecase.createbid.CreateBidOutput;

@RestController
@RequestMapping("orderbook/vibranium")
public class OrderBookController {
    @Autowired
    private MatcherEngine matcher;

    @PostMapping("/bids")
    public CreateBidOutput createBid(CreateBidInputDto input) {
        return new CreateBid(matcher).execute(input.getWalletCode(),
                                       input.getQuantity(),
                                       input.getPrice());
    }

    @PostMapping("/ask")
    public ResponseEntity createAsk(CreateAskInputDto input) {
        new CreateAsk(matcher).execute(input.getWalletCode(),
                                       input.getQuantity(),
                                       input.getPrice());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
