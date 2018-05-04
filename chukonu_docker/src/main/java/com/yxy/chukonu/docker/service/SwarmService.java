package com.yxy.chukonu.docker.service;

import java.util.List;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.ContainerSpec;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.Service;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.swarm.TaskSpec;

public class SwarmService extends BaseService{

	public SwarmService(String host, String port, String certPath) {
		super(host, port, certPath);
	}
	
	//cmd: docker service create --name=my_nginx nginx  
	public String createService(String sName, String imgName) {
		ContainerSpec containerSpec = ContainerSpec.builder().image(imgName).build() ;
		TaskSpec taskSpec = TaskSpec.builder().containerSpec(containerSpec).build() ;
		ServiceSpec spec = ServiceSpec.builder().name(sName).taskTemplate(taskSpec).build() ;
		
		DockerClient session = null ;
		try {
			session = openSession() ;
			ServiceCreateResponse response = session.createService(spec) ;
			return response.id() ;
			
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return null ;
	}
	
	
	public boolean removeService(String sId) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.removeService(sId); ;
			return true;
			
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return false ;
	}
	
	//cmd: docker service ls
	public List<Service> listService(){
		DockerClient session = null ;
		try {
			session = openSession() ;
			return session.listServices() ;
			
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return null ;
	}
	
	
	//cmd: docker node ls
	public List<Node> listNodes(){
		DockerClient session = null ;
		try {
			session = openSession() ;
			return session.listNodes() ;
			
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return null ;
	}

}
