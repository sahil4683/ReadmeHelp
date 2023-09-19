package com.as.response;

import java.util.HashMap;
import java.util.Map;

import com.as.request.Report_Bill_Request;

import lombok.Data;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
public class GroupWiseTestResponse {

	private String testName;
	private String testCountTotal;
	private String groupTotal;
	
	 private JRBeanCollectionDataSource mainDataSource;
	 
	 public Map<String, Object> getParameters(Report_Bill_Request request) {
	       Map<String,Object> parameters = new HashMap<>();
	       parameters.put("testName", getTestName());
	       parameters.put("testCountTotal", getTestCountTotal());
	       parameters.put("groupTotal", getGroupTotal());
	       
	       parameters.put("billType", "Management Information System");
		   parameters.put("fullDate", "From Date : "+request.getFromDate()+" To : "+request.getToDate()+",");
		   parameters.put("groupTotal", request.getGroupTotal());
		   
	       return parameters;
	   }
	   public Map<String, Object> getDataSources() {
	       Map<String,Object> dataSources = new HashMap<>();
	       dataSources.put("mainDataSource", mainDataSource);
	 
	       return dataSources;
	   }
}
