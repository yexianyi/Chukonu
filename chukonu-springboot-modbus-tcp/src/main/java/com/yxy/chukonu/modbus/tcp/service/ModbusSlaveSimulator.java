package com.yxy.chukonu.modbus.tcp.service;


import java.net.InetAddress;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.intelligt.modbus.jlibmodbus.data.DataHolder;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlaveTCP;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.yxy.chukonu.modbus.tcp.config.ModbusConfig;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Modbus 从站（模拟设备，自动生成数据）
 * 
 * TagID（唯一标识）	点位名称		寄存器地址（相对）	数据类型		长度（寄存器个数）	比例系数ratio		单位		说明
		TE-001		环境温度			0					INT16		1					10.0			℃		原始值 / 10
		HU-001		环境湿度			1					INT16		1					1.0				%		原始值直接用
		PR-001		管道压力			2					INT16		1					1.0				kPa		原始值直接用
		VO-001		母线电压			3					FLOAT32		2					1.0				V		占 3、4 两寄存器
 */
@Service("modbusSlave")  
public class ModbusSlaveSimulator {

    private ModbusSlaveTCP slave;
    private final Random random = new Random();

    @PostConstruct
    public void start() {
        try {
            TcpParameters tcp = new TcpParameters();
            tcp.setHost(InetAddress.getByName(ModbusConfig.HOST));
            tcp.setPort(ModbusConfig.PORT);
            tcp.setKeepAlive(true);

            slave = new ModbusSlaveTCP(tcp);
            slave.setServerAddress(ModbusConfig.SLAVE_ID);
            slave.listen(); // 修复：高版本用 listen()

            System.out.println("===== Modbus 从站启动成功 port:" + ModbusConfig.PORT + " =====");

            DataHolder dataHolder = slave.getDataHolder();
            ModbusHoldingRegisters hr = new ModbusHoldingRegisters(100);
            dataHolder.setHoldingRegisters(hr);  

            new Thread(() -> {
                while (true) {
                    try {
                    	// 温度：随机 0~499，对应真实值 0~49.9℃（除以10）
                        dataHolder.writeHoldingRegister(0, random.nextInt(500));
                        // 湿度：随机 20~79，对应真实值 20~79%
                        dataHolder.writeHoldingRegister(1, random.nextInt(60) + 20);
                        // 压力：随机 100~999，对应真实值 100~999（不缩放）
                        dataHolder.writeHoldingRegister(2, random.nextInt(900) + 100);

                        // 电压（浮点）
                        float v = 220 + random.nextFloat() * 10;
                        int bits = Float.floatToIntBits(v);
                        dataHolder.writeHoldingRegister(3, (bits >> 16) & 0xFFFF);
                        dataHolder.writeHoldingRegister(4, bits & 0xFFFF);

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        try {
            if (slave != null) {
                slave.shutdown(); // 修复：高版本用 shutdown()
            }
        } catch (Exception e) {}
    }
}