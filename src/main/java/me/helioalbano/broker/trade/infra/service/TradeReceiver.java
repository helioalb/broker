package me.helioalbano.broker.trade.infra.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.helioalbano.broker.common.dto.TradeDto;
import me.helioalbano.broker.trade.domain.entity.Trade;
import me.helioalbano.broker.trade.repository.TradeRepository;
import me.helioalbano.broker.trade.usecase.CreateTrade;

@Service
@Slf4j
public class TradeReceiver implements RabbitListenerConfigurer {

    @Autowired
    private TradeRepository repository;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        // Not overrided
    }

    @RabbitListener(queues = "${spring.rabbitmq.trade.queue}")
    public void received(TradeDto tradeDto) {
        log.info("[TRADE][RECEIVED]" + tradeDto);
        try {
            Trade trade = new CreateTrade(repository)
                            .execute(tradeDto.getAssetTraded(),
                                     tradeDto.getAssetUsedToPay(),
                                     tradeDto.getBuyer(),
                                     tradeDto.getSeller(),
                                     tradeDto.getQuantity(),
                                     tradeDto.getAmount());
            log.info("[TRADE][PERSISTED] " + trade);
        } catch (Exception e) {
            log.error("[TRADE][ERROR] " + tradeDto + e.getMessage());
        }
    }
}
