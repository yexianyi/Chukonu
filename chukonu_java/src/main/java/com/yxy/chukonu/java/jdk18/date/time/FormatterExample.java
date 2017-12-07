package com.yxy.chukonu.java.jdk18.date.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatterExample {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm");
		LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
		String string = formatter.format(parsed);
		System.out.println(string);     // Nov 03, 2014 - 07:13
 
	}

}
