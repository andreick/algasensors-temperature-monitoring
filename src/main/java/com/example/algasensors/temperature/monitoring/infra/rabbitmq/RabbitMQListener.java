package com.example.algasensors.temperature.monitoring.infra.rabbitmq;

import com.example.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.example.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;

    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_MONITOR_TEMPERATURE, concurrency = "2-3")
    public void handleProcessingTemperature(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.monitorTemperature(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
