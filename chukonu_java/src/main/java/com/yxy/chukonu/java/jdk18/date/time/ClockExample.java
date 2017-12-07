package com.yxy.chukonu.java.jdk18.date.time;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

public class ClockExample {

	public static void main(String[] args) {
		//Timezone sensitive and used for replacing System.currentTimeMillis() 
		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();

		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);   // legacy java.util.Date

	}

}
