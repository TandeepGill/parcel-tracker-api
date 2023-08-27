package com.fdmgroup.parceltracker.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EntryAndExitTraceLoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(EntryAndExitTraceLoggingAspect.class);
	
	@Around("execution(* com.fdmgroup.parceltracker.controller.*.*(..))")
	public Object controllerLogging (ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		String className = methodSignature.getDeclaringTypeName();
		String methodName = methodSignature.getName();
		
		LOGGER.trace(() -> "Entering " + className + "." + methodName + "()");
		Object result = joinPoint.proceed();
		LOGGER.trace(() -> "Exiting " + className + "." + methodName + "()");
		
		return result;
	}

}
