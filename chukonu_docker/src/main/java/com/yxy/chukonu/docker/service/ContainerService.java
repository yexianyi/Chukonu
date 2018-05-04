package com.yxy.chukonu.docker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import com.yxy.chukonu.docker.global.Constants;

public class ContainerService extends BaseService {

	public ContainerService(String host, String port, String certPath) {
		super(host, port, certPath);
	}

	public String createContainer(String imgName, String cName, String... port) {
		DockerClient session = null ;
		String containerId = null ;
		try {
			session = openSession() ;
			// Pull an image
			session.pull(imgName);
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

			final ContainerCreation creation = session.createContainer(containerConfig);
			containerId = creation.id();

			// Start container
			session.startContainer(containerId);
			
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return containerId ;
	}

	public boolean stopContainer(String cId) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.stopContainer(cId, Constants.SEC_TO_WAIT_BEFORE_KILLING);
			session.removeContainer(cId);
			
			return true ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		} catch (DockerException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return false ;
	}

	
	
}
