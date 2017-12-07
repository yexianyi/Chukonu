package com.yxy.chukonu.java.jdk18.date.time;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class LocalTimeExample {

	public static void main(String[] args) {

		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		
		System.out.println(now1.isBefore(now2));  // false
		
		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
		
		System.out.println(hoursBetween);       // -3
		System.out.println(minutesBetween);     // -239


		LocalTime late = LocalTime.of(23, 59, 59);
		System.out.println(late);       // 23:59:59
		
		DateTimeFormatter germanFormatter = DateTimeFormatter
									        .ofLocalizedTime(FormatStyle.SHORT)
									        .withLocale(Locale.GERMAN);
		
		LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
		System.out.println(leetTime);   // 13:37

		
	}

}
