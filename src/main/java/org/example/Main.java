package org.example;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.parse;

public class Main {
    public static void main(String[] args) {
        MeasurementService service = new MeasurementServiceImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        List<Measurement> inputMeasurements = Arrays.asList(
                new Measurement(parse("2017-01-03T10:04:45", formatter), 35.79, MeasurementType.TEMP),
                new Measurement(parse("2017-01-03T10:01:18", formatter), 98.78, MeasurementType.SPO2),
                new Measurement(parse("2017-01-03T10:09:07", formatter), 35.01, MeasurementType.TEMP),
                new Measurement(parse("2017-01-03T10:03:34", formatter), 96.49, MeasurementType.SPO2),
                new Measurement(parse("2017-01-03T10:02:01", formatter), 35.82, MeasurementType.TEMP),
                new Measurement(parse("2017-01-03T10:05:00", formatter), 97.17, MeasurementType.SPO2),
                new Measurement(parse("2017-01-03T10:05:01", formatter), 95.08, MeasurementType.SPO2)
        );

        System.out.println("INPUT:");
        inputMeasurements.forEach(System.out::println);

        Map<MeasurementType, List<Measurement>> outputMeasurements = service.sample(parse("2017-01-03T10:00:00", formatter), inputMeasurements);

        System.out.println("OUTPUT:");
        outputMeasurements.forEach((type, measurements) -> measurements.forEach(System.out::println));
    }
}