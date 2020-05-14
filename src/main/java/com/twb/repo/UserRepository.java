package com.twb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twb.model.User;
/**
 * @author twb
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
	 User findByUsername(String username);

}
