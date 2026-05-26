package com.yxy.chukonu.modbus.tcp.config;

import lombok.Getter;

@Getter
public enum ModbusPoint {

    // TagId                名称         地址  类型    系数
    TEMP_SENSOR_001("温度传感器1",        0, Type.INT16, 10.0),
    HUMI_SENSOR_001("湿度传感器1",        1, Type.INT16, 1.0),
    PRESS_SENSOR_001("压力传感器1",       2, Type.INT16, 1.0),
    VOLTAGE_METER_001("电压仪表1",        3, Type.FLOAT, 1.0);

    private final String tagId;
    private final String name;
    private final int address;
    private final Type type;
    private final double ratio;

    ModbusPoint(String name, int address, Type type, double ratio) {
        this.tagId = name(); // 枚举名 = TagId
        this.name = name;
        this.address = address;
        this.type = type;
        this.ratio = ratio;
    }

    public enum Type {
        INT16, FLOAT
    }
}