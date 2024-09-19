package com.cognizant.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.dto.UserDTO;
import com.cognizant.entities.User;

@Service
public interface UserService {
	public UserDTO authenticateUser(String username, String password);

	List<User> listOfUsers();
}