package com.spcom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spcom.entity.UserCredential;
import com.spcom.repository.UserCredentialRepository;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	public String saveUser(UserCredential credential) {
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		repository.save(credential);
		return "user added to the system";
	}

	public String generateToken(String username) {
		Optional<UserCredential> user = repository.findByName(username);
		return jwtService.generateToken(user.get());
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}

}
