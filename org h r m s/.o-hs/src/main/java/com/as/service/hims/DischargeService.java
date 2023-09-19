package com.as.service.hims;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.Discharge_DTO;
import com.as.h_entities.hims.BedMaster;
import com.as.h_entities.hims.Discharge_Entity;
import com.as.h_entities.hims.IpdBill_FinalIpdBill_Entity;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.reporsitories.hims.BedMasterRepository;
import com.as.reporsitories.hims.Discharge_Repository;
import com.as.reporsitories.hims.IpdBill_FinalIpdBill_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class DischargeService {

	private static final Logger logger = Logger.getLogger(DischargeService.class.getName());

	@Autowired
	private ReceptionReceptionAdmission_Repository admissionRepository;

	@Autowired
	private BedMasterRepository bedMasterRepository;

	@Autowired
	private Discharge_Repository dischargeRepository;
	
	@Autowired
	IpdBill_FinalIpdBill_Repository bill_FinalIpdBill_Repository;

	public BaseResponse saveOrUpdate(Discharge_DTO form, Principal principal, Long creditYear) {
		logger.info("Enter into DischargeService saveOrUpdate(@RequestBody Discharge_DTO form, Principal principal)");

		BaseResponse response = new BaseResponse();
		try {
			if (form.getId() != null) {
				Discharge_Entity dischargeEntity = dischargeRepository.getOne(form.getId());
				if (dischargeEntity == null) {
					response.setMessage("Discharge rRecord not found successfully.");
					response.setStatus(404);
					return response;
				}
				dischargeEntity.setDtype(form.getDtype());
				dischargeRepository.save(dischargeEntity);
				response.setStatus(200);
				response.setType(ResponseType.SUCCESS);
//				response.setBody(dischargeEntity);
				response.setMessage("Record updated successfully.");

			} else {
				
				ReceptionReceptionAdmission_Entity admissionEntity = admissionRepository.findByIPDNOAndCreditYear(form.getIpdno(),creditYear);
				if(admissionEntity !=null) {
					admissionEntity.setDischarge("YES");	
				}

				BedMaster bedMaster = bedMasterRepository.findByBedno(form.getBedNo());
				if(bedMaster !=null) {
					bedMaster.setBookedyn(0);	
				}
				
				Discharge_Entity dischargeEntity = new Discharge_Entity();
				BeanUtils.copyProperties(dischargeEntity, form);
				dischargeEntity.setGroupName(bedMaster.getWardname());
				
				
				IpdBill_FinalIpdBill_Entity ipdBill_FinalIpdBill_Entity = bill_FinalIpdBill_Repository.findByIpdnoAndCreditYear(form.getIpdno(), creditYear);
				if(ipdBill_FinalIpdBill_Entity !=null) {
					ipdBill_FinalIpdBill_Entity.setReadyForDischarge(false);
				}
			 	
				bedMasterRepository.save(bedMaster);
				admissionRepository.save(admissionEntity);
				bill_FinalIpdBill_Repository.save(ipdBill_FinalIpdBill_Entity);
				dischargeRepository.save(dischargeEntity);
				
				response.setBody(dischargeEntity);
				response.setStatus(200);
				response.setMessage("Patient Discharged");

			}
			return response;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
			response.setMessage(e.getMessage());
			response.setStatus(500);
			return response;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			logger.error(e);
			response.setMessage(e.getMessage());
			response.setStatus(500);
			return response;
		}

	}

	public BaseResponse findAll(Principal principal,Long creditYear) {
		logger.info("Enter into DischargeService findAll(Principal principal)");

		BaseResponse response = new BaseResponse();
		
		List<Discharge_Entity> dataList = dischargeRepository.findByCreditYearOrderByIdDesc(creditYear);
		
		if(dataList!=null && dataList.size()>0)
		{
		response.setBody(dataList);
		response.setStatus(200);
		response.setMessage("Records.");
		}else
		{ 
			response.setStatus(404);
			response.setMessage("Record not found.");
		}
		return response;
	}

	public BaseResponse getCurrent(String date, Principal principal,Long creditYear) {
		logger.info("Enter into DischargeService  getCurrent(String date, Principal principal)");
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(dischargeRepository.findByDateAndCreditYearOrderByIdDesc(date,creditYear));
		return response;
	}
	
	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Optional<Discharge_Entity> check = dischargeRepository.findById(id);
		Discharge_Entity entity=check.get();
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			dischargeRepository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

}
