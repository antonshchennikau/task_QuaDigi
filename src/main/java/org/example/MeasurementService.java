package org.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The MeasurementService interface defines the contract for sampling measurements.
 */
public interface MeasurementService {

    /**
     * Samples the given measurements starting from the specified time.
     *
     * @param startOfSampling The start time for sampling
     * @param unsampledMeasurements The list of unsampled measurements
     * @return A map of MeasurementType to a list of sampled Measurements
     * @throws IllegalArgumentException if startOfSampling or unsampledMeasurements is null
     */
    Map<MeasurementType, List<Measurement>> sample(LocalDateTime startOfSampling, List<Measurement> unsampledMeasurements);
}