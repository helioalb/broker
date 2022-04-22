package br.com.mercadolivre.broker.orderbook.vibranium.infra.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.BidInput;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.OrderInput;
import br.com.mercadolivre.broker.orderbook.vibranium.usecase.CreateAsk;
import br.com.mercadolivre.broker.orderbook.vibranium.usecase.createbid.CreateBid;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver implements RabbitListenerConfigurer {
    @Autowired
    private MatcherEngine matcherEngine;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        // Not overrided
    }

    @RabbitListener(queues = "${spring.rabbitmq.order.queue}")
    public void received(OrderInput order) {
        log.info("[ORDER][RECEIVED]" + order);
        try {
            if (order instanceof BidInput) {
                new CreateBid(matcherEngine).execute(order.getWalletCode(),
                                                    order.getQuantity(),
                                                    order.getPrice());
            } else {
                new CreateAsk(matcherEngine).execute(order.getWalletCode(),
                                                    order.getQuantity(),
                                                    order.getPrice());
            }
        } catch (Exception e) {
            log.error("[ORDER][ERROR]: %s" +  e.getMessage());
        }
    }
}
