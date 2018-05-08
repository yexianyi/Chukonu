package com.yxy.chukonu.docker.service;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Info;

public class SystemService extends BaseService {

	public SystemService(String host, String port, String certPath) {
		super(host, port, certPath);
	}

	public Info getDockerInfo() {
		DockerClient session = null ;
		try {
			session = openSession() ;
			return session.info() ;
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
