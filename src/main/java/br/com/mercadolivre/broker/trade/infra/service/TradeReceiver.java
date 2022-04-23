package br.com.mercadolivre.broker.trade.infra.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercadolivre.broker.common.dto.TradeDto;
import br.com.mercadolivre.broker.trade.domain.entity.Trade;
import br.com.mercadolivre.broker.trade.repository.TradeRepository;
import br.com.mercadolivre.broker.trade.usecase.CreateTrade;
import lombok.extern.slf4j.Slf4j;

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
