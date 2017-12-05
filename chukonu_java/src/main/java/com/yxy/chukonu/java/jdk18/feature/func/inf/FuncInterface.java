package com.yxy.chukonu.java.jdk18.feature.func.inf;

@FunctionalInterface
public interface FuncInterface<F, T> {
	 T convert(F from);

}
