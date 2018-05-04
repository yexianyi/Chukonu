package com.yxy.chukonu.docker.service;

import java.net.URI;
import java.nio.file.Paths;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

public abstract class BaseService {
	private DockerClient client ;
	private String host ;
	private String port;
	private String certPath ;
	
	public BaseService(String host, String port, String certPath) {
		this.host = host;
		this.port = port; 
		this.certPath = certPath ;
	}
	
	protected DockerClient openSession() throws DockerCertificateException {
		client = DefaultDockerClient.fromEnv()
				.uri(URI.create("https://"+host+":"+port))
			    .dockerCertificates(new DockerCertificates(Paths.get(certPath)))
			    .build();
		return client ;
	}
	
	protected void closeSession() {
		client.close();
	}
}
