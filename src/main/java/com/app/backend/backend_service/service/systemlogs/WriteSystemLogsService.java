package com.app.backend.backend_service.service.systemlogs;

import com.app.backend.backend_service.entities.SystemLogs;
import com.app.backend.backend_service.model.MessageBrokerRequestResponse;
import com.app.backend.backend_service.repository.SystemLogsRepository;
import com.app.backend.backend_service.service.producer.RabbitMQProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteSystemLogsService {

    private final SystemLogsRepository systemLogsRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final ObjectMapper objectMapper;

    public void write(String type, String message){
        log.info("write logs..");
        SystemLogs systemLogs = new SystemLogs();
        systemLogs.setType(type);
        systemLogs.setMessage(message);
        systemLogs.setTimestamp(LocalDateTime.now());
        var responseSystemLogs = systemLogsRepository.save(systemLogs);

        var messageBrokerRequestResponse = new MessageBrokerRequestResponse();
        messageBrokerRequestResponse.setId(UUID.randomUUID().toString());
        messageBrokerRequestResponse.setData(responseSystemLogs);
        try {
            rabbitMQProducer.sendSystemLogs(objectMapper.writeValueAsString(messageBrokerRequestResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
