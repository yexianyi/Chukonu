package com.yxy.chukonu.java.dp.singleton;

import java.util.HashMap;

public class RegistrySingleton {
	private static HashMap registry = new HashMap();

	static {
		RegistrySingleton rs = new RegistrySingleton();
		registry.put(rs.getClass().getName(), rs);
	}

	// protected constructor granted class inheritance features
	protected RegistrySingleton() {
	}

	public static RegistrySingleton getInstance(String name) {
		if (name == null) {
			name = "RegistrySingleton";
		}

		if(registry.get(name) == null) { //double check
			synchronized(registry) {
				if(registry.get(name) == null) {
					try {
						registry.put(name, Class.forName(name).newInstance());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
		return (RegistrySingleton) registry.get(name);
	}
}
