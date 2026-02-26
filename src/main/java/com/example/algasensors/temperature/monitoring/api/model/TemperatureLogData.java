package com.example.algasensors.temperature.monitoring.api.model;

import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureLogData {
    private UUID id;
    private TSID sensorId;
    private OffsetDateTime registeredAt;
    private Double value;
}
