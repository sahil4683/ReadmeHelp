package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.entities.Privileges;

public interface PrivilegesRepository extends JpaRepository<Privileges, Integer> {

//	Privileges findByUser(User user);
	
}
