package com.example.algasensors.temperature.monitoring.api.controller;

import com.example.algasensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.example.algasensors.temperature.monitoring.domain.model.SensorId;
import com.example.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.example.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
public class SensorMonitoringController {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringOutput getSensorMonitoring(@PathVariable("sensorId") TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        return SensorMonitoringOutput.builder()
                .id(sensorMonitoring.getId().getValue())
                .enabled(sensorMonitoring.getEnabled())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updatedAt(sensorMonitoring.getUpdatedAt())
                .build();
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableMonitoring(@PathVariable("sensorId") TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        sensorMonitoring.enable();
        sensorMonitoringRepository.save(sensorMonitoring);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableMonitoring(@PathVariable("sensorId") TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        sensorMonitoring.disable();
        sensorMonitoringRepository.save(sensorMonitoring);
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .build());
    }
}
