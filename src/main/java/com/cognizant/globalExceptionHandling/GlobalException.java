package com.cognizant.globalExceptionHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cognizant.controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e){
	    final Logger logger = LoggerFactory.getLogger(AuthController.class);
        logger.error("Error occurred ", e.getMessage());
		return new ResponseEntity<> (HttpStatus.BAD_REQUEST+" Error => "+e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
