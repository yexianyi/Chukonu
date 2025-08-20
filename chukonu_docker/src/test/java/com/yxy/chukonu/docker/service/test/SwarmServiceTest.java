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
package com.yxy.chukonu.docker.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.Service;
import com.yxy.chukonu.docker.client.conn.DockerConnection;
import com.yxy.chukonu.docker.service.SwarmService;


/**
 * Unit test for simple App.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SwarmServiceTest {
	
	final String host1 = "192.168.99.101" ;
	final String host2 = "192.168.99.102" ;
	final String host3 = "192.168.99.103" ;
	final String port = "2376" ;
	final String mgrPort = "2377" ;
	final String certPath1 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1" ;
	final String certPath2 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm2" ;
	final String certPath3 = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm3" ;
	
	private DockerConnection conn = null ;
	private SwarmService ss = null ;
	
	@Before
	public void before() throws Exception {
		conn = new DockerConnection(host1, port, certPath1) ;
		ss = new SwarmService(conn) ;
	}
	
	@After
	public void after() throws Exception {
		conn.close();
	}

	
	@Test
	public void test1CreateCluster() {
		//create manager node on host1
		String joinToken = ss .initCluster(host1, "0.0.0.0:"+mgrPort) ; 
		assertNotNull(joinToken) ;
	}
	
	@Test
	public void test2AddWorkerNode() {
		String joinToken = ss.inspectSwarm().joinTokens().worker() ;
		conn.close();
		
		//create worker node on host1
		conn = new DockerConnection(host2, port, certPath2) ;
		ss = new SwarmService(conn) ;
		assertTrue(ss.joinCluster(joinToken, host1, mgrPort)) ;
		conn.close();
		
		//create worker node on host1
		conn = new DockerConnection(host3, port, certPath3) ; 
		ss = new SwarmService(conn) ;
		assertTrue(ss.joinCluster(joinToken, host1, mgrPort)) ;
		conn.close();
	}
	
	
	@Test
	public void test3ListNode() {
		List<Node> nodes = ss.listNodes() ;
		assertNotNull(nodes);
		assertEquals(3, nodes.size()) ;
		for(Node node : nodes) {
			System.out.println(node.description().hostname() + " | " + node.status() + " | " + node.spec().availability());
		}
	}
	
	
	@Test
	public void test4CreateService() {
		int originalSize = ss.listService().size() ;
		String sId;
		try {
			sId = ss.createService("service8", "vm6", "tomcat:7");
			assertNotNull(sId);
			assertEquals(originalSize+1, ss.listService().size());
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5ListService() {
		List<Service> services = ss.listService() ;
		assertNotNull(services);
		assertEquals(1, services.size()) ;
		for(Service service : services) {
			System.out.println(service.spec().name());
		}
	}
	
	@Test
	public void test6RemoveService() {
		int originalSize = ss.listService().size() ;
		String sId = ss.listService().get(0).id() ;
		assertNotNull(sId);
		ss.removeService(sId);
		assertEquals(originalSize-1, ss.listService().size());
		
	}
	
	
	@Test
	public void test7RemoveWorkerFromCluster() {
		List<String> nodeNames = new ArrayList<String>() ;
		//ask worker node leave cluster gracefully
		DockerConnection conn = new DockerConnection(host2, port, certPath2) ; 
		ss = new SwarmService(conn) ; 
		nodeNames.add(ss.leaveClusterForWorker()) ;
		//ask worker node leave cluster gracefully
		conn = new DockerConnection(host3, port, certPath3) ; 
		ss = new SwarmService(conn) ; 
		nodeNames.add(ss.leaveClusterForWorker()) ;
		conn.close();
		
		//remove worker node entries from manager
		conn = new DockerConnection(host1, port, certPath1) ; 
		ss = new SwarmService(conn) ; 
		for(String nodeName : nodeNames) {
			System.out.println(ss.inspectNode(nodeName).status().state().toUpperCase()) ;
			while(!ss.inspectNode(nodeName).status().state().toUpperCase().equals("DOWN")) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ss.removeWorkerNodeEntry(nodeName) ;
		}
		conn.close();
		conn = new DockerConnection(host1, port, certPath1) ; 
		ss = new SwarmService(conn) ; 
		assertEquals(1, ss.listNodes().size()) ;
		conn.close();
	}
	
	@Test
	public void test8RemoveEntireCluster() {
		//ask manager node leave cluster forcedly, because current cluster is single-node.
		String nodeName = ss.leaveClusterForManager() ;
		assertNotNull(nodeName) ;
	}
	
	@Test
	public void test9GetCpuUsageInNode() {
		float cpuUsage = ss.getCpuUsageInNode() ;
		System.out.println(cpuUsage) ;
		assertTrue(cpuUsage>=0) ;
	}
	
	@Test
	public void test10GetMemUsageInNode() {
		float memUsage = ss.getMemUsageInNode() ;
		System.out.println(memUsage) ;
		assertTrue(memUsage>=0) ;
	}
	
}
