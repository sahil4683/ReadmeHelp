package com.as.security;

import java.security.Principal;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.dto.NumberSequenceUtility_DTO;
import com.as.request.LoginRequest;
import com.as.request.PrivilegesUpdateRequest;
import com.as.request.SignupRequest;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	@Autowired
	UserService userService;
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/allUser")
	public BaseResponse findAll(Principal principal) {
		return userService.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getUsersByLoginType/{login_type}")
	public BaseResponse getUsersByLoginType(@PathVariable(value="login_type") String loginType,Principal principal) {
		return userService.getUsersByLoginType(loginType);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/get-privileges/{id}")
	public BaseResponse getPrivileges(@PathVariable(value = "id") Long id, Principal principal) {
		return userService.getPrivileges(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/update-privileges")
	public BaseResponse updatePrivileges(@Valid @RequestBody PrivilegesUpdateRequest request, Principal principal) {
		return userService.updatePrivileges(request);
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/delete/{username}")
	public BaseResponse deleteUser(@PathVariable(value = "username") String username, Principal principal) {
		BaseResponse response = new BaseResponse();
		if (username == null) {
			response.setStatus(300);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Please review data submitted");
			return response;
		}
		return userService.delete(username, principal);
	}
	
	@PostMapping("/update")
	public BaseResponse updateUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.updateUser(signUpRequest);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/console-update")
	public BaseResponse ConsoleUserCreate(@RequestBody LoginRequest loginRequest, ServletRequest request,
			HttpServletRequest httpServletRequest) {
		return userService.ConsoleUserCreate(loginRequest);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getConsoleUser")
	public BaseResponse getConsoleUser(Principal principal) {
		return userService.getConsoleUser();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/number-sequence-update")
	public BaseResponse SaveNumberSequence(@RequestBody NumberSequenceUtility_DTO utility, ServletRequest request,
			HttpServletRequest httpServletRequest) {
		return userService.SaveNumberSequence(utility);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getNumberSequence")
	public BaseResponse getNumberSequence(Principal principal) {
		return userService.getNumberSequence();
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getNextNumberSequence/{name}")
	public BaseResponse getNextNumberSequence(@PathVariable(value = "name") String name, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return userService.getNextNumberSequence(name,principal,yearCd);
	}
}
