package com.as.security;

import java.util.List;

import com.as.entities.Privileges;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtResponse {
	private String token;
	@JsonIgnore
	private String type = "Bearer";
	private Long id;
	private String username;

//	private List<String> roles;
	private String roles;
	private Long accountYearId;
	private String accountYearDate;
//	private String loginType;
	private List<Privileges> privileges;

	public JwtResponse(String accessToken, Long id, String username, String roles, Long accountYearId,
			String accountYearDate, List<Privileges> privileges) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.accountYearId = accountYearId;
		this.accountYearDate = accountYearDate;
//		this.loginType = loginType;
		this.privileges = privileges;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoles() {
		return roles;
	}

	public Long getAccountYearId() {
		return accountYearId;
	}

	public void setAccountYearId(Long accountYearId) {
		this.accountYearId = accountYearId;
	}

	public String getAccountYearDate() {
		return accountYearDate;
	}

	public void setAccountYearDate(String accountYearDate) {
		this.accountYearDate = accountYearDate;
	}

//	public String getLoginType() {
//		return loginType;
//	}
//
//	public void setLoginType(String loginType) {
//		this.loginType = loginType;
//	}

	public List<Privileges> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privileges> privileges) {
		this.privileges = privileges;
	}

}
