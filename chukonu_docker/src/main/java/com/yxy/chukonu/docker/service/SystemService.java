package com.yxy.chukonu.docker.service;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Info;
import com.yxy.chukonu.docker.client.conn.DockerConnection;

public class SystemService extends BaseService{

	public SystemService(DockerConnection conn) {
		super(conn);
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
	
	
}
