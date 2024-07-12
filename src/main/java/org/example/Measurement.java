package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single measurement with time, value, and type.
 */
public class Measurement {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private LocalDateTime measurementTime;
    private Double measurementValue;
    private MeasurementType type;

    /**
     * Constructs a new Measurement.
     *
     * @param measurementTime The time of the measurement
     * @param measurementValue The value of the measurement
     * @param type The type of the measurement
     */
    public Measurement(LocalDateTime measurementTime, Double measurementValue, MeasurementType type) {
        this.measurementTime = measurementTime;
        this.measurementValue = measurementValue;
        this.type = type;
    }

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" + measurementTime.format(formatter) + ", " + type + ", " + measurementValue + "}";
    }
}




