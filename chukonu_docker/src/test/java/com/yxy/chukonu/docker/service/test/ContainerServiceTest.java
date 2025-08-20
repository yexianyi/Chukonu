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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spotify.docker.client.messages.Container;
import com.yxy.chukonu.docker.client.conn.DockerConnection;
import com.yxy.chukonu.docker.service.ContainerService;


/**
 * Unit test for simple App.
 */
public class ContainerServiceTest {
	
	final String host = "192.168.99.101" ;
	final String port = "2376" ;
	final String certPath = "C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1" ;
	
	private DockerConnection conn = null ;
	private ContainerService cs = null ;
	
	@Before
	public void before() throws Exception {
		DockerConnection conn = new DockerConnection(host, port, certPath) ; 
		cs = new ContainerService(conn) ; 
	}
	
	@After
	public void after() throws Exception {
		conn.close();
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
	
	@Test
	public void testListContainers() {
		List<Container> containers = cs.listContainers() ;
		assertNotNull(containers);
	}
	
	
}
