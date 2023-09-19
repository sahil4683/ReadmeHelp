package com.as.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse {
	private int status;
	private String message;
	private String type;
	private Object body;
	private List<FieldErrorMessage> fieldErrorMessageList;
}