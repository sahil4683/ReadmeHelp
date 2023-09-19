package com.as.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.as.response.BaseResponse;
import com.as.response.FieldErrorMessage;
import com.as.response.ResponseType;

public class ApiValidationUtility {

	public <T>  BaseResponse validateAPI(T form) {
		
		BaseResponse response = new BaseResponse();
		List<FieldErrorMessage> fieldErrorMessageList = new ArrayList<FieldErrorMessage>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> errors = validator.validate(form);
		if (errors.size() > 0) {
			for (ConstraintViolation<T> error : errors) {
				FieldErrorMessage fieldErrorMessage = new FieldErrorMessage();
				fieldErrorMessage.setFieldName(error.getPropertyPath().toString());
				fieldErrorMessage.setErrorMessage(error.getMessage());
				fieldErrorMessageList.add(fieldErrorMessage);
			}
			response.setStatus(300);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setFieldErrorMessageList(fieldErrorMessageList);
			response.setMessage("Please review data submitted");
			return response;
		}
		return response;
	}
	
}
