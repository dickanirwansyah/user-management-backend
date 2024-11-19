package com.app.backend.backend_service.service.consumer;

import com.app.backend.backend_service.config.rabbit.RabbitMQConfig;

import com.app.backend.backend_service.entities.SystemLogs;
import com.app.backend.backend_service.exception.MessageBrokerException;
import com.app.backend.backend_service.model.MessageBrokerRequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SYSTEM_LOGS_QUEUE)
    public void receiveSystemLogs(String message){
        try{
            Thread.sleep(1000);
            log.info("##### CONSUMER RECEIVE SYSTEM LOGS : {} #####", message);
        }catch (MessageBrokerException e){
            log.error("Message broker error processing topic: ",e);
        }catch (Exception e){
            log.error("something went wrong processing topic : ",e);
        }
    }

    private MessageBrokerRequestResponse<SystemLogs> parseMessage(String message){
        try {
            return objectMapper.readValue(message, objectMapper.getTypeFactory()
                    .constructParametricType(MessageBrokerRequestResponse.class, SystemLogs.class));
        } catch (JsonProcessingException e) {
            log.error("Error parsing system logs message : ",e);
            throw new RuntimeException(e);
        }
    }
}
