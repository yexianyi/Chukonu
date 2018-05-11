package com.yxy.chukonu.docker.service;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.yxy.chukonu.docker.client.conn.DockerConnection;

public abstract class BaseService {
	protected DockerClient client ;
	
	public BaseService(DockerConnection conn) {
		try {
			this.client = conn.open();
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		}
	}
	
}
