package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementServiceTest {

    private MeasurementServiceImpl service;
    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        service = new MeasurementServiceImpl();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Test
    void testSampleWithNormalInput() {
        LocalDateTime startOfSampling = LocalDateTime.parse("2017-01-03T10:00:00", formatter);
        List<Measurement> inputMeasurements = Arrays.asList(
                new Measurement(LocalDateTime.parse("2017-01-03T10:01:00", formatter), 35.79, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:02:00", formatter), 98.78, MeasurementType.SPO2),
                new Measurement(LocalDateTime.parse("2017-01-03T10:06:00", formatter), 35.01, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:07:00", formatter), 96.49, MeasurementType.SPO2)
        );

        Map<MeasurementType, List<Measurement>> result = service.sample(startOfSampling, inputMeasurements);

        assertEquals(2, result.size());
        assertEquals(2, result.get(MeasurementType.TEMP).size());
        assertEquals(2, result.get(MeasurementType.SPO2).size());
        assertEquals(LocalDateTime.parse("2017-01-03T10:05:00", formatter), result.get(MeasurementType.TEMP).get(0).getMeasurementTime());
        assertEquals(LocalDateTime.parse("2017-01-03T10:10:00", formatter), result.get(MeasurementType.TEMP).get(1).getMeasurementTime());
    }

    @Test
    void testSampleWithMeasurementsBeforeStartOfSampling() {
        LocalDateTime startOfSampling = LocalDateTime.parse("2017-01-03T10:00:00", formatter);
        List<Measurement> inputMeasurements = Arrays.asList(
                new Measurement(LocalDateTime.parse("2017-01-03T09:59:59", formatter), 35.79, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:00:01", formatter), 98.78, MeasurementType.SPO2)
        );

        Map<MeasurementType, List<Measurement>> result = service.sample(startOfSampling, inputMeasurements);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(MeasurementType.SPO2));
        assertFalse(result.containsKey(MeasurementType.TEMP));
    }

    @Test
    void testSampleWithMultipleMeasurementsInSameInterval() {
        LocalDateTime startOfSampling = LocalDateTime.parse("2017-01-03T10:00:00", formatter);
        List<Measurement> inputMeasurements = Arrays.asList(
                new Measurement(LocalDateTime.parse("2017-01-03T10:01:00", formatter), 35.79, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:04:00", formatter), 35.80, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:04:59", formatter), 35.81, MeasurementType.TEMP)
        );

        Map<MeasurementType, List<Measurement>> result = service.sample(startOfSampling, inputMeasurements);

        assertEquals(1, result.size());
        assertEquals(1, result.get(MeasurementType.TEMP).size());
        assertEquals(35.81, result.get(MeasurementType.TEMP).get(0).getMeasurementValue());
    }

    @Test
    void testSampleWithEmptyInput() {
        LocalDateTime startOfSampling = LocalDateTime.parse("2017-01-03T10:00:00", formatter);
        List<Measurement> inputMeasurements = Arrays.asList();

        Map<MeasurementType, List<Measurement>> result = service.sample(startOfSampling, inputMeasurements);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSampleWithMeasurementsExactlyOnFiveMinuteIntervals() {
        LocalDateTime startOfSampling = LocalDateTime.parse("2017-01-03T10:00:00", formatter);
        List<Measurement> inputMeasurements = Arrays.asList(
                new Measurement(LocalDateTime.parse("2017-01-03T10:00:00", formatter), 35.79, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:05:00", formatter), 35.80, MeasurementType.TEMP),
                new Measurement(LocalDateTime.parse("2017-01-03T10:10:00", formatter), 35.81, MeasurementType.TEMP)
        );

        Map<MeasurementType, List<Measurement>> result = service.sample(startOfSampling, inputMeasurements);

        assertEquals(1, result.size());
        assertEquals(3, result.get(MeasurementType.TEMP).size());
        assertEquals(LocalDateTime.parse("2017-01-03T10:00:00", formatter), result.get(MeasurementType.TEMP).get(0).getMeasurementTime());
        assertEquals(LocalDateTime.parse("2017-01-03T10:05:00", formatter), result.get(MeasurementType.TEMP).get(1).getMeasurementTime());
        assertEquals(LocalDateTime.parse("2017-01-03T10:10:00", formatter), result.get(MeasurementType.TEMP).get(2).getMeasurementTime());
    }
}