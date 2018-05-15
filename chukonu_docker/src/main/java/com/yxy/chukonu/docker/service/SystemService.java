package com.yxy.chukonu.docker.service;

import java.util.List;

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
