package com.app.backend.backend_service.service.producer;

import com.app.backend.backend_service.config.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendSystemLogs(String message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_SYSTEM_LOGS, RabbitMQConfig.ROUTING_KEY_SYSTEM_LOGS, message);
        log.info("##### PRODUCER SENT SYSTEM LOGS : {} #####", message);
    }
}
