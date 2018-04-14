/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.prussia.play.spring.service.aop;

import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

	private static final Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);
	
	 private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();
	 private static long statLogFrequency = 10;
	 private static long methodWarningThreshold = 1000;

	/*
	2016-03-29 21:43:19.596  WARN 1608 --- [nio-8080-exec-1] c.p.t.p.s.service.aop.ServiceMonitor     : around start.., execution(void com.prussia.test.play.spring.service.AccountService.createAcctount(String,String)) 
	2016-03-29 21:43:19.596  WARN 1608 --- [nio-8080-exec-1] c.p.t.p.s.service.aop.ServiceMonitor     : before aspect executing, execution(void com.prussia.test.play.spring.service.AccountService.createAcctount(String,String))
	2016-03-29 21:43:19.596  WARN 1608 --- [nio-8080-exec-1] c.p.t.p.s.service.aop.ServiceMonitor     : around end, execution(void com.prussia.test.play.spring.service.AccountService.createAcctount(String,String)) 
	2016-03-29 21:43:19.596  WARN 1608 --- [nio-8080-exec-1] c.p.t.p.s.service.aop.ServiceMonitor     : after aspect executed, execution(void com.prussia.test.play.spring.service.AccountService.createAcctount(String,String))
	2016-03-29 21:43:19.596  WARN 1608 --- [nio-8080-exec-1] c.p.t.p.s.service.aop.ServiceMonitor     : afterReturning executed, execution(void com.prussia.test.play.spring.service.AccountService.createAcctount(String,String)), return result is null 
 
	 */

	@Pointcut("execution(* com.prussia.test.play..*ServiceBean.*(..))")
	public void pointCut() {
		logger.warn("I am pointCut: " , this);
	}

	@After("pointCut()")
	public void after(JoinPoint joinPoint) {
		logger.warn("after aspect executed, {}", joinPoint);
	}

	@Before("pointCut()")
	public void before(JoinPoint joinPoint) {
		// 如果需要这里可以取出参数进行处理
		// Object[] args = joinPoint.getArgs();
		logger.warn("before aspect executing, {}", joinPoint);
	}

	@AfterReturning(pointcut = "pointCut()", returning = "returnVal")
	public void afterReturning(JoinPoint joinPoint, Object returnVal) {
		logger.warn("afterReturning executed, {}, return result is {} ", joinPoint , returnVal);
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.warn("around start.., {} ", joinPoint);
		long start = System.currentTimeMillis();
		Object o = null;
		try {
			o = joinPoint.proceed();
		} catch (Throwable ex) {
			throw ex;
		} finally {
			logger.warn("ServiceMonitor:" + joinPoint.getSignature().getName() + " takes "
					+ (System.currentTimeMillis() - start) + "ms");
			
			updateStats(joinPoint.getSignature().getName(),(System.currentTimeMillis() - start));
        }
		return o;
	}

	@AfterThrowing(pointcut = "pointCut()", throwing = "error")
	public void afterThrowing(JoinPoint jp, Throwable error) {
		logger.warn("error:" + error);
	}
	
	private void updateStats(String methodName, long elapsedTime) {
        MethodStats stats = methodStats.get(methodName);
        if(stats == null) {
            stats = new MethodStats(methodName);
            methodStats.put(methodName,stats);
        }
        stats.count++;
        stats.totalTime += elapsedTime;
        if(elapsedTime > stats.maxTime) {
            stats.maxTime = elapsedTime;
        }
        
        if(elapsedTime > methodWarningThreshold) {
            logger.warn("method warning: " + methodName + "(), count = " + stats.count + ", lastTime = " + elapsedTime + ", maxTime = " + stats.maxTime);
        }
        
        if(logger.isDebugEnabled()){
	        if(stats.count % statLogFrequency == 0) {
	            long avgTime = stats.totalTime / stats.count;
	            long runningAvg = (stats.totalTime-stats.lastTotalTime) / statLogFrequency;
	            logger.debug("method: " + methodName + "(), count = " + stats.count + ", lastTime = " + elapsedTime + ", avgTime = " + avgTime + ", runningAvg = " + runningAvg + ", maxTime = " + stats.maxTime);
	            
	            //reset the last total time
	            stats.lastTotalTime = stats.totalTime;   
	        }
        }
    }
    
    class MethodStats {
        public String methodName;
        public long count;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;
        
        public MethodStats(String methodName) {
            this.methodName = methodName;
        }
    }

}
