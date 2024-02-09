package me.helioalbano.broker.orderbook.vibranium.infra.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.helioalbano.broker.orderbook.vibranium.domain.service.MatcherEngine;
import me.helioalbano.broker.orderbook.vibranium.infra.dto.BidInput;
import me.helioalbano.broker.orderbook.vibranium.infra.dto.OrderInput;
import me.helioalbano.broker.orderbook.vibranium.usecase.CreateAsk;
import me.helioalbano.broker.orderbook.vibranium.usecase.CreateBid;

@Component
@Slf4j
public class OrderReceiver implements RabbitListenerConfigurer {

    private MatcherEngine matcherEngine;

    public OrderReceiver(MatcherEngine matcherEngine) {
        this.matcherEngine = matcherEngine;
    }

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
            log.error("[ORDER][ERROR]: " +  e.getMessage());
        }
    }
}
