package com.yxy.chukonu.opcua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yxy.chukonu.opcua.config.OpcPoint;
import com.yxy.chukonu.opcua.server.OpcUaServerService;

@RestController
@RequestMapping("/api/opcua/test")
public class OpcUaTestController {

    @Autowired
    private OpcUaServerService opcUaServerService;

    /**
     * 测试入口 1：通过设备点位枚举写入
     * 示例：POST http://localhost:8080/api/opcua/test/write-by-enum?point=TEMPERATURE&value=88.5
     */
    @PostMapping("/write-by-enum")
    public ResponseEntity<String> writeByEnum(@RequestParam OpcPoint point, @RequestParam Object value) {
        // 根据你传入的数值类型做简单转换（OPC UA 强类型对应）
        Object typedValue = parseValue(value);
        boolean success = opcUaServerService.writeNodeValue(point, typedValue);
        return success ? ResponseEntity.ok("Enum写入成功") : ResponseEntity.badRequest().body("Enum写入失败");
    }

    /**
     * 测试入口 2：通过纯字符串 NodeId 写入
     * 示例：POST http://localhost:8080/api/opcua/test/write-by-string?nodeId=Devices.Temperature&value=99.9
     */
    @PostMapping("/write-by-string")
    public ResponseEntity<String> writeByString(@RequestParam String nodeId, @RequestParam Object value) {
        Object typedValue = parseValue(value);
        boolean success = opcUaServerService.writeNodeValue(nodeId, typedValue);
        return success ? ResponseEntity.ok("String ID写入成功") : ResponseEntity.badRequest().body("String ID写入失败");
    }

    private Object parseValue(Object value) {
        if (value == null) return null;
        String str = value.toString();
        // 尝试转成 Double 或 Integer，适配 Milo 的 Variant 强类型规范
        try {
            if (str.contains(".")) {
                return Double.parseDouble(str);
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return str;
        }
    }
}