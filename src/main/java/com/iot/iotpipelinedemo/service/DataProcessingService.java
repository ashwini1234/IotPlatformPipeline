package com.iot.iotpipelinedemo.service;

import com.iot.iotpipelinedemo.entity.SensorReading;
import com.iot.iotpipelinedemo.repository.SensorReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class DataProcessingService {

    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    @KafkaListener(
            topics = "${spring.kafka.topic}"
            , containerFactory = "${spring.kafka.container-factory}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void processReading(SensorReading reading) {
        try {
            sensorReadingRepository.insertReading(reading.getDeviceId(), reading.getReadingValue(),reading.getSensorType(), reading.getTimestamp());
        } catch (Exception exception) {
            System.out.println("Failed to process reading::"+ reading + "with exception:: " + exception.getMessage());
            //log.error("Failed to process reading: {}", reading, exception);
            throw exception;
        }
    }
}
