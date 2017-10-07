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
        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        Human proxyInstance = (Human)Proxy.newProxyInstance(handler.getClass().getClassLoader(), man.getClass().getInterfaces(), handler);
        proxyInstance.getName() ;
    }

}
