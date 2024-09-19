package com.cognizant.aspects;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
	
	private Logger logger = Logger.getLogger(LoggerAspect.class.getName());
	
	@Around("execution(* com.cognizant.services.*.*(..))")
	 public Object log(ProceedingJoinPoint point) throws Throwable {
        logger.info(point.getSignature().toString() + " method execution starts ");
        Instant start = Instant.now();
        Object result = point.proceed();  // Capture the result of the method call
        Instant finish = Instant.now();
        long timeTotal = Duration.between(start, finish).toMillis();
        logger.info("time taken to execute the method is " + timeTotal);
        logger.info(point.getSignature().toString() + " method execution ends ");
        return result;  // Return the result to the caller
    }
	
}
