package br.com.mercadolivre.broker.orderbook.vibranium.infra.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.com.mercadolivre.broker.common.dto.TradeDto;
import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Trade;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.TradeSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeSenderRabbitmq implements TradeSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.trade.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.trade.routingkey}")
    private String routingKey;

    @Override
    public void send(Trade trade) {
        TradeDto dto = new TradeDto(Asset.VIB,
                                    Asset.BRL,
                                    trade.getBid().getWalletCode(),
                                    trade.getAsk().getWalletCode(),
                                    trade.getQuantity(),
                                    trade.getAmount());
        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
        log.info("[TRADE][SENT] " + dto);
    }

}
