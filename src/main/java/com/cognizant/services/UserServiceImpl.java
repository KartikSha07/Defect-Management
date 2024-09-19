package com.cognizant.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.dto.UserDTO;
import com.cognizant.entities.User;
import com.cognizant.repositries.UserRepository;
 
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
    private UserRepository userRepository;
    
    UserServiceImpl(UserRepository userRepository)
    {
    	this.userRepository = userRepository;
    }
 
    @Override
    public List<User> listOfUsers() {
        return (List<User>) userRepository.findAll();
    }
    
    @Override
    public UserDTO authenticateUser(String username, String password) {
        List<User> users = listOfUsers();
    	System.out.println("users list is "+users);
        UserDTO userModel = new UserDTO();
        for (User user : users) {
	            if (user.getUserName().equals(username) && user.getPassword().equals(password) && !user.isAccountLocked()) {
                userModel.setUserName(user.getUserName());
                userModel.setPassword(user.getPassword());
                userModel.setRole(user.getRole());
                userModel.setAccountLocked(user.isAccountLocked());
                break;
            }
        }
        return userModel;
    }
}