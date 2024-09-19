package com.cognizant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.UserDTO;
import com.cognizant.dto.UserRequest;
import com.cognizant.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
@Tag(name="Defects Management Authentication",description="Authentication API for Defect management module")
public class AuthController {
 
	private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


	AuthController(UserService userService)
	{
		this.userService = userService;
	}

	@Operation(description="Authenticate valid user credentials")
    @PostMapping("/users/login")
    public ResponseEntity<?> authenticate(@RequestBody UserRequest userRequest) {
        logger.info("Received request to authenticate user: {}", userRequest.getUserName());
        UserDTO userDTO = userService.authenticateUser(userRequest.getUserName(), userRequest.getPassword());
        if (userDTO.getUserName() != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}