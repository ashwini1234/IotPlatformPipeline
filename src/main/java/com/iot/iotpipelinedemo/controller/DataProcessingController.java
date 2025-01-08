package com.iot.iotpipelinedemo.controller;

import com.iot.iotpipelinedemo.entity.SensorReading;
import com.iot.iotpipelinedemo.service.DataProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensors")
@Slf4j
public class DataProcessingController {

    @Autowired
    private DataProcessingService dataProcessingService;

    @PostMapping("/readings")
    //@PreAuthorize("hasAuthority('ROLE_device')")
    public ResponseEntity<Void> processReading(@RequestBody SensorReading sensorReading) {
        System.out.println("Processing reading {}"+ sensorReading);
        dataProcessingService.processReading(sensorReading);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
