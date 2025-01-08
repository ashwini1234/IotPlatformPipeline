package com.iot.iotpipelinedemo.service;

import com.iot.iotpipelinedemo.entity.SensorReading;
import com.iot.iotpipelinedemo.repository.SensorReadingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DataProcessingServiceTest {

    @InjectMocks
    private DataProcessingService service;

    @Mock
    private SensorReadingRepository repository;

    @Test
    public void testProcessReading() {
        SensorReading reading = new SensorReading(1L, "Device1", "TEMPERATURE_SENSOR", 25.5, Instant.now());
        service.processReading(reading);

        verify(repository, times(1)).insertReading(
                eq(reading.getDeviceId()),
                eq(reading.getReadingValue()),
                eq(reading.getSensorType()),
                eq(reading.getTimestamp())
        );
    }
}