package com.yxy.chukonu.java.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyWithJDK implements InvocationHandler{

	//the real object we want to delegate
    private Object subject;

    public DynamicProxyWithJDK(Object subject)
    {
        this.subject = subject;
    }
    
    @Override
    /**
     * @param object: the proxy instance
     * @param method: the method name of the proxy instance we want to call
     * @param args: the arguments of the calling method
     */
    public Object invoke(Object object, Method method, Object[] args) throws Throwable{
    	 System.out.println("do sth before invoking...");
         
//         System.out.println("invoking Method:" + method);
         Object result = method.invoke(subject, args);
         System.out.println("result: "+result) ;
         System.out.println("do sth after invoking...");
         return result;
    }
    
    static interface Human{
    	public String getName() ;
    }

    static class Man implements Human{
    	public String getName(){
    		return "Man" ;
    	}
    }
    
    static class Woman implements Human{
    	public String getName(){
    		return "Woman" ;
    	}
    }
    
    
    public static void main(String[] args)
    {
        Human man = new Man();
        //inject delegation instance to handler
        InvocationHandler handler = new DynamicProxyWithJDK(man);
        Human proxyInstance = (Human)Proxy.newProxyInstance(handler.getClass().getClassLoader(), man.getClass().getInterfaces(), handler);
        proxyInstance.getName() ;
    }

}
