package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.as.dto.NumberSequenceUtility_DTO;
import com.as.entities.ConsoleUser;
import com.as.entities.NumberSequenceUtility;
import com.as.entities.Privileges;
import com.as.entities.User;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.ConsoleUser_Repository;
import com.as.reporsitories.hims.PrivilegesRepository;
import com.as.reporsitories.hims.UserRepository;
import com.as.request.LoginRequest;
import com.as.request.PrivilegesDTO;
import com.as.request.PrivilegesUpdateRequest;
import com.as.request.SignupRequest;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.response.UserResponse;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	PrivilegesRepository privilegesRepository;
	
	@Autowired
	ConsoleUser_Repository console_repository;
	
	@Autowired
	NumberSequenceUtilityRepository sequenceUtilityRepository;

	public BaseResponse findAll() {
		List<User> user = userRepository.findAll();
		List<UserResponse> userResponse = new ArrayList<UserResponse>();
		UserResponse Uresponse = null;
		for (User u : user) {
			Uresponse = new UserResponse(u.getId(), u.getUsername(), u.getLoginType());
			userResponse.add(Uresponse);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(userResponse);
		return response;
	}

	public BaseResponse getUsersByLoginType(String loginType) {
		List<User> user = userRepository.findByLoginType(loginType);
		List<UserResponse> userResponse = new ArrayList<UserResponse>();
		UserResponse Uresponse = null;
		for (User u : user) {
			Uresponse = new UserResponse(u.getId(), u.getUsername(), u.getLoginType());
			userResponse.add(Uresponse);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(userResponse);
		return response;
	}

	public BaseResponse getPrivileges(Long id) {
		User user = userRepository.findById(id).orElse(null);
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(user.getPrivileges());
		return response;
	}

	public BaseResponse updatePrivileges(PrivilegesUpdateRequest updateReqest) {

		BaseResponse response = new BaseResponse();
		Optional<User> entity = userRepository.findById(updateReqest.getUser());
		if (!entity.isPresent()) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("User Not Found");
			return response;
		} else {
			User user = entity.get();

			List<Privileges> privilegeList = new ArrayList<Privileges>();
			for (PrivilegesDTO dto : updateReqest.getPrivileges()) {
				if(dto.getId() != null) {
					Privileges p = privilegesRepository.findById(dto.getId()).get();
					p.setMenu_action(dto.getMenu_action());
					p.setEdit_action(dto.getEdit_action());
					p.setDelete_action(dto.getDelete_action());
					privilegesRepository.save(p);
				}else {
					Privileges p = new Privileges();
					BeanUtils.copyProperties(dto, p);
					privilegeList.add(p);	
				}
			}

			if(user.getPrivileges().isEmpty()) {
				user.setPrivileges(privilegeList);
			}
			
			System.err.println(user.getPrivileges());	
			
			userRepository.save(user);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Updated User Data");
			return response;
		}
	}

	public BaseResponse delete(String username, Principal principal) {
		BaseResponse response = new BaseResponse();
		User entity = userRepository.findByUsername(username).orElse(null);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			userRepository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	public BaseResponse updateUser(SignupRequest signUpRequest) {
		BaseResponse response = new BaseResponse();
		User entity = userRepository.findByUsername(signUpRequest.getUsername()).orElse(null);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("User Not Found");
			return response;
		} else {
			if (signUpRequest.getPassword() != null) {
				entity.setPassword(encoder.encode(signUpRequest.getPassword()));
			}
			userRepository.save(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Updated User Data");
			return response;
		}
	}
	
	public BaseResponse ConsoleUserCreate(LoginRequest loginRequest) {
		
		BaseResponse response = new BaseResponse();
		/*
		 * ConsoleUser user =new ConsoleUser();
		 * user.setUsername(loginRequest.getUsername());
		 * user.setPassword(encoder.encode(loginRequest.getPassword())); user =
		 * console_repository.save(user); response.setStatus(404);
		 * response.setType(ResponseType.RESPONSE_ERROR);
		 * response.setMessage("Contact Administrator"); if(user !=null) {
		 * response.setStatus(200); response.setType(ResponseType.SUCCESS);
		 * response.setMessage("User Created"); return response; }
		 */
		/* { "password": "o@12345", "username": "Old User", "loginType": "New User" } */
		ConsoleUser user = console_repository.findByUsername(loginRequest.getUsername());
		if (user == null) {
			response.setStatus(404);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("User Not Found");
			return response;
		} else {
//			user.setUsername(loginRequest.getLoginType());
			user.setPassword(encoder.encode(loginRequest.getPassword()));
			console_repository.save(user);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("User Updated");
			return response;
		}
		
	}
	
	public BaseResponse getConsoleUser() {
		List<ConsoleUser> userList = console_repository.findAll();
		ConsoleUser user= userList.get(0);
		UserResponse Uresponse = new UserResponse(user.getId(), user.getUsername());
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(Uresponse);
		return response;
	}
	
	public BaseResponse SaveNumberSequence(NumberSequenceUtility_DTO utility) {
		BaseResponse response = new BaseResponse();
		NumberSequenceUtility number = sequenceUtilityRepository.findByName(utility.getName());
		if (number == null) {
			response.setStatus(404);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Not Found");
			return response;
		} else {
			number.setPrefix(utility.getPrefix());
			number.setSuffix(utility.getSuffix());
			number.setSequence(utility.getSequence());
			sequenceUtilityRepository.save(number);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Sequence Updated");
			return response;
		}
	}
	
	public BaseResponse getNumberSequence() {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(sequenceUtilityRepository.findAll());
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getNextNumberSequence(String name,Principal principal, Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName(name);
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();

		response.setBody(prefix+""+nextSequence+""+suffix);
		return response;
	}
	
	
}
