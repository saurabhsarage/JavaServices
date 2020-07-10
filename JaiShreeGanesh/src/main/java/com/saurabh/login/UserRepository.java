package com.saurabh.login;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findAllByUsername(String username);
	
	User findByUsername(String username);
	

	List<User> findAllByFnameIgnoreCase(String fname);

}
