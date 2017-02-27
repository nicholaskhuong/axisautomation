package com.abb.ventyx.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
	public static String milliSecToTimeStamp(long millisec) {
		Date date = new Date(millisec);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
		return formatter.format(date);
	}
}
