/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.mybatis.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yxy.chukonu.mybatis.annotation.SystemLog;
import com.yxy.chukonu.mybatis.mapper.record.RecordMapper;
import com.yxy.chukonu.mybatis.util.TimeUtil;
import com.yxy.chukonu.mybatis.util.UUIDUtil;

@Aspect
@Component
public class SystemLogAspect {
	
	@Autowired
	private RecordMapper logService ;
	
	// Service layer point cut
	public void serviceAspect() {
	}

	// Controller layer point cut
	@Pointcut("@annotation(com.yxy.chukonu.mybatis.annotation.SystemLog)")    
	public void controllerAspect() {
	}

	@Before("controllerAspect()") 
	public void before(JoinPoint joinPoint){
		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes()).getRequest();
		// HttpSession session = request.getSession();
		// String ip = request.getRemoteAddr();
		try {
			// System.out.println("request method:" +
			// (joinPoint.getTarget().getClass().getName() + "." +
			// joinPoint.getSignature().getName() + "()"));
			String operation = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
			String desc = getMethodDescription(joinPoint);
			logService.createLog(UUIDUtil.next(), operation, desc, TimeUtil.currentTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void after(){
		
	}
	
	@AfterReturning("execution(* com.yxy.chukonu.mybatis.service.impl.AccountServiceImpl.deposit(..))"
			+ "&& execution(* com.yxy.chukonu.mybatis.service.impl.AccountServiceImpl.withdraw(..))"
			+ "&& execution(* com.yxy.chukonu.mybatis.service.impl.TransactionServiceImpl.doTransaction(..))")
	public void afterReturning(JoinPoint joinPoint){
		String operation = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
		String desc = "success";
		logService.createLog(UUIDUtil.next(), operation, desc, TimeUtil.currentTime());
	}
	
	public void afterThrowing(){
		
	}
	
	public  static String getMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemLog. class).description();  
                     break;  
                }  
            }  
        }  
         return description;  
    }  

	
}
