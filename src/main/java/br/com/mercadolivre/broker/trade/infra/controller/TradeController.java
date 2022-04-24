package br.com.mercadolivre.broker.trade.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.broker.trade.domain.entity.Trade;
import br.com.mercadolivre.broker.trade.infra.repository.dao.TradeDao;

@RestController
@RequestMapping("trades")
public class TradeController {

    @Autowired
    private TradeDao tradeDao;

    @GetMapping
    public ResponseEntity<List<Trade>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tradeDao.findAll());
    }
}
