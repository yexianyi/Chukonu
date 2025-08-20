/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
