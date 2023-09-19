package com.as.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BedStatusResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<BedResponseInterface> bedList;
	private Map<String,Long> statusWiseCount;
	private Long totalBeds;
	private Long occupiedBeds;
	private Long availableBeds;
}
