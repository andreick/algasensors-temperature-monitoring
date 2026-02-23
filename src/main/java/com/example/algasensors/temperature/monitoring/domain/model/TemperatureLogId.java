package com.example.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class TemperatureLogId implements Serializable {

    private UUID value;

    public TemperatureLogId(@NonNull UUID value) {
        this.value = value;
    }

    public TemperatureLogId(@NonNull String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
