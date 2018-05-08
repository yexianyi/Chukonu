package com.yxy.chukonu.docker.service;

import java.util.ArrayList;
import java.util.List;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.ContainerSpec;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeInfo;
import com.spotify.docker.client.messages.swarm.Service;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.swarm.Swarm;
import com.spotify.docker.client.messages.swarm.SwarmInit;
import com.spotify.docker.client.messages.swarm.SwarmJoin;
import com.spotify.docker.client.messages.swarm.TaskSpec;

public class SwarmService extends BaseService{

	public SwarmService(String host, String port, String certPath) {
		super(host, port, certPath);
	}
	
	
	public String initCluster(String advertiseAddr, String listenAddr) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			SwarmInit swarmInit = SwarmInit.builder().advertiseAddr(advertiseAddr).listenAddr(listenAddr).build() ;
			return session.initSwarm(swarmInit) ;
			
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
	
	public Swarm inspectSwarm() {
		DockerClient session = null ;
		try {
			session = openSession() ;
			return session.inspectSwarm() ;
			
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
	
	
	public NodeInfo inspectNode(String nodeId) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			return session.inspectNode(nodeId) ;
			
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
	
	/**
	 * --advertise-addr is the address other nodes in the Docker swarm use to connect into your node.
	 * --listen-addr is the address that the swarm service listens on for incoming connections.
	 * 
	 * 
	 * @param joinToken
	 * @param mgrAddress
	 * @param mgrPort
	 * @param remoteAddrs
	 * @return
	 */
	public boolean joinCluster(String joinToken, String mgrAddr, String listeningPort) {
		DockerClient session = null ;
		List<String> remoteAddrs = new ArrayList<String>() ;
		remoteAddrs.add(mgrAddr+":"+listeningPort) ;
		try {
			session = openSession() ;
			SwarmJoin swarmJoin = SwarmJoin.builder()
					.joinToken(joinToken)
					.advertiseAddr(mgrAddr+":"+listeningPort)
					.listenAddr("0.0.0.0:"+listeningPort)
					.remoteAddrs(remoteAddrs) //remoteAddrs equal to advertise-addr
					.build() ;
			session.joinSwarm(swarmJoin);
			return true ;
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
	
	
	/**
	 * Make current node leave current swarm cluster.
	 * However, the node entry is still in the cluster list marked as "DOWN" status.
	 * @return
	 */
	public String leaveClusterForWorker() {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.leaveSwarm();
		
			return session.info().name() ;
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
	
	
	/**
	 * Make current node leave current swarm cluster.
	 * However, the node entry is still in the cluster list marked as "DOWN" status.
	 * @return
	 */
	public String leaveClusterForManager() {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.leaveSwarm(true);
			
			return session.info().name() ;
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
	
	
	/**
	 * Remove current node entry from cluster list.
	 * This method is often called after leaveCluster().
	 * @param nodeId
	 * @return
	 */
	public boolean removeWorkerNodeEntry(String nodeName) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.deleteNode(nodeName);
			return true ;
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
	
	
	/**
	 * Remove current node entry from cluster list.
	 * This method is often called after leaveCluster().
	 * @param nodeId
	 * @return
	 */
	public boolean removeManagerNodeEntry(String nodeName) {
		DockerClient session = null ;
		try {
			session = openSession() ;
			session.deleteNode(nodeName, true);
			return true ;
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
			if(e.getMessage().contains("This node is not a swarm manager")) {
				return new ArrayList<Node>() ;
			}
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closeSession() ;
		}
		
		return null ;
	}

}
