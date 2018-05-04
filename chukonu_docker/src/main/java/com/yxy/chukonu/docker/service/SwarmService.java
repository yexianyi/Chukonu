package com.yxy.chukonu.docker.service;

import java.util.List;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.Service;

public class SwarmService extends BaseService{

	public SwarmService(String host, String port, String certPath) {
		super(host, port, certPath);
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
