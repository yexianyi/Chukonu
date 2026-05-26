package com.yxy.chukonu.modbus.tcp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yxy.chukonu.modbus.tcp.model.PointData;
import com.yxy.chukonu.modbus.tcp.service.ModbusMasterService;

import java.util.List;

@RestController
@RequestMapping("/modbus")
public class DataController {

	private final ModbusMasterService service;

	public DataController(ModbusMasterService service) {
		this.service = service;
	}

	@GetMapping("/data")
	public List<PointData> getData() {
		return service.getLastData();
	}
}
