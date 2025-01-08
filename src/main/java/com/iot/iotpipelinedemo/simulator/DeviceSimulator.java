package com.iot.iotpipelinedemo.simulator;

import com.iot.iotpipelinedemo.entity.SensorReading;
import com.iot.iotpipelinedemo.entity.SensorType;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class DeviceSimulator {

    private final KafkaTemplate<String, SensorReading> kafkaTemplate;
    private final ScheduledExecutorService executorService;
    private final Random random;
    private static final String TOPIC = "sensor-readings";
    private final AtomicLong sequence;

    public DeviceSimulator(KafkaTemplate<String, SensorReading> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.executorService = Executors.newScheduledThreadPool(3);
        this.random = new Random();
        this.sequence = new AtomicLong(400L);
    }

    @PostConstruct
    public void startSimulation() {
        // Simulate 3 different types of sensors
        simulateSensor(0L, "TEMP_B1_FL2_RM15", SensorType.TEMPERATURE_SENSOR.name(), 20.0, 35.0);
        simulateSensor(333L, "HUM_B1_FL2_RM15",SensorType.HUMIDITY_SENSOR.name(), 30.0, 100.0);
        simulateSensor(666L,"PRESS_B1_FL2_RM15", SensorType.PRESSURE_SENSOR.name(), 980.0, 1020.0);
    }

    private void simulateSensor(long initialDelay, String deviceId,String sensorType,
                                double minValue, double maxValue) {
        executorService.scheduleAtFixedRate(() -> {
            try {
                double value = minValue + random.nextDouble() * (maxValue - minValue);
                SensorReading reading = new SensorReading(
                        sequence.incrementAndGet(),
                        deviceId,
                        sensorType,
                        value,
                        Instant.now()
                );

                kafkaTemplate.send(TOPIC, reading)
                        .whenComplete((result, ex) -> {
                            if (ex == null) {
                                System.out.println("Sent reading:: " +reading);
                                //log.debug("Sent reading:: ", reading);
                            } else {
                                System.out.println("Failed to send reading:: " +reading + ex.getMessage());
                                //log.error("Failed to send reading: {}", reading, ex);
                            }
                        });
            } catch (Exception exception){
                //log.error("Error simulating {}: {}", sensorType, exception.getMessage());
            }
        }, initialDelay, 1000, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void stopSimulation() {
        //log.info("Stopping device simulation...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}