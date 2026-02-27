package com.example.algasensors.temperature.monitoring.infra.rabbitmq;

import com.example.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.example.algasensors.temperature.monitoring.domain.service.TemperatureAlertingService;
import com.example.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;
    private final TemperatureAlertingService temperatureAlertingService;

    // @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_MONITOR_TEMPERATURE, concurrency = "2-3")
    public void handleProcessingTemperature(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.monitorTemperature(temperatureLogData);
        // Thread.sleep(Duration.ofSeconds(5));
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TEMPERATURE_ALERT, concurrency = "2-3")
    public void handleTemperatureAlert(@Payload TemperatureLogData temperatureLogData) {
        temperatureAlertingService.alertTemperature(temperatureLogData);
    }
}
