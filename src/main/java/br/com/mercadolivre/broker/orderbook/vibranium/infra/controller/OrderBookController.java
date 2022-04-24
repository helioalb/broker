package br.com.mercadolivre.broker.orderbook.vibranium.infra.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.AskInput;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.BidInput;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskEntity;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidEntity;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.service.OrderSender;

@RestController
@RequestMapping("orderbook/vibranium")
public class OrderBookController {
    @Autowired
    private OrderSender orderSender;

    @Autowired
    private BidDAO bidDAO;

    @Autowired
    private AskDAO askDAO;

    @PostMapping("bids")
    public ResponseEntity<Void> createBid(@Valid @RequestBody BidInput input) {
        orderSender.send(input);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("asks")
    public ResponseEntity<Void> createAsk(@Valid @RequestBody AskInput input) {
        orderSender.send(input);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("bids")
    public ResponseEntity<Page<BidEntity>> getAllBids(Pageable pageable) {
        Page<BidEntity> result = bidDAO.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("asks")
    public ResponseEntity<Page<AskEntity>> getAllAsks(Pageable pageable) {
        Page<AskEntity> result = askDAO.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
