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
package com.yxy.chukonu.docker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import com.yxy.chukonu.docker.client.conn.DockerConnection;
import com.yxy.chukonu.docker.global.Constants;

public class ContainerService extends BaseService{

	public ContainerService(DockerConnection conn) {
		super(conn);
	}


	public String createContainer(String imgName, String cName, String... port) {
		String containerId = null ;
		try {
			// Pull an image
			client.pull(imgName);
			// Bind container ports to host ports
			final String[] ports = port;
			final Map<String, List<PortBinding>> portBindings = new HashMap<>();
			for (String p : ports) {
			    List<PortBinding> hostPorts = new ArrayList<>();
			    hostPorts.add(PortBinding.of("0.0.0.0", p));
			    portBindings.put(p, hostPorts);
			}

			final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

			// Create container with exposed ports
			final ContainerConfig containerConfig = ContainerConfig.builder()
			    .hostConfig(hostConfig)
			    .hostname(cName)
			    .image(imgName)
			    .exposedPorts(ports)
			    .build();

			final ContainerCreation creation = client.createContainer(containerConfig);
			containerId = creation.id();

			// Start container
			client.startContainer(containerId);
			
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return containerId ;
	}

	public boolean stopContainer(String cId) {
		try {
			client.stopContainer(cId, Constants.SEC_TO_WAIT_BEFORE_KILLING);
			client.removeContainer(cId);
			
			return true ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} 
		
		return false ;
	}
	
	
	public List<Container> listContainers(){
		try {
			return client.listContainers() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} 
		
		return null ;
	}

	
	
}
