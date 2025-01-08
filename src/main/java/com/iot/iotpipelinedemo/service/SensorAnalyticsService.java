package com.iot.iotpipelinedemo.service;

import com.iot.iotpipelinedemo.repository.SensorReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class SensorAnalyticsService {
    @Autowired
    private  SensorReadingRepository repository;


    @Cacheable(value = "sensorAverages", key = "#sensorType + #startTime + #endTime")
    public Double getAverageValue(String sensorType, Instant startTime, Instant endTime) {
        validateTimeRange(startTime, endTime);
       return repository.findAverageValue(sensorType, startTime, endTime);
    }

    @Cacheable(value = "sensorMinValues", key = "#sensorType + #startTime + #endTime")
    public Double getMinValue(String sensorType, Instant startTime, Instant endTime) {
        validateTimeRange(startTime, endTime);
        return repository.findMinValue(sensorType, startTime, endTime);
    }

    @Cacheable(value = "sensorMaxValues", key = "#sensorType + #startTime + #endTime")
    public Double getMaxValue(String sensorType, Instant startTime, Instant endTime) {
        validateTimeRange(startTime, endTime);
        return repository.findMaxValue(sensorType, startTime, endTime);
    }

    @Cacheable(value = "sensorMaxValues", key = "#sensorTypes + #startTime + #endTime")
    public Double getMaxValueGroupSensors(List<String> sensorTypes, Instant startTime, Instant endTime) {
        validateTimeRange(startTime, endTime);
        return repository.findMaxValueGroupOfSensors(sensorTypes, startTime, endTime);
    }

    private void validateTimeRange(Instant startTime, Instant endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}