package com.spcom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spcom.entity.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
	Optional<UserCredential> findByName(String username);
}
