package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.BillDetailDTO;
import com.as.h_dto.hims.ListDetail;
import com.as.h_entities.hims.BillDetailSheet;
import com.as.h_entities.hims.BillDetailSheetDetail;
import com.as.h_entities.hims.MasterTestMaster_IpdTest_Entity;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.reporsitories.hims.BillDetailSheetDetailRepository;
import com.as.reporsitories.hims.BillDetailSheetRepository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.reporsitories.hims.MasterOtherMasters2_ClassMaster_Repository;
import com.as.reporsitories.hims.MasterTestMaster_IpdTest_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.response.BaseResponse;
import com.as.response.BillDetailSheetTable;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
import com.as.utils.ListToString;
import com.as.utils.TimeFormatChanger;

@Service
public class BillDetailService {

	@Autowired
	ReceptionReceptionAdmission_Repository receptionReceptionAdmission_Repository;
	@Autowired
	BillDetailSheetDetailRepository details_Repository;
	@Autowired
	BillDetailSheetRepository repository;
	
	@Autowired
	MasterTestMaster_IpdTest_Repository test_Repository;
	
	@Autowired
	ReceptionReceptionAdmission_Repository admission_Repository;
	
	@Autowired
	MasterOtherMasters2_ClassMaster_Repository classMaster_Repository;
	
	@Autowired
	MasterOtherMasters1_Group_Repository group_repository;
	
	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal, Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc2(yearCd));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByDateAndCreditYearOrderByIdDesc2(Date,creditYear));
		return response;
	}
	
	public BaseResponse getDetailsById(String id, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(details_Repository.findByBillNoAndCreditYear(id,creditYear));
		return response;
	}
	
	
	public BaseResponse save(Principal principal, BillDetailDTO form) {
		BaseResponse response = new BaseResponse();
		BillDetailSheet entity = null;
		entity = new BillDetailSheet();
		BeanUtils.copyProperties(form, entity);
		/*
		 * Other Modification On Entity
		 */
//		String dailyTest="";
//		StringBuilder bu=new StringBuilder();
//		for(String s:form.getDailyTestList()) {
////			dailyTest.concat(s).concat("_");
//			bu.append(s+",");
//		}
//		bu.deleteCharAt(0)
//		dailyTest = bu.toString();
		
		entity.setDailyTest(ListToString.getString(form.getDailyTestList()));
		entity = repository.save(entity);
		for (ListDetail detail : form.getListData()) {
			BillDetailSheetDetail details_Entity = new BillDetailSheetDetail();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setBillNo(String.valueOf(entity.getId()));
			details_Entity.setTestName(test_Repository.findById(Long.valueOf(detail.getTestName())).getTestName());
			details_Entity.setTestCode(detail.getTestName());
			details_Repository.save(details_Entity);
		}
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}
	
	public BaseResponse update(Principal principal, BillDetailDTO form) {
		BaseResponse response = new BaseResponse();
		BillDetailSheet entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		entity.setDailyTest(ListToString.getString(form.getDailyTestList()));
		for (ListDetail detail : form.getListData()) {
			BillDetailSheetDetail details_Entity = details_Repository.findById(detail.getId());
			if(details_Entity != null) {
				BeanUtils.copyProperties(detail, details_Entity);
				details_Repository.save(details_Entity);
			}else {
				BillDetailSheetDetail details_new = new BillDetailSheetDetail();
				BeanUtils.copyProperties(detail, details_new);
				details_new.setBillNo(String.valueOf(entity.getId()));
				details_new.setTestName(test_Repository.findById(Long.valueOf(detail.getTestName())).getTestName());
				details_new.setTestCode(detail.getTestName());
				details_Repository.save(details_new);
			}
				
		}
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
		return response;
	}
	
	public BaseResponse delete(long id, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		BillDetailSheet entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<BillDetailSheetDetail> delete = details_Repository.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
			details_Repository.deleteAll(delete);
			repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}
	
	public BaseResponse deleteDetailById(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		BillDetailSheetDetail entity = details_Repository.findById(id);
		details_Repository.delete(entity);
		response.setBody("Deleted");
		return response;
	}
	
	public BaseResponse getLabRequest(String ipdno, Principal principle, Long creditYear) {
		BaseResponse response = new BaseResponse();
		List<BillDetailSheet> requestList = repository.findByIpdnoAndCreditYear(ipdno,creditYear);
		if(requestList.isEmpty()) {
			response.setStatus(400);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Test Details Found !");
			return response;
		}
		
		BillDetailSheet request=requestList.get(requestList.size() - 1);
		List<BillDetailSheetDetail> requestDetail=details_Repository.findByBillNoAndCreditYear(String.valueOf(request.getId()),creditYear);
		ReceptionReceptionAdmission_Entity admission=admission_Repository.findByIPDNOAndCreditYear(ipdno,creditYear);
		List<MasterTestMaster_IpdTest_Entity> testList=test_Repository.findAll();

		List<LabRequestResponse> labResponse=requestDetail.stream().map(m-> new LabRequestResponse(
				m.getId(),
				m.getSno(), 
				m.getTestName(), 
				m.getTestCode(), 
				"", 
				"", 
				"1",
				getClassRate(admission,Long.valueOf(m.getTestCode()),testList), 
				"", 
				"", 
				getClassRate(admission,Long.valueOf(m.getTestCode()),testList), 
				"", 
				"", 
				DateFormatChanger.getCurrentDate(),
				TimeFormatChanger.getCurrentTime(),
				"BillRequest",
				request.getOrganization(),
				String.valueOf(classMaster_Repository.findByClassName(admission.getWardname()).getId()),
				test_Repository.findById(Long.valueOf(m.getTestCode())).getGroupName(),
				group_repository.findById(Long.valueOf(test_Repository.findById(Long.valueOf(m.getTestCode())).getGroupName())).getGroupName(),
				test_Repository.findById(Long.valueOf(m.getTestCode())).getSuperGroup())
				).collect(Collectors.toList());
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(labResponse);
		return response;
	}
	public String getClassRate(ReceptionReceptionAdmission_Entity admission,
			Long testId,List<MasterTestMaster_IpdTest_Entity> testList) {
		String className=admission.getWardname();
		if (className.equals("GENERAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
			.map(MasterTestMaster_IpdTest_Entity::getGeneral).findAny().orElse(null);
		}
		if (className.equals("NICU")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
			.map(MasterTestMaster_IpdTest_Entity::getNicu).findAny().orElse(null);
		}
		if (className.equals("SEMI SPECIAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getSemiSpecial).findAny().orElse(null);
			}
		if (className.equals("SPECIAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getSpecial).findAny().orElse(null);
			}
		if (className.equals("DELUXE")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getDeluxe).findAny().orElse(null);
			}
		if (className.equals("EXECUTIVE")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getExecutive).findAny().orElse(null);
			}
		if (className.equals("ICU")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getIcu).findAny().orElse(null);
			}
		if (className.equals("EMERGENCY")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getEmergency).findAny().orElse(null);
			}
		//Write Same For All
		
		return null;
		
	}
	
	
	
	@ReadOnlyProperty
	public BaseResponse searchOnTableData(String searchtext,Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.searchOnTableData(searchtext,creditYear));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getOnTableData(Long searchtext,Principal principal, Long creditYear) {
		
		BillDetailSheetTable entity = repository.findByIdAndCreditYear(searchtext,creditYear);
		ArrayList<BillDetailSheetTable> array=new ArrayList<>();
		if(entity!=null) {
			array.add(entity);
		}
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}
	
}
