package com.yxy.chukonu.mybatis.util;

import java.util.UUID;

public final class UUIDUtil {

	public static String next(){
		return UUID.randomUUID().toString() ;
	}
}
