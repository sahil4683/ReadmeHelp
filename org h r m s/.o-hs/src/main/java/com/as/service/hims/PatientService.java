package com.as.service.hims;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.response.AdmitedPatientListForShiftingResponse;
import com.as.response.BaseResponse;

@Service
public class PatientService {

	@Autowired
	private ReceptionReceptionAdmission_Repository admissionRepository;

	@Autowired
	ReceptionReceptionRegistration_Repository registrationRepository;

	public BaseResponse getAdmittedPatiendDetails(Long creditYear) {
		BaseResponse baseResponse = new BaseResponse();
		List<AdmitedPatientListForShiftingResponse> patienDetailsList = admissionRepository.getAdmitedPatientListForShifting(creditYear);
		if (patienDetailsList != null && patienDetailsList.size() > 0) {
			baseResponse.setBody(patienDetailsList);
			baseResponse.setMessage("Record found Successfully");
			baseResponse.setStatus(200);
		} else {
			baseResponse.setMessage("Record not found");
			baseResponse.setStatus(404);
		}
		return baseResponse;
	}
	
	public BaseResponse getAdmittedPatiendDetailsByIpdNo(String ipdno,Long creditYear) {
		BaseResponse baseResponse = new BaseResponse();
		AdmitedPatientListForShiftingResponse patienDetailsList = admissionRepository.getAdmitedPatientListForShiftingByIPDNo(ipdno,creditYear);
		if (patienDetailsList != null) {
			baseResponse.setBody(patienDetailsList);
			baseResponse.setMessage("Record found Successfully");
			baseResponse.setStatus(200);
		} else {
			baseResponse.setMessage("Record not found");
			baseResponse.setStatus(404);
		}
		return baseResponse;
	}

	public BaseResponse getDischargedPatiendDetails(Long creditYear) {
		BaseResponse baseResponse = new BaseResponse();

		List<ReceptionReceptionAdmission_Entity> patienDetailsList = admissionRepository.findByDischargeAndCreditYear("YES",creditYear);

		if (patienDetailsList != null && patienDetailsList.size() > 0) {
			baseResponse.setBody(patienDetailsList);
			baseResponse.setMessage("Record found Successfully");
			baseResponse.setStatus(200);
		} else {
			baseResponse.setMessage("Record not found");
			baseResponse.setStatus(404);
		}

		return baseResponse;
	}
}
