package com.yxy.chukonu.java.multi.thread.threadlocal;

import java.lang.ref.WeakReference;

//public class JDK8ThreadLocal<T> {
//
//	public T get() {
//		Thread t = Thread.currentThread();
//		ThreadLocalMap map = getMap(t);
//		if (map != null) {
//			ThreadLocalMap.Entry e = map.getEntry(this);
//			if (e != null) {
//				@SuppressWarnings("unchecked")
//				T result = (T) e.value;
//				return result;
//			}
//		}
//		return setInitialValue();
//	}
//	
//	ThreadLocalMap getMap(Thread t) {
//        return t.threadLocals;
//    }
//
//	public void set(T value) {
//		Thread t = Thread.currentThread();
//		ThreadLocalMap map = getMap(t);
//		if (map != null)
//			map.set(this, value);
//		else
//			createMap(t, value);
//	}
//
//	void createMap(Thread t, T firstValue) {
//		t.threadLocals = new ThreadLocalMap(this, firstValue);
//	}
//	
//
//}
