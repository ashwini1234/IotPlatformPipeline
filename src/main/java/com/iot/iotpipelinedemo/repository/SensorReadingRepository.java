package com.iot.iotpipelinedemo.repository;

import com.iot.iotpipelinedemo.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    /**
     * Saves a record into database
     *
     * @param deviceId
     * @param readingValue
     * @param sensorType
     * @param timestamp
     */
    @Modifying
    @Query("INSERT INTO SensorReading (deviceId, readingValue, sensorType, timestamp) " +
            "VALUES (:deviceId, :readingValue, :sensorType, :timestamp)")
    void insertReading(
            @Param("deviceId") String deviceId,
                       @Param("readingValue") Double readingValue,
                       @Param("sensorType") String sensorType,
                       @Param("timestamp") Instant timestamp);

    /**
     * Get Average Value for a sensorType(HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorType
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT AVG(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType = :sensorType " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findAverageValue(@Param("sensorType") String sensorType,
                            @Param("startTime") Instant startTime,
                            @Param("endTime") Instant endTime);

    /**
     * Get Maximum Value for a sensorType(HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorType
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT MAX(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType = :sensorType " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findMaxValue(@Param("sensorType") String sensorType,
                        @Param("startTime") Instant startTime,
                        @Param("endTime") Instant endTime);

    /**
     * Get Minimum Value for a sensorType(HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorType
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT MIN(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType = :sensorType " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findMinValue(@Param("sensorType") String sensorType,
                        @Param("startTime") Instant startTime,
                        @Param("endTime") Instant endTime);

    /**
     * Get Average Value for a list of sensorTypes passed in (HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorTypes
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT AVG(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType IN :sensorTypes " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findAverageValueGroupOfSensors(@Param("sensorTypes") List<String> sensorTypes,
                                          @Param("startTime") Instant startTime,
                                          @Param("endTime") Instant endTime);

    /**
     * Get Maximum Value for a list of sensorTypes passed in (HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorTypes
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT MAX(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType IN :sensorTypes " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findMaxValueGroupOfSensors(@Param("sensorTypes") List<String> sensorTypes,
                                          @Param("startTime") Instant startTime,
                                          @Param("endTime") Instant endTime);


    /**
     * Get Minimum Value for a list of sensorTypes passed in (HUMIDITY_SENSOR,TEMPERATURE_SENSOR,PRESSURE_SENSOR) based on start time, end Time
     *
     * @param sensorTypes
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("SELECT MIN(sr.readingValue) FROM SensorReading sr WHERE sr.sensorType IN :sensorTypes " +
            "AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findMinValueGroupOfSensors(@Param("sensorTypes") List<String> sensorTypes,
                                          @Param("startTime") Instant startTime,
                                          @Param("endTime") Instant endTime);

}