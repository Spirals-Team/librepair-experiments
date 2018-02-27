package poe.spring.annotation;

//import java.util.logging.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import ch.qos.logback.classic.Logger;

@Aspect
@Component
public class ChronoAspect {
	
	@Autowired
	Logger logger;
	

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
	}

	@Around("@annotation(poe.spring.annotation.Chrono)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
//		System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		logger.debug(joinPoint.getSignature() + " executed in " + executionTime + "ms");

		return proceed;
	}

	@Around("@within(poe.spring.annotation.Chrono)")
	public Object logExecutionTimeClass(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
//		System.out.println(joinPoint.getSignature() + " executed class in " + executionTime + "ms");
		logger.debug(joinPoint.getSignature() + " executed class in " + executionTime + "ms");
		return proceed;
	}

//	protected void logg(String s) {
//		Logger logger = LoggerFactory.getLogger(".springboot.src.main.java.poe.spring.annotation.ChronoAspect.java");
//	    logger.debug("Hello world.");
//		
//	}
}