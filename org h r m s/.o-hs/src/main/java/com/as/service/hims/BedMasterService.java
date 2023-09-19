package com.as.service.hims;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.as.h_entities.hims.BedMaster;
import com.as.reporsitories.hims.BedMasterRepository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.BedReportGenerator;
import com.as.response.BaseResponse;
import com.as.response.BedResponseInterface;
import com.as.response.BedStatusResponse;
import com.as.response.ResponseType;

@Service
public class BedMasterService {
	
	private static final Logger logger = Logger.getLogger(BedMasterService.class.getName());

	@Autowired
	BedMasterRepository repository;
	
	@Autowired
	ReceptionReceptionRegistration_Repository receptionRegistrationRepository;
	
	@Autowired
	private BedReportGenerator bedReportGenrator;

	public BaseResponse getBedList(Principal principal) {
		logger.info("Enter into BedMasterService getBedList(Principal principal)");
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findBybookedynOrderByBedno(0));
		return response;
	}

	public BaseResponse getAllBedList(Principal principal) {
		BaseResponse response = new BaseResponse();

		List<BedResponseInterface> bedMasterList = repository.getBedList();
		
		Map<String, Long> nameCount = bedMasterList.stream().filter(f-> f.getPatientType() !=null).collect(Collectors.groupingBy(string -> string.getPatientType(), Collectors.counting()));
		
		Long occupiedBeds = bedMasterList.stream().filter(ii -> ii.getOccupied()==1).count();
		
		Long totalBeds = Long.valueOf(bedMasterList.size());
		
		Long availableBeds = totalBeds - occupiedBeds;
		
		BedStatusResponse bedStatusResponse = new BedStatusResponse();

		bedStatusResponse.setBedList(bedMasterList);
		bedStatusResponse.setStatusWiseCount(nameCount);
		bedStatusResponse.setTotalBeds(totalBeds);
		bedStatusResponse.setAvailableBeds(availableBeds);
		bedStatusResponse.setOccupiedBeds(occupiedBeds);
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(bedStatusResponse);
		
		return response;
	}

	public ByteArrayInputStream vacentBedReport() {
		logger.info("Enter into BedMasterService vacentBedReport()");

		List<BedMaster> bedList = repository.findBybookedynOrderByBedno(0);
		return bedReportGenrator.generateBedReport(bedList);
	}

	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		BedMaster entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}
	 
}
