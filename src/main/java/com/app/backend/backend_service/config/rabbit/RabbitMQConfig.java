package com.app.backend.backend_service.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //forgot password
    public static final String EXCHANGE_NOTIFICATION_FORGOT_PASSWORD = "notification_exchange_forgot_password";
    public static final String QUEUE_FORGOT_PASSWORD_NAME = "notification_forgot_password_queue";
    public static final String ROUTING_KEY_FORGOT_PASSWORD = "notification_key_forgot_password";

    //system logs
    public static final String EXCHANGE_SYSTEM_LOGS = "system_logs";
    public static final String QUEUE_SYSTEM_LOGS_QUEUE = "system_logs_queue";
    public static final String ROUTING_KEY_SYSTEM_LOGS = "system_logs_routing_key";

    @Bean(name = "notificationForgotPasswordExchange")
    public TopicExchange notificationForgotPasswordExchange(){
        return new TopicExchange(EXCHANGE_NOTIFICATION_FORGOT_PASSWORD);
    }

    @Bean(name = "notificationForgotPasswordQueue")
    public Queue notificationForgotPasswordQueue(){
        return new Queue(QUEUE_FORGOT_PASSWORD_NAME);
    }

    @Bean
    public Binding notificationForgotPasswordBinding(@Qualifier("notificationForgotPasswordQueue") Queue notificationForgotPasswordQueue,
                                                     @Qualifier("notificationForgotPasswordExchange") TopicExchange topicNotificationExchange){
        return BindingBuilder.bind(notificationForgotPasswordQueue)
                .to(topicNotificationExchange).with(ROUTING_KEY_FORGOT_PASSWORD);
    }

    @Bean(name = "systemLogsExchange")
    public TopicExchange systemLogsExchange(){
        return new TopicExchange(EXCHANGE_SYSTEM_LOGS);
    }

    @Bean(name = "systemLogsQueue")
    public Queue systemLogsQueue(){
        return new Queue(QUEUE_SYSTEM_LOGS_QUEUE);
    }

    @Bean
    public Binding systemLogsBinding(@Qualifier("systemLogsQueue") Queue systemLogsQueue,
                                     @Qualifier("systemLogsExchange") TopicExchange topicSystemLogsExchange){
        return BindingBuilder.bind(systemLogsQueue)
                .to(topicSystemLogsExchange).with(ROUTING_KEY_SYSTEM_LOGS);
    }
}
