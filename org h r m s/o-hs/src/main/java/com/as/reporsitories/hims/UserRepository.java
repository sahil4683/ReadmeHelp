package com.as.reporsitories.hims;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.as.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	
	List<User> findByLoginType(String loginType);

//	Boolean existsByEmail(String email);
	
//	User findById(int id);
}
