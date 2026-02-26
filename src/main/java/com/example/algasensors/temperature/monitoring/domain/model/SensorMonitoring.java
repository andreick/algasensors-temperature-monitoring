package com.example.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SensorMonitoring {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "bigint"))
    private SensorId id;

    private Double lastTemperature;

    private OffsetDateTime updatedAt;

    private Boolean enabled;

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return Boolean.TRUE.equals(enabled);
    }
}
