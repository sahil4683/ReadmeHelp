package com.as.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserResponse {

	private Long id;
	private String username;
	private String type;
	
	public UserResponse(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	
	
}
