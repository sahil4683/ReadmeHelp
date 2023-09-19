package com.as.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatChanger {

	public static String getCurrentDate() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.now().format(format);
	}
	
	public static String YYYYMMDD_TO_DDMMYYYY(Date obj) {
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd-MM-yyyy";

		String newDateString = null;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);

		try {
			Date d = sdf.parse(String.valueOf(obj).substring(0, 10));
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return newDateString;
	}
	
	public static String YYYYMMDD_TO_DDMMYYYY(String obj) {
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd-MM-yyyy";

		String newDateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		try {
			Date d = sdf.parse(obj.substring(0, 10));
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return newDateString;
	}
	
}
