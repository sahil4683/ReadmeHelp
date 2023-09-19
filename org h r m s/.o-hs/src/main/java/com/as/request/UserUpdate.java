package com.as.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdate {
	private Long id;
	private String username;
	private String password;
}
