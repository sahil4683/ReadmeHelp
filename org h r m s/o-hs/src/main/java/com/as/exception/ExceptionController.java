package com.as.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.as.response.BaseResponse;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler  {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 
		BaseResponse exceptionResponse= new BaseResponse( );
		exceptionResponse.setMessage(ex.getMessage()); 
		exceptionResponse.setStatus(400); 
		
		System.out.print(ex.getMessage());
		System.out.print(ex.getStackTrace());

		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);  
	}

	
	
}
