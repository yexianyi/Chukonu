package com.yxy.chukonu.modbus.tcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointData {
	private String tagId;
	private String pointName;
	private int address;
	private double value;
}