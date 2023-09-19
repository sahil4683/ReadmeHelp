package com.as.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryCheck {
	
	public static void print() {
		Runtime runtime = Runtime.getRuntime();
        System.err.println(format_long(runtime.freeMemory()));
        System.err.println(format_long(runtime.maxMemory()));
        System.err.println(format_long(runtime.totalMemory()));
	}
	
	private static String format_long(long number){
	    String formatted;
	    if (number > 1000000000) {
	       String re = "^(.*)\\d{9}$";
	       Matcher m = Pattern.compile(re).matcher(Long.toString (number));
	       if (m.find()) {
	          formatted = m.group(1) + " Gb";
	       } else {
	          formatted = "0";
	       }
	    } else if (number > 1000000) {
	       String re = "^(.*)\\d{6}$";
	       Matcher m = Pattern.compile(re).matcher(Long.toString (number));
	       if (m.find()) {
	          formatted = m.group(1) + " Mb";
	       } else {
	          formatted = "0";
	       }
	    } else if (number > 1000) {
	       String re = "^(.*)\\d{3}$";
	       Matcher m = Pattern.compile(re).matcher(Long.toString (number));
	       if (m.find()) {
	          formatted = m.group(1) + " Kb";
	       } else {
	          formatted = "0";
	       }
	    } else {
	       formatted = Long.toString (number) + " bytes";
	    }
	    return (formatted);
	}
}
