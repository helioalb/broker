package me.helioalbano.broker.trade.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTradeConfig {
    @Value("${spring.rabbitmq.trade.queue}")
    private String tradeQueue;

    @Value("${spring.rabbitmq.trade.exchange}")
    private String tradeExchange;

    @Value("${spring.rabbitmq.trade.routingkey}")
    private String tradeRoutingKey;

    @Bean
    Queue tradeQueue() {
        return new Queue(tradeQueue, true);
    }

    @Bean
    Exchange tradeExchange() {
        return ExchangeBuilder
                .directExchange(tradeExchange)
                .durable(true)
                .build();
    }

    @Bean
    Binding tradeBinding() {
        return BindingBuilder
                .bind(tradeQueue())
                .to(tradeExchange())
                .with(tradeRoutingKey)
                .noargs();
    }

}
