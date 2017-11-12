package com.yxy.chukonu.mybatis.util;

import java.sql.Timestamp;

public final class TimeUtil {

	public static Timestamp currentTime(){
		return new Timestamp(System.currentTimeMillis()) ;
	}
	
}
