package com.yxy.chukonu.docker.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.yxy.chukonu.docker.service.ContainerService;


/**
 * Unit test for simple App.
 */
public class ContainerServiceTest {
	
	final String host = "192.168.99.101" ;
	final String port = "2376" ;
	final String certPath = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1" ;
	
	public ContainerService cs = null ;
	
	@Before
	public void before() throws Exception {
		cs = new ContainerService(host, port, certPath) ; 
	}

	@Test
	public void testCreateContainer() {
		String cId = cs.createContainer("tomcat:7", "container1", "80", "8080") ;
		assertNotNull(cId);
		cs.stopContainer(cId) ;
	}
	
	@Test
	public void testStopContainer() {
		String cId = cs.createContainer("tomcat:7", "container1", "80", "8080") ;
		assertTrue(cs.stopContainer(cId));
	}
	
	
}
