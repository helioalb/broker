package br.com.mercadolivre.broker.orderbook.vibranium.infra.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.mercadolivre.broker.orderbook.vibranium.infra.dto.OrderInput;

@Service
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.order.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.order.routingkey}")
    private String routingKey;

    public void send(OrderInput order) {
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }
}
