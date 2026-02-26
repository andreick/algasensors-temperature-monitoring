package com.example.algasensors.temperature.monitoring.domain.service;

import com.example.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.example.algasensors.temperature.monitoring.domain.model.SensorId;
import com.example.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.example.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.example.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.example.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.example.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void monitorTemperature(TemperatureLogData temperatureLogData) {
        SensorId sensorId = new SensorId(temperatureLogData.getSensorId());
        Optional<SensorMonitoring> sensor = sensorMonitoringRepository.findById(sensorId);

        if (!sensor.isPresent()) {
            log.warn("Sensor not found: SensorId {} Temp {}", temperatureLogData.getSensorId(),
                    temperatureLogData.getValue());
            return;
        }

        handleSensorMonitoring(temperatureLogData, sensor.get());
    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
        if (!sensor.isEnabled()) {
            log.info("Sensor Disabled: SensorId {} Temp {}", temperatureLogData.getSensorId(),
                    temperatureLogData.getValue());
            return;
        }

        sensor.setLastTemperature(temperatureLogData.getValue());
        sensor.setUpdatedAt(OffsetDateTime.now());
        sensorMonitoringRepository.save(sensor);

        TemperatureLog temperatureLog = TemperatureLog.builder()
                .id(new TemperatureLogId(temperatureLogData.getId()))
                .registeredAt(temperatureLogData.getRegisteredAt())
                .value(temperatureLogData.getValue())
                .sensorId(new SensorId(temperatureLogData.getSensorId()))
                .build();

        temperatureLogRepository.save(temperatureLog);
        log.info("Temperature Updated: SensorId {} Temp {}", temperatureLogData.getSensorId(),
                temperatureLogData.getValue());
    }
}
