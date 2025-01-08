package com.iot.iotpipelinedemo.service;

import com.iot.iotpipelinedemo.repository.SensorReadingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorAnalyticsServiceTest {
    @InjectMocks
    private SensorAnalyticsService service;

    @Mock
    private SensorReadingRepository repository;

    @Test
    public void testGetAverageValue() {
        when(repository.findAverageValue(any(), any(), any())).thenReturn(25.5);
        Double avg = service.getAverageValue("TEMPERATURE_SENSOR", Instant.now(), Instant.now().plusSeconds(3600));
        assertEquals(25.5, avg);
    }
}
