package me.helioalbano.broker.orderbook.vibranium.infra.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import me.helioalbano.broker.common.dto.TradeDto;
import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.orderbook.vibranium.domain.entity.Trade;
import me.helioalbano.broker.orderbook.vibranium.domain.service.TradeSender;

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
