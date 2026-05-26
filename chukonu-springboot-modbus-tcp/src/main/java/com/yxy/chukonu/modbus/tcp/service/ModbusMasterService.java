package com.yxy.chukonu.modbus.tcp.service;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.yxy.chukonu.modbus.tcp.config.ModbusConfig;
import com.yxy.chukonu.modbus.tcp.config.ModbusPoint;
import com.yxy.chukonu.modbus.tcp.model.PointData;

@Service
@DependsOn("modbusSlave") 
public class ModbusMasterService {

    private final List<PointData> lastData = new ArrayList<>();

    static {
        Modbus.setAutoIncrementTransactionId(true);
    }

    // 每 2 秒采集一次
    @Scheduled(fixedRate = 2000)
    public void collectData() {
        ModbusMaster master = null;

        try {
            // 1. 创建连接
            TcpParameters tcp = new TcpParameters();
            tcp.setHost(InetAddress.getByName(ModbusConfig.HOST));
            tcp.setPort(ModbusConfig.PORT);
            tcp.setKeepAlive(true);

            master = ModbusMasterFactory.createModbusMasterTCP(tcp);
            master.connect();

            lastData.clear();

            // 2. 采集所有点位
            for (ModbusPoint point : ModbusPoint.values()) {
                if (point.getType() == ModbusPoint.Type.INT16) {
                    int[] val = master.readHoldingRegisters(ModbusConfig.SLAVE_ID, point.getAddress(), 1);
                    double realVal = val[0] / point.getRatio();
                    lastData.add(new PointData(point.getTagId(), point.getName(), point.getAddress(), realVal));
                }

                if (point.getType() == ModbusPoint.Type.FLOAT) {
                    int[] val = master.readHoldingRegisters(ModbusConfig.SLAVE_ID, point.getAddress(), 2);
                    int bits = (val[0] << 16) | (val[1] & 0xFFFF);
                    float realVal = Float.intBitsToFloat(bits);
                    lastData.add(new PointData(point.getTagId(), point.getName(), point.getAddress(), realVal));
                }
            }

            System.out.println("===== 采集成功 =====");
            lastData.forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("采集异常：" + e.getMessage());
        } finally {
            // 3. 采集完立即断开（适配 jlibmodbus slave）
            try {
                if (master != null && master.isConnected()) {
                    master.disconnect();
                }
            } catch (Exception ignored) {}
        }
    }

    public List<PointData> getLastData() {
        return lastData;
    }
}