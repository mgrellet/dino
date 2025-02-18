package com.dinosaur.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String DINO_QUEUE = "dinosaur.queue";
    public static final String DINO_EXCHANGE = "dinosaur.exchange";
    public static final String DINO_ROUTING_KEY = "dinosaur.routing.key";

    @Bean
    public Queue personaQueue() {
        return new Queue(DINO_QUEUE, false);
    }

    @Bean
    public DirectExchange personaExchange() {
        return new DirectExchange(DINO_EXCHANGE);
    }

    @Bean
    public Binding bindingPersona(Queue personaQueue, DirectExchange personaExchange) {
        return BindingBuilder.bind(personaQueue)
                .to(personaExchange)
                .with(DINO_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
