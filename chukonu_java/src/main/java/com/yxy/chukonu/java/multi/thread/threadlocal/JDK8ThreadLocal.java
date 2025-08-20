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
package com.yxy.chukonu.java.multi.thread.threadlocal;

public class JDK8ThreadLocal<T> {

	public T get() {
		Thread t = Thread.currentThread();
		ThreadLocalMap map = getMap(t);
		if (map != null) {
			ThreadLocalMap.Entry e = map.getEntry(this);
			if (e != null) {
				@SuppressWarnings("unchecked")
				T result = (T) e.value;
				return result;
			}
		}
		return setInitialValue();
	}
	
	
	ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }

	
	public void set(T value) {
		Thread t = Thread.currentThread();
		ThreadLocalMap map = getMap(t);
		if (map != null)
			map.set(this, value);
		else
			createMap(t, value);
	}
	
	
	private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
	
	
	protected T initialValue() {
        return null;
    }

	
	void createMap(Thread t, T firstValue) {
		t.threadLocals = new ThreadLocalMap(this, firstValue);
	}
	

}
