package com.example.algasensors.temperature.monitoring.infra.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String MONITOR_TEMPERATURE = "temperature-monitoring.monitor-temperature.v1";

    public static final String QUEUE_MONITOR_TEMPERATURE = MONITOR_TEMPERATURE + ".q";
    public static final String QUEUE_TEMPERATURE_ALERT = "temperature-monitoring-alert-temperature.v1.q";

    private static final String TEMPERATURE_RECEIVED_EXCHANGE = "temperature-processing.temperature-received.v1.e";

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queueMonitorTemperature() {
        return QueueBuilder.durable(QUEUE_MONITOR_TEMPERATURE).build();
    }

    @Bean
    Queue queueTemperatureAlert() {
        return QueueBuilder.durable(QUEUE_TEMPERATURE_ALERT).build();
    }

    FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange(TEMPERATURE_RECEIVED_EXCHANGE).build();
    }

    @Bean
    Binding bindingMonitorTemperature() {
        return BindingBuilder.bind(queueMonitorTemperature()).to(exchange());
    }

    @Bean
    Binding bindingTemperatureAlert() {
        return BindingBuilder.bind(queueTemperatureAlert()).to(exchange());
    }
}
