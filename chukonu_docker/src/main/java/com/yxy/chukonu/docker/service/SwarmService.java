package com.yxy.chukonu.docker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.ContainerSpec;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeInfo;
import com.spotify.docker.client.messages.swarm.Placement;
import com.spotify.docker.client.messages.swarm.Service;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.swarm.Swarm;
import com.spotify.docker.client.messages.swarm.SwarmInit;
import com.spotify.docker.client.messages.swarm.SwarmJoin;
import com.spotify.docker.client.messages.swarm.TaskSpec;
import com.yxy.chukonu.docker.client.conn.DockerConnection;

public class SwarmService extends BaseService{
	private DockerConnection conn = null ;

	public SwarmService(DockerConnection conn) {
		super(conn);
		this.conn = conn ;
	}

	
	public String initCluster(String advertiseAddr, String listenAddr) {
		SwarmInit swarmInit = SwarmInit.builder().advertiseAddr(advertiseAddr).listenAddr(listenAddr).build() ;
		try {
			return client.initSwarm(swarmInit) ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null ;
		
	}
	
	public Swarm inspectSwarm() {
		try {
			return client.inspectSwarm() ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		return null ;
	}
	
	
	public NodeInfo inspectNode(String nodeId) {
		try {
			return client.inspectNode(nodeId) ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		List<String> remoteAddrs = new ArrayList<String>() ;
		remoteAddrs.add(mgrAddr+":"+listeningPort) ;
		SwarmJoin swarmJoin = SwarmJoin.builder()
				.joinToken(joinToken)
				.advertiseAddr(mgrAddr+":"+listeningPort)
				.listenAddr("0.0.0.0:"+listeningPort)
				.remoteAddrs(remoteAddrs) //remoteAddrs equal to advertise-addr
				.build() ;
		try {
			client.joinSwarm(swarmJoin);
			return true ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false ;
	}
	
	
	/**
	 * Make current node leave current swarm cluster.
	 * However, the node entry is still in the cluster list marked as "DOWN" status.
	 * @return
	 */
	public String leaveClusterForWorker() {
		try {
			client.leaveSwarm();
			return client.info().name() ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
	/**
	 * Make current node leave current swarm cluster.
	 * However, the node entry is still in the cluster list marked as "DOWN" status.
	 * @return
	 */
	public String leaveClusterForManager() {
		try {
			client.leaveSwarm(true);
			return client.info().name() ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		try {
			client.deleteNode(nodeName);
			return true ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		try {
			client.deleteNode(nodeName, true);
			return true ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false ;
	}
	
	
	public String createService(String serviceName, String imgName) throws DockerException, InterruptedException {
		return createService(serviceName, null, imgName) ;
	}
	
	//cmd: docker service create --name=my_nginx nginx  
	public String createService(String serviceName, String targetHost, String imgName) throws DockerException, InterruptedException {
		Map<String, String> labels = new HashMap<String, String>() ;
		labels.put("alias", targetHost) ;
		ContainerSpec containerSpec = ContainerSpec.builder().image(imgName).build() ;
		TaskSpec taskSpec = null ;
		if(targetHost==null) {
			List<String> constraints = new ArrayList<String>() ;
			constraints.add("node.labels.alias=="+targetHost) ;
			taskSpec = TaskSpec.builder().placement(Placement.create(constraints)).containerSpec(containerSpec).build() ;
		}else {
			taskSpec = TaskSpec.builder().containerSpec(containerSpec).build() ;
		}
		
		ServiceSpec spec = ServiceSpec.builder()
				.name(serviceName)
				.taskTemplate(taskSpec)
				.build() ;
		
		ServiceCreateResponse response;
		response = client.createService(spec);
		return response.id() ;
			
	}
	
	
	public boolean removeService(String sId) {
		try {
			client.removeService(sId);
			return true;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return false ;
	}
	
	//cmd: docker service ls
	public List<Service> listService(){
		try {
			return client.listServices() ;
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		return null ;
	}
	
	
	//cmd: docker node ls
	public List<Node> listNodes(){
		try {
			return client.listNodes() ;
		} catch (DockerException e) {
			if(e.getMessage().contains("This node is not a swarm manager")) {
				return new ArrayList<Node>() ;
			}
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
	public float getCpuUsageInNode() {
		ContainerService cs = new ContainerService(this.conn) ;
		List<Container> containers = cs.listContainers() ;
		float sum = 0.0f ;
		for(Container c:containers) {
			SystemService ss = new SystemService(this.conn) ;
			sum += ss.getCpuUsage(c.id()) ;
		}
		
		return sum ;
	}
	
	public float getMemUsageInNode() {
		ContainerService cs = new ContainerService(this.conn) ;
		List<Container> containers = cs.listContainers() ;
		float sum = 0.0f ;
		for(Container c:containers) {
			SystemService ss = new SystemService(this.conn) ;
			sum += ss.getMemUsage(c.id()) ;
		}
		SystemService ss = new SystemService(this.conn) ;
		return (float) (sum/ss.getMemLimit()) ;
	}
	

}
