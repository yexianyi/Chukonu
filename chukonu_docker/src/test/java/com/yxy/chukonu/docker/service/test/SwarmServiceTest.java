package com.yxy.chukonu.docker.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.Service;
import com.yxy.chukonu.docker.service.SwarmService;


/**
 * Unit test for simple App.
 */
public class SwarmServiceTest {
	
	final String host = "192.168.99.101" ;
	final String port = "2376" ;
	final String certPath = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1" ;
	
	public SwarmService ss = null ;
	
	@Before
	public void before() throws Exception {
		ss = new SwarmService(host, port, certPath) ; 
	}

	
	@Test
	public void testListNode() {
		List<Node> nodes = ss.listNodes() ;
		assertNotNull(nodes);
		assertTrue(nodes.size()>=0) ;
		for(Node node : nodes) {
			System.out.println(node.description().hostname() + " | " + node.status() + " | " + node.spec().availability());
		}
	}
	
	@Test
	public void testCreateService() {
		int originalSize = ss.listService().size() ;
		String sId = ss.createService("service1", "tomcat:7") ;
		assertNotNull(sId);
		assertEquals(originalSize+1, ss.listService().size());
		ss.removeService(sId);
	}
	
	@Test
	public void testRemoveService() {
		int originalSize = ss.listService().size() ;
		String sId = ss.createService("service1", "tomcat:7") ;
		assertNotNull(sId);
		ss.removeService(sId);
		assertEquals(originalSize, ss.listService().size());
		
	}
	
	@Test
	public void testListService() {
		List<Service> services = ss.listService() ;
		assertNotNull(services);
		assertTrue(services.size()>=0) ;
		for(Service service : services) {
			System.out.println(service.spec().name());
		}
	}
	
	
	
}
