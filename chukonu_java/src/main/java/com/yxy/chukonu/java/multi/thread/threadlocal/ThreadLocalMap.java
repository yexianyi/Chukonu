package com.yxy.chukonu.java.multi.thread.threadlocal;

import java.lang.ref.WeakReference;

public class ThreadLocalMap {
	
	public <T> ThreadLocalMap(JDK8ThreadLocal<T> jdk8ThreadLocal, T firstValue) {
		// TODO Auto-generated constructor stub
	}


	<T> Entry getEntry(JDK8ThreadLocal<T> jdk8ThreadLocal) {
		//ignore 
		return null ;
	}
	
	
	static class Entry extends WeakReference<ThreadLocal<?>> {
        /** The value associated with this ThreadLocal. */
        Object value;

        Entry(ThreadLocal<?> k, Object v) {
            super(k);
            value = v;
        }
        
        
    }


	public <T> void set(JDK8ThreadLocal<T> jdk8ThreadLocal, T value) {
		// TODO Auto-generated method stub
		
	}
}
