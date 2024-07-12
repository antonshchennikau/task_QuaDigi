package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the MeasurementService interface.
 * This class provides functionality to sample measurements at 5-minute intervals.
 */
public class MeasurementServiceImpl implements MeasurementService {

    /**
     * Samples the given measurements starting from the specified time.
     *
     * @param startOfSampling The start time for sampling
     * @param unsampledMeasurements The list of unsampled measurements
     * @return A map of MeasurementType to a list of sampled Measurements
     * @throws IllegalArgumentException if startOfSampling or unsampledMeasurements is null
     */
    @Override
    public Map<MeasurementType, List<Measurement>> sample(LocalDateTime startOfSampling, List<Measurement> unsampledMeasurements) {
        if (startOfSampling == null || unsampledMeasurements == null) {
            throw new IllegalArgumentException("startOfSampling and unsampledMeasurements must not be null");
        }

        Map<MeasurementType, NavigableMap<LocalDateTime, Measurement>> sampledData = new HashMap<>();

        for (Measurement measurement : unsampledMeasurements) {
            if(measurement.getMeasurementTime().isBefore(startOfSampling)){
                continue;
            }

            MeasurementType type = measurement.getType();
            LocalDateTime truncatedTime = roundToNearestFiveMinutes(measurement.getMeasurementTime());

            sampledData.putIfAbsent(type, new TreeMap<>());
            NavigableMap<LocalDateTime, Measurement> typeData = sampledData.get(type);

            if (!typeData.containsKey(truncatedTime)
                    || typeData.get(truncatedTime).getMeasurementTime().isBefore(measurement.getMeasurementTime())) {
                typeData.put(truncatedTime, measurement);
            }
        }

        return sampledData.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(innerEntry -> new Measurement(
                                innerEntry.getKey(),
                                innerEntry.getValue().getMeasurementValue(),
                                entry.getKey()
                        ))
                )
                .collect(Collectors.groupingBy(
                        Measurement::getType,
                        () -> new TreeMap<>(Comparator.naturalOrder()),
                        Collectors.toList()
                ));
    }

    /**
     * Rounds the given LocalDateTime to the nearest 5-minute interval.
     *
     * @param dateTime The LocalDateTime to round
     * @return The rounded LocalDateTime
     */
    private LocalDateTime roundToNearestFiveMinutes(LocalDateTime dateTime) {
        int minute = dateTime.getMinute();
        if (minute % 5 == 0 && dateTime.getSecond() == 0) {
            return dateTime.truncatedTo(ChronoUnit.MINUTES);
        }
        return dateTime.withSecond(0).withNano(0).plusMinutes(5 - minute % 5);
    }
}





