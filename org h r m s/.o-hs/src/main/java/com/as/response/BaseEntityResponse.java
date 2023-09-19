package com.as.response;


import java.io.Serializable;
import java.util.List; 
import com.as.constant.Constant; 
public class BaseEntityResponse<T> implements Serializable {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Integer responseCode;

	protected String responseMessage;
	 
	private T responseData; 
	private Object responseData1; 
	private List<T> responseDataList; 

	public void setInvalidRequestParamResponse() {		
		this.responseCode=Constant.ResponseCode.INVALID_REQUEST_PARAMETERS.value();
		this.responseMessage=Constant.ResponseMessage.INVALID_REQUEST_PARAMETERS.value();
	}

	public void setRecordSavedResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_CREATED.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_CREATED.value();
		this.responseData=responsObj;
	}
	
	public void setSuccessResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.SUCCESS.value();
		this.responseData=responsObj;
	}
	
	public void setRecordPartiallySavedResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_CREATED.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_CREATED_PARTIALLY.value();
		this.responseData=responsObj;
	}
	
	public void setRecordRemovedResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_REMOVED.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_REMOVED.value();
		this.responseData=responsObj;
	}
	
	public void setRecordNotRemovedResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_REMOVED.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_NOT_REMOVED.value();
		this.responseData=responsObj;
	}

	 public void setUpdateSuccessfulResponse(T responsObj){
		 this.responseCode=Constant.ResponseCode.RECORD_UPDATE_SUCCESS.value();
		 this.responseMessage=Constant.ResponseMessage.RECORD_UPDATE_SUCCESS.value();
		 this.responseData=responsObj;
	 }
	 
	 public void setUpdateFailureResponse(T responsObj){
		 this.responseCode=Constant.ResponseCode.RECORD_UPDATE_FAIL.value();
		 this.responseMessage=Constant.ResponseMessage.RECORD_UPDATE_FAIL.value();
		 this.responseData=responsObj;
	 }
	public void setRecordNotSavedResponse() {
		this.responseCode=Constant.ResponseCode.RECORD_NOT_CREATED.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_NOT_CREATED.value();
	}

	public void setRecordFoundResponse(T responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_FOUND.value();
		this.responseData=responsObj;
	}
	
	public void setRecordFoundResponse1(Object responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_FOUND.value();
		this.responseData1=responsObj;
	}

	public void setRecordNotFoundResponse() {
		this.responseCode=Constant.ResponseCode.RECORD_NOT_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_NOT_FOUND.value();
	}
	
	public void setExceptionResponse(String message){
		this.responseCode=Constant.ResponseCode.INTERNAL_SERVER_ERROR.value();
		this.responseMessage=message;
	}

	public void setRecordAlreadyExistResponse(String message){
		this.responseCode=Constant.ResponseCode.RECORD_ALREADY_EXIST.value();
		this.responseMessage=message;
	}
	public void setAllRecordFoundResponse(List<T> responsObj) {
		this.responseCode=Constant.ResponseCode.RECORD_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_FOUND.value();
		this.responseDataList=responsObj;
	}
	
	public void setRecordFoundResponse2(int count) {
		this.responseCode=Constant.ResponseCode.RECORD_FOUND.value();
		this.responseMessage=Constant.ResponseMessage.RECORD_FOUND.value();
		this.responseData1=count;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}

	public Object getResponseData1() {
		return responseData1;
	}

	public void setResponseData1(Object responseData1) {
		this.responseData1 = responseData1;
	}

	public List<T> getResponseDataList() {
		return responseDataList;
	}

	public void setResponseDataList(List<T> responseDataList) {
		this.responseDataList = responseDataList;
	}

 

}
