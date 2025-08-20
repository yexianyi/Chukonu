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
