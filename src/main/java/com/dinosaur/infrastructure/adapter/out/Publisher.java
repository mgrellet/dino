package com.dinosaur.infrastructure.adapter.out;


import com.dinosaur.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private final RabbitTemplate rabbitTemplate;

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MessageDto messageDto) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DINO_EXCHANGE,
                RabbitMQConfig.DINO_ROUTING_KEY,
                messageDto
        );
    }
}
