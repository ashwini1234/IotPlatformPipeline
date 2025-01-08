package com.iot.iotpipelinedemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SensorType {
    HUMIDITY_SENSOR,
    TEMPERATURE_SENSOR,
    PRESSURE_SENSOR
}
