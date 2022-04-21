package br.com.mercadolivre.broker.orderbook.vibranium.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.OrderInput;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.message.OrderSender;

@RestController
@RequestMapping("orderbook/vibranium")
public class OrderBookController {
    @Autowired
    private OrderSender orderSender;

    @PostMapping("/bids")
    public ResponseEntity<Void> createBid(OrderInput input) {
        input.setType("BID");
        orderSender.send(input);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/ask")
    public ResponseEntity<Void> createAsk(@RequestBody OrderInput input) {
        input.setType("ASK");
        orderSender.send(input);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
