package com.as.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

public class ClientRequestInfo {

	public static String getInternetAddress(HttpServletRequest request) {
		String clientIp;
		String clientXForwardedForIp = request.getHeader("x-forwarded-for");
		if (clientXForwardedForIp != null) { clientIp = clientXForwardedForIp.split(" *, *")[0]; }
		else { clientIp = request.getRemoteAddr(); }
		return clientIp;
	}

	public static String getCurentDateAndTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dtf.format(LocalDateTime.now()).toString();
	}

}
