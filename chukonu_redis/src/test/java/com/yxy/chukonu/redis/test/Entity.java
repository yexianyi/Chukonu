package com.yxy.chukonu.redis.test;

import java.io.Serializable;

import lombok.Data;

@Data
public class Entity implements Serializable{
	private String name ;
	private String status ;
	
	public Entity(String name, String status) {
		this.name = name ;
		this.status = status ;
	}
}
