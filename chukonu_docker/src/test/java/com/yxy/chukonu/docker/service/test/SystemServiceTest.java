package com.yxy.chukonu.docker.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.spotify.docker.client.messages.Container;
import com.yxy.chukonu.docker.client.conn.DockerConnection;
import com.yxy.chukonu.docker.service.ContainerService;
import com.yxy.chukonu.docker.service.SystemService;


/**
 * Unit test for simple App.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemServiceTest {
	
	final String host1 = "192.168.99.101" ;
	final String host2 = "192.168.99.102" ;
	final String host3 = "192.168.99.103" ;
	final String port = "2376" ;
	final String mgrPort = "2377" ;
	final String certPath1 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1" ;
	final String certPath2 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm2" ;
	final String certPath3 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm3" ;
	
	private DockerConnection conn = null ;
	private SystemService ss = null ;
	private ContainerService cs = null ;
	
	@Before
	public void before() throws Exception {
		conn = new DockerConnection(host1, port, certPath1) ;
		ss = new SystemService(conn) ; 
		cs = new ContainerService(conn) ;
	}
	
	@After
	public void after() throws Exception {
		conn.close();
	}
	
	
	@Test
	public void testGetContainerCpuUsage() {
		List<Container> containers = cs.listContainers() ;
		for(Container c:containers) {
			Float cpu = ss.getCpuUsage(c.id()) ; 
			assertNotNull(cpu) ;
		}
	}
	
	@Test
	public void testGetContainerMemUsage() {
		List<Container> containers = cs.listContainers() ;
		for(Container c:containers) {
			Float mem = ss.getMemUsage(c.id()) ; 
			assertNotNull(mem) ;
		}
	}
	
	@Test
	public void testGetTotalCpuUsage() {
		float totalCpu = ss.getHostCpuUsage() ;
		System.out.println("Total CPU Percent:"+totalCpu) ;
		assertTrue(totalCpu>=0.0) ;
	}
	
	
	
	
	
}
