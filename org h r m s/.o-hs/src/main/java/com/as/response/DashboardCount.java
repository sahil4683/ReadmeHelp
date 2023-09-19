package com.as.response;

public interface DashboardCount {
	String getTodayRegisterd();
	String getTotalAdmited();
	String getTodayAdmited();
	String getTodayDischarge();
	
	String getTotalBeds();
	String getAvailableBeds();
	String getOccupiedBeds();
}
