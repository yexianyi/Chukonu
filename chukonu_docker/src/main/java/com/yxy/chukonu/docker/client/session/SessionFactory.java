package com.yxy.chukonu.docker.client.session;

import java.net.URI;
import java.nio.file.Paths;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

public class SessionFactory {
	private DefaultDockerClient session ;
	
	public DefaultDockerClient createSession() throws DockerCertificateException {
		session = DefaultDockerClient.fromEnv()
				.uri(URI.create("https://192.168.99.101:2376"))
			    .dockerCertificates(new DockerCertificates(Paths.get("C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1")))
			    .build();
		return session ;
	}
	
	protected void closeSession() {
		session.close();;
	}
}
