package com.as.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
public class GroupWiseTestResponseMain {

	private String doctorName;
	private String testCount;
	private String doctorTotal;

	List<GroupWiseTestResponseSub> subList = new ArrayList<>();

//	private JRBeanCollectionDataSource subDataSource;
	
	public JRBeanCollectionDataSource getSubDataSource() {
		return new JRBeanCollectionDataSource(subList, false);
	}
}
