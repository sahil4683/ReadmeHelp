package com.as.service.hims;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.Shifting_DTO;
import com.as.h_entities.hims.BedMaster;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.h_entities.hims.Shifting;
import com.as.reporsitories.hims.BedMasterRepository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reporsitories.hims.ShiftingRepository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class ShiftingService  {

	@Autowired
	private ShiftingRepository shiftingRepository;

	@Autowired
	private BedMasterRepository bedMasterRepository;
	
	@Autowired
	ReceptionReceptionRegistration_Repository receptionRegistrationRepository;
	
	@Autowired
	ReceptionReceptionAdmission_Repository receptionAdmissionRepository;


	public BaseResponse save(Shifting_DTO shifting,Long creditYear) {
	
		BaseResponse response = new BaseResponse();

		Shifting entity = new Shifting();
		BeanUtils.copyProperties(shifting, entity);

		ReceptionReceptionAdmission_Entity receptionAdmission = receptionAdmissionRepository.findByIPDNOAndCreditYear(shifting.getIpdno(),creditYear);
		if(receptionAdmission==null){
			response.setMessage("Patient Not Found");
			response.setStatus(404);
			return response;
		}
		ReceptionReceptionRegistration_Entity receptionRegistration = receptionRegistrationRepository.findByUHID(receptionAdmission.getUHID());
		if(receptionRegistration!=null) {
			entity.setPname(receptionRegistration.getName());
		}
		
		if(entity.getPreBedNo()==null || entity.getPreBedNo().isEmpty()){
			entity.setPreBedNo(receptionAdmission.getBEDNO());
		}
		
		BedMaster shiftedBed = bedMasterRepository.findByBedno(entity.getShiftBedNo());
		BedMaster previousBed = bedMasterRepository.findByBedno(entity.getPreBedNo());

		if (shiftedBed.getBookedyn() == 1) {
			response.setMessage("Shift bed number is already occupied.");
			response.setStatus(204);
			return response;
		}
		
		entity = shiftingRepository.save(entity);
		if (entity != null) {

			if(previousBed!=null) {
				entity.setPreGroup(String.valueOf(previousBed.getGroup_s()));
				previousBed.setBookedyn(0);
				bedMasterRepository.save(previousBed);
			}
			
			
			entity.setShiftGroup(String.valueOf(shiftedBed.getGroup_s()));
			
			
			shiftedBed.setBookedyn(1);			
			
			bedMasterRepository.save(shiftedBed);

			receptionAdmission.setBEDNO(shiftedBed.getBedno());
			receptionAdmission.setWardname(shiftedBed.getWardname());
			receptionAdmissionRepository.save(receptionAdmission);
			
			response.setBody(entity);
			response.setMessage("Bed Shifted successfully");
			response.setStatus(200);
			return response;
		}
		response.setMessage("Bet shifting failed");
		response.setStatus(204);
		return null;
	}

	@ReadOnlyProperty
	public BaseResponse getShiftingHistory(Long creditYear) {
		BaseResponse response = new BaseResponse();
		List<Shifting> shiftingHistory = shiftingRepository.findByCreditYearOrderByIdDesc(creditYear);
		if(shiftingHistory!=null && shiftingHistory.size()>0){
			response.setBody(shiftingHistory);
			response.setMessage("Record found succefully");
			response.setStatus(200);
		}else{
			response.setMessage("Record not found.");
			response.setStatus(404);
		}
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Long creditYear, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(shiftingRepository.findByShiftDateAndCreditYear(Date,creditYear));
		return response;
	}


	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Shifting entity = shiftingRepository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			shiftingRepository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

}
