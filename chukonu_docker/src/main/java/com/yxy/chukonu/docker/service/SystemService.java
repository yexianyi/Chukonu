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

import java.util.List;

import org.jvnet.hk2.annotations.Service;

import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.Info;
import com.yxy.chukonu.docker.client.conn.DockerConnection;

public class SystemService extends BaseService{
	
	private ContainerService cs = null ;

	public SystemService(DockerConnection conn) {
		super(conn);
		cs = new ContainerService(conn) ;
	}


	public Info getDockerInfo() {
		try {
			return client.info() ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
	public Float getCpuUsage(String containerId) {
		try {
			float cpuUsage = client.stats(containerId).cpuStats().cpuUsage().totalUsage().floatValue();
			float sysCpuUsage = client.stats(containerId).cpuStats().systemCpuUsage().floatValue();
			
			return (cpuUsage/sysCpuUsage) ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return null ;
	}
	
	
	public Float getMemUsage(String containerId) {
		try {
			float memUsage = client.stats(containerId).memoryStats().usage().floatValue();
			return memUsage ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return null ;
	}
	
	public long getMemLimit() {
		return getDockerInfo().memTotal() ;
		
	}
	
	public Float getHostCpuUsage() {
		List<Container> containers = cs.listContainers() ;
		float sum = 0 ;
		for(Container c:containers) {
			sum += getCpuUsage(c.id()) ;
		}
		
		return sum ;
	}
	
	
	public Float getHostMemUsage() {
		List<Container> containers = cs.listContainers() ;
		float sum = 0 ;
		for(Container c:containers) {
			sum += getMemUsage(c.id()) ;
		}
		
		return sum ;
	}
	
}
