package com.cognizant.repositries;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.User;
 
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserNameAndPassword(String userName, String password);

}