package com.example.algasensors.temperature.monitoring.api.controller;

import com.example.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.example.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.example.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.example.algasensors.temperature.monitoring.domain.model.SensorId;
import com.example.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    public SensorAlertOutput getSensorAlert(@PathVariable("sensorId") TSID sensorId) {
        SensorAlert sensorAlert = findByIdOrThrow(sensorId);
        return SensorAlertOutput.from(sensorAlert);
    }

    @PutMapping
    public SensorAlertOutput createOrUpdateSensorAlert(
            @PathVariable("sensorId") TSID sensorId,
            @RequestBody SensorAlertInput input) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseGet(() -> SensorAlert.builder()
                        .id(new SensorId(sensorId))
                        .build());

        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlertRepository.saveAndFlush(sensorAlert);

        return SensorAlertOutput.from(sensorAlert);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensorAlert(@PathVariable("sensorId") TSID sensorId) {
        SensorAlert sensorAlert = findByIdOrThrow(sensorId);
        sensorAlertRepository.delete(sensorAlert);
    }

    private SensorAlert findByIdOrThrow(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
