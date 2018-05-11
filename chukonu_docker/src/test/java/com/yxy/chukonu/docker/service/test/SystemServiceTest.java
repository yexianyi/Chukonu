package com.yxy.chukonu.docker.service.test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.yxy.chukonu.docker.client.conn.DockerConnection;
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
	
	@Before
	public void before() throws Exception {
		conn = new DockerConnection(host1, port, certPath1) ;
		ss = new SystemService(conn) ; 
	}
	
	@After
	public void after() throws Exception {
		conn.close();
	}

	
	@Test
	public void testGetContainerCpuUsage() {
		//create manager node on host1
		Float status = ss.getCpuUsage("035b5548d8bf") ; 
		System.out.println(status);
		assertNotNull(status) ;
	}
	
	@Test
	public void testGetContainerMemUsage() {
		//create manager node on host1
		Float status = ss.getMemUsage("035b5548d8bf") ; 
		System.out.println(status);
		assertNotNull(status) ;
	}
	
	
	
}
