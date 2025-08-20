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
package com.yxy.chukonu.docker.client;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ExecCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

public class DockerClientTest {

	public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {
//		final DockerCertificatesStore cert = 
		// Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
		final DockerClient docker = DefaultDockerClient.fromEnv()
				.uri(URI.create("https://192.168.99.101:2376"))
			    .dockerCertificates(new DockerCertificates(Paths.get("C:\\Users\\Administrator\\.docker\\machine\\machines\\vm1")))
			    .build();

		// Pull an image
		docker.pull("tomcat:7");
		// Bind container ports to host ports
		final String[] ports = {"80", "8080"};
		final Map<String, List<PortBinding>> portBindings = new HashMap<>();
		for (String port : ports) {
		    List<PortBinding> hostPorts = new ArrayList<>();
		    hostPorts.add(PortBinding.of("0.0.0.0", port));
		    portBindings.put(port, hostPorts);
		}

		// Bind container port 443 to an automatically allocated available host port.
		List<PortBinding> randomPort = new ArrayList<>();
		randomPort.add(PortBinding.randomPort("0.0.0.0"));
		portBindings.put("443", randomPort);

		final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

		// Create container with exposed ports
		final ContainerConfig containerConfig = ContainerConfig.builder()
		    .hostConfig(hostConfig)
		    .image("tomcat:7").exposedPorts(ports)
//		    .cmd("sh", "-c", "while :; do sleep 1; done")
		    .build();

		final ContainerCreation creation = docker.createContainer(containerConfig);
		final String id = creation.id();

		// Inspect container
		final ContainerInfo info = docker.inspectContainer(id);

		// Start container
		docker.startContainer(id);

		// Exec command inside running container with attached STDOUT and STDERR
//		final String[] command = {"sh", "-c", "ls"};
//		final ExecCreation execCreation = docker.execCreate(
//		    id, command, DockerClient.ExecCreateParam.attachStdout(),
//		    DockerClient.ExecCreateParam.attachStderr());
//		final LogStream output = docker.execStart(execCreation.id());
//		final String execOutput = output.readFully();

		// Kill container
//		docker.killContainer(id);

		// Remove container
//		docker.removeContainer(id);

		// Close the docker client
		docker.close();

	}

}
