package com.as.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountYearResponse {

	private Date lowerDate;
	private Date upperDate;
}
