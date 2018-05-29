package com.yxy.chukonu.docker.client.conn;

import java.net.URI;
import java.nio.file.Paths;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerCertificates.SslContextFactory;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

import lombok.Getter;


public class DockerConnection {
	private DockerClient client ;
	@Getter
	private String host ;
	@Getter
	private String port;
	@Getter
	private String certPath ;
	
	private boolean isClose = true;
	
	public DockerConnection(String host, String port, String certPath) {
		this.host = host;
		this.port = port; 
		this.certPath = certPath ;
	}
	
	public DockerClient open() throws DockerCertificateException {
		if(isClose()) {
			client = DefaultDockerClient.fromEnv()
					.uri(URI.create("https://"+host+":"+port))
					.dockerCertificates(new DockerCertificates(Paths.get(certPath)))
					.build();
			this.isClose = false ;
		}
		
		return client ;
	}
	
	public boolean isClose() {
		return isClose ;
	}
	
	public void close() {
		if(!isClose) {
			client.close();
			this.isClose = true ;
		}
	}
}
