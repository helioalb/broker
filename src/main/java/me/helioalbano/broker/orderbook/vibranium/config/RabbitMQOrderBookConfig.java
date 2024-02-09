package me.helioalbano.broker.orderbook.vibranium.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQOrderBookConfig {

    @Value("${spring.rabbitmq.order.queue}")
    private String orderQueue;

    @Value("${spring.rabbitmq.order.exchange}")
    private String orderExchange;

    @Value("${spring.rabbitmq.order.routingkey}")
    private String orderRoutingKey;

    @Bean
    Queue orderQueue() {
        return new Queue(orderQueue, true);
    }

    @Bean
    Exchange orderExchange() {
        return ExchangeBuilder
                .directExchange(orderExchange)
                .durable(true)
                .build();
    }

    @Bean
    Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(orderRoutingKey)
                .noargs();
    }

}
