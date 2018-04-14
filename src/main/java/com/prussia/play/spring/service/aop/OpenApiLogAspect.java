package com.prussia.play.spring.service.aop;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * http://itlab.idcquan.com/Java/Spring/938605_2.html
 * @author N551JM
 *
 */

public class OpenApiLogAspect {
	private static Logger logger = LoggerFactory.getLogger(OpenApiLogAspect.class);

	public Object logExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
		Date start = new Date();
		try {
			return joinPoint.proceed(joinPoint.getArgs());
		} catch (Exception err) {
			throw err;
		} finally {
			Date end = new Date();
			logger.info("OpenApiExecuteTime:" + joinPoint.getSignature().getName() + " takes "
					+ (end.getTime() - start.getTime()) + "ms");
		}
	}
}
