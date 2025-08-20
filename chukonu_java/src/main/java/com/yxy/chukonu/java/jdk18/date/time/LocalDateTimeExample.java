/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.java.jdk18.date.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

public class LocalDateTimeExample {

	public static void main(String[] args) {
		LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
		
		DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
		System.out.println(dayOfWeek);      // WEDNESDAY
		
		Month month = sylvester.getMonth();
		System.out.println(month);          // DECEMBER
		
		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		System.out.println(minuteOfDay);    // 1439
		
		Instant instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();

		Date legacyDate = Date.from(instant);
		System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014


	}

}
