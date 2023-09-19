package com.as.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.as.entities.AccountYear_Entity;
import com.as.reporsitories.hims.AccountYearRepository;
import com.as.response.AccountYearResponse;

@Component
public class AccountYearUtil {

	
	@Autowired
	AccountYearRepository accountYearRepository;
	
	public AccountYearResponse getAccountYear(Integer id) throws ParseException {
		AccountYear_Entity entity = accountYearRepository.findById(2);
		String lowerDate = entity.getFromDate();
		String upperDate = entity.getToDate();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date ldate = format.parse(lowerDate);
		Date udate = format.parse(upperDate);
		AccountYearResponse response = new AccountYearResponse(ldate, udate);
		return response;
	}
	
}
