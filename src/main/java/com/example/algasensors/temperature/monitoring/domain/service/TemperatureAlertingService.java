package com.example.algasensors.temperature.monitoring.domain.service;

import com.example.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.example.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.example.algasensors.temperature.monitoring.domain.model.SensorId;
import com.example.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureAlertingService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional(readOnly = true)
    public void alertTemperature(TemperatureLogData temperatureLogData) {
        SensorId sensorId = new SensorId(temperatureLogData.getSensorId());
        SensorAlert sensorAlert = sensorAlertRepository.findById(sensorId).orElse(null);

        if (sensorAlert == null) {
            log.warn("Sensor Alert not found: SensorId {} Temp {}", temperatureLogData.getSensorId(),
                    temperatureLogData.getValue());
            return;
        }

        if (sensorAlert.maxTemperatureExceeded(temperatureLogData.getValue())) {
            log.warn("Temperature Alert: SensorId {} Temp {} Max Threshold {}",
                    temperatureLogData.getSensorId(), temperatureLogData.getValue(),
                    sensorAlert.getMaxTemperature());
        }
        if (sensorAlert.minTemperatureExceeded(temperatureLogData.getValue())) {
            log.warn("Temperature Alert: SensorId {} Temp {} Min Threshold {}",
                    temperatureLogData.getSensorId(), temperatureLogData.getValue(),
                    sensorAlert.getMinTemperature());
        }
    }
}
