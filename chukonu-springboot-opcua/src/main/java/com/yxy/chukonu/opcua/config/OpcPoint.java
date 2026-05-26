package com.yxy.chukonu.opcua.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpcPoint {
    TEMPERATURE("TEMP_SENSOR_001", "温度传感器1", "ns=2;s=Temperature", 10.0),
    HUMIDITY("HUMI_SENSOR_001", "湿度传感器1", "ns=2;s=Humidity", 1.0),
    PRESSURE("PRESS_SENSOR_001", "压力传感器1", "ns=2;s=Pressure", 1.0),
    VOLTAGE("VOLTAGE_METER_001", "电压仪表1", "ns=2;s=Voltage", 1.0);

    private final String tagId;
    private final String name;
    private final String nodeId;
    private final double ratio;
}