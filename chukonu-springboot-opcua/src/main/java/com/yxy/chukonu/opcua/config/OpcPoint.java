package com.yxy.chukonu.opcua.config;

import lombok.Data;

@Data
public class OpcPoint {
    private final String tagId;
    private final String name;
    private final String nodeId;
    private final double ratio;
}