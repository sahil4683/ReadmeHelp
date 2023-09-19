package com.as.security;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.entities.AccountYear_Entity;
import com.as.entities.ConsoleUser;
import com.as.entities.Role;
import com.as.entities.User;
import com.as.reporsitories.hims.AccountYearRepository;
import com.as.reporsitories.hims.ConsoleUser_Repository;
import com.as.reporsitories.hims.PrivilegesRepository;
import com.as.reporsitories.hims.RoleRepository;
import com.as.reporsitories.hims.UserRepository;
import com.as.request.LoginRequest;
import com.as.request.SignupRequest;
import com.as.request.UserUpdate;
import com.as.response.BaseResponse;
import com.as.response.MessageResponse;
import com.as.response.ResponseType;
import com.as.service.hims.UserDetailsImpl;
import com.as.utils.ClientRequestInfo;
import com.as.utils.ERole;
import com.as.utils.EmailHandler;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailHandler emailHandler;

	@Autowired
	AccountYearRepository accountYearRepository;

	@Autowired
	PrivilegesRepository privilegesRepository;

	@Autowired
	ConsoleUser_Repository console_repository;

	@PostMapping("/console")
	public BaseResponse ConsoleUserLogin(@RequestBody LoginRequest loginRequest, ServletRequest request,
			HttpServletRequest httpServletRequest) {
		BaseResponse response = new BaseResponse();
		ConsoleUser user = console_repository.findByUsername(loginRequest.getUsername());
		if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			return response;
		}
		response.setStatus(404);
		response.setType(ResponseType.RESPONSE_ERROR);
		response.setMessage("Contact Administrator");
		return response;
	}

	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, ServletRequest request,
			HttpServletRequest httpServletRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		// If LoginType No Same As userType...
//		if (!userDetails.getLoginType().equals(loginRequest.getLoginType())) {
//			return ResponseEntity.badRequest().body(String.valueOf("Invalid User !"));
//		}
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// Setting Account Year...(Get Current Year By Date Based.)
		String currentTimestamp = ClientRequestInfo.getCurentDateAndTime();
//		AccountYear_Entity entiry = accountYearRepository.findByFromDateGreaterThanEqualAndToDateLessThanEqual(currentTimestamp);
		//Setting Account Year By Year
		AccountYear_Entity entiry = accountYearRepository.findByAcYear(loginRequest.getYear());
		//Satic For Set Accunt Year In Audit Entity.
//		StaticParameters.creditYear = entiry.getId();
		
		logger.info("\n#####################" + "\nUser Authenticated - " + "\nIP Address : "
				+ ClientRequestInfo.getInternetAddress(httpServletRequest) + "" + "\nLogin Date: " + currentTimestamp
//				+ "" + "\nLogin Type: " 
//				+ loginRequest.getLoginType() +
				+"" + "\nUserName: " + userDetails.getUsername()
				+ "\n#####################");

		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles.get(0).toString(),
						entiry.getId(), entiry.getFromDate(), userDetails.getPrivileges()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
			HttpServletRequest httpServletRequest) throws RuntimeException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.ok(new MessageResponse("Error: Username is already taken!", 404));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getLoginType());
		user.setCreatedIp(ClientRequestInfo.getInternetAddress(httpServletRequest));
//		String strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		/* Role Hack */
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(adminRole);
		/* Role Hack */
		
//		if (strRoles == null || strRoles.isEmpty()) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			switch (strRoles) {
//			case "allow":
//				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				roles.add(adminRole);
//				break;
//
//			case "mod":
//				Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				roles.add(modRole);
//				break;
//
//			default:
//				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				roles.add(userRole);
//			}
//		}
		user.setRoles(roles);
		user = userRepository.save(user);

		logger.info("\n#####################" + "\nUser Registered - " + "\nIP Address : "
				+ ClientRequestInfo.getInternetAddress(httpServletRequest) + "" + "\nLogin Date: "
				+ user.getCreatedDate() + "" + "\nLogin Type: " + user.getLoginType() + "" + "\nUserName: "
				+ user.getUsername() + "\n#####################");

		return ResponseEntity.ok(new MessageResponse("User registered successfully!", 200));
	}

//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
//	@PostMapping("/user-update")
//	public BaseResponse userUpdate(@RequestBody UserUpdate form, @RequestHeader("villageCd") String villageCd, Principal principal) {
//		BaseResponse response = new BaseResponse();
//		
//		Optional<User> entity = userRepository.findById(form.getId());
//		if (entity == null) {
//			response.setStatus(300);
//			response.setType(ResponseType.WARNING);
//			response.setMessage("Not Found");
//			return response;
//		}
//		User u= entity.get();
//		u.setUsername(form.getUsername());
//		u = userRepository.save(u);
//		if (u.getId() != 0) {
//			response.setStatus(200);
//			response.setType(ResponseType.SUCCESS);
//			response.setMessage("Update");
//			return response;
//		}
//		return response;
//	}
//	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/user-pass-update")
	public BaseResponse userPassUpdate(@RequestBody UserUpdate form, Principal principal) {
		BaseResponse response = new BaseResponse();

		Optional<User> entity = userRepository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		User u = entity.get();
		u.setPassword(encoder.encode(form.getPassword()));
		u = userRepository.save(u);
		if (u.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
		return response;
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/user-delete/{id}")
	public BaseResponse userDelete(@PathVariable(value = "id") int id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Optional<User> entity = userRepository.findById(Long.valueOf(id));
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			User u = entity.get();
			userRepository.delete(u);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}
}
