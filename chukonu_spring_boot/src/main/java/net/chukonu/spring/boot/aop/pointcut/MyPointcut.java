package net.chukonu.spring.boot.aop.pointcut;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyPointcut {

	@Pointcut("execution(* net.chukonu.spring.boot.controller.*.**(..))")
	public void callController() {
		//only used for defining pointcut
	}

	
	@Before("callController()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("MyPointcut.doBefore()");
//		System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
//				+ joinPoint.getSignature().getName());
//		System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

	}

	
	@Around("execution(* net.chukonu.spring.boot.controller.*.**(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("MyPointcut.doAround()");
		Object result = pjp.proceed();
		System.out.println(result.toString());
		return result;
	}

	
	@AfterReturning("callController()")
	public void doAfterReturning(JoinPoint joinPoint) {
		System.out.println("MyPointcut.doAfterReturning()");

	}

}
