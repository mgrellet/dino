package com.dinosaur.infrastructure.adapter.in;

import com.dinosaur.infrastructure.adapter.out.MessageDto;
import com.dinosaur.infrastructure.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    @RabbitListener(queues = RabbitMQConfig.DINO_QUEUE)
    public void consumeMessage(MessageDto messageDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(messageDto);
        log.info(jsonMessage);
    }
}
