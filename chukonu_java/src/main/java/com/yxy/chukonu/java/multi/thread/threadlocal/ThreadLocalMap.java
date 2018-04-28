package com.yxy.chukonu.java.multi.thread.threadlocal;

import java.lang.ref.WeakReference;

public class ThreadLocalMap {
	
	static class Entry extends WeakReference<ThreadLocal<?>> {
        /** The value associated with this ThreadLocal. */
        Object value;

        Entry(ThreadLocal<?> k, Object v) {
            super(k);
            value = v;
        }
    }
}
