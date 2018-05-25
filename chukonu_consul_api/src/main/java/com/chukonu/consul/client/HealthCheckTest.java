package com.chukonu.consul.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;

public class HealthCheckTest {

	public static void main(String[] args) throws Exception {
		Consul consul = Consul.builder().build(); // connect to Consul on localhost
		AgentClient agentClient = consul.agentClient();

		String serviceName = "MyService";
		String serviceId = "1";

		
		agentClient.register(7777, new URL("http://localhost/test"), 10, serviceName, serviceId,
                new ArrayList<String>(), new HashMap<String, String>()) ; 
		
	}

}
