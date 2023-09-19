package com.as.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeFormatChanger {

	public static String getCurrentTime() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
		return LocalTime.now().format(format);
	}

	public static String getDateTimeToTime(String datetime) {
		return datetime.split(" ").length > 1 
				? LocalTime.parse(datetime.split(" ")[1]).toString()
				: LocalTime.parse(datetime).toString();
	}
	
	public static String Time24HourTo12Hour(String obj) {
		SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
		SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
		try {
			Date _24HourDt = _24HourSDF.parse(obj);
			return _12HourSDF.format(_24HourDt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
