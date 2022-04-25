package br.com.mercadolivre.broker.trade.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Trade>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(tradeDao.findAll(pageable));
    }
}
