package com.chukonu.consul.client;

import java.util.Map;
import java.util.Map.Entry;

import com.orbitz.consul.Consul;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.cache.NodesCatalogCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.Node;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;

public class Test {

	public static void main(String[] args) {
		Consul consul = Consul.builder().build(); // connect to Consul on localhost
		QueryOptions queryOptions = QueryOptions.BLANK ;
		NodesCatalogCache nodeCatalogCache = NodesCatalogCache.newCache(consul.catalogClient(), queryOptions, 5) ;
		nodeCatalogCache.addListener(new ConsulCache.Listener<String, Node>(){

			@Override
			public void notify(Map<String, Node> newValues) {
				System.out.println("<--------------Node Event:-------------->");
				for (Entry<String, Node> entry : newValues.entrySet()) {
					System.out.println(entry.getKey() + ":" + entry.getValue().toString());
				}
			}});
		nodeCatalogCache.start();
		
		
		String serviceName = "MySQL_datasource";
		ServiceHealthCache svHealth = ServiceHealthCache.newCache(consul.healthClient(), serviceName);
		svHealth.addListener(new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {

			@Override
			public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
				System.out.println("<--------------Service Event:-------------->");
				for (Entry<ServiceHealthKey, ServiceHealth> entry : newValues.entrySet()) {
					System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
				}
			}
		});
		svHealth.start();
		
		
		KVCache kvCache = KVCache.newCache(consul.keyValueClient(), "test", 5); 
		kvCache.addListener(new ConsulCache.Listener<String, Value>() { 
			public void notify(Map<String, Value> newValues) { // Key changed 
				System.out.println("<--------------KV Event:-------------->");
				for (Entry<String, Value> entry : newValues.entrySet()) {
					System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
				}
			} 
		});
		kvCache.start();
		
		
		
		
		while(true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
