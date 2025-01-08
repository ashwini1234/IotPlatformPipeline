package com.iot.iotpipelinedemo.controller;

import com.iot.iotpipelinedemo.service.SensorAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class SensorAnalyticsController {
    @Autowired
    private  SensorAnalyticsService sensorAnalyticsService;

    @GetMapping("/average")
    public Double getAverage(
            @RequestParam String sensorType, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        return sensorAnalyticsService.getAverageValue(sensorType, startTime, endTime);
    }

    @GetMapping("/min")
    public Double getMin(@RequestParam String sensorType, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        return sensorAnalyticsService.getMinValue(sensorType, startTime, endTime);
    }

    @GetMapping("/max")
    public Double getMax(@RequestParam String sensorType, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        return sensorAnalyticsService.getMaxValue(sensorType, startTime, endTime);
    }

    @GetMapping("/groupMax")
    public Double getMaxGroupValue(@RequestParam List<String> sensorTypes, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        return sensorAnalyticsService.getMaxValueGroupSensors(sensorTypes, startTime, endTime);
    }
}