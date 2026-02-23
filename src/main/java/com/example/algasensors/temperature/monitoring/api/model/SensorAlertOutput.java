package com.example.algasensors.temperature.monitoring.api.model;

import com.example.algasensors.temperature.monitoring.domain.model.SensorAlert;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorAlertOutput {
    private TSID id;
    private Double maxTemperature;
    private Double minTemperature;

    public static SensorAlertOutput from(SensorAlert sensorAlert) {
        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}
