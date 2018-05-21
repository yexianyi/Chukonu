package com.chukonu.consul.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.coordinate.model.Node;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest {
	
	String consulServer = "192.168.1.13" ;
	ConsulClient client ;
	
	@Before
	public void before() {
		client = new ConsulClient("localhost");
		
	}
	
	@Test
    public void test1JoinAgentServer(){
		Response<Void> response = client.agentJoin(consulServer, false) ;
		System.out.println(response.toString());
        assertTrue( true );
    }
	
	@Test
    public void test2ListNodes(){
		QueryParams queryParams = QueryParams.Builder.builder().build() ;
		Response<List<Node>> nodes = client.getNodes(queryParams) ;
		List<Node> list = nodes.getValue() ;
		for(Node node:list) {
			System.out.println(node.toString());
		}
        assertTrue( true );
    }
	
    
	@Test
    public void test3CreateService(){
    	NewService newService = new NewService();
		newService.setId("MySQL_Datasource");
		newService.setName("MySQL_datasource");
		newService.setAddress("www.sohu.com");
		newService.setTags(Arrays.asList("Datasource Adapter", "MySQL"));
		newService.setPort(80);
		
		Map<String, String> metas = new HashMap<String, String>() ;
		metas.put("db_url", "jdbc:mysql://localhost:3306/Test") ;
		metas.put("db_username", "root") ;
		metas.put("db_password", "root") ;
		newService.setMeta(metas);
		
		try {
			Response<Void> response = client.agentServiceRegister(newService);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);

    }
	
	
	@Test
    public void test4FindService(){
    	String tag = "Datasource Adapter" ;
    	QueryParams queryParams = QueryParams.Builder.builder().build() ;
		Response<List<CatalogService>> services = client.getCatalogService("MySQL_datasource", tag, queryParams) ;
		List<CatalogService> list = services.getValue() ;
		for(CatalogService service:list) {
			System.out.println(service.toString());
		}
        assertTrue(list.size()>0);
    }
	
	
	@Test
    public void test5RemoveService(){
    	String tag = "Datasource Adapter" ;
    	QueryParams queryParams = QueryParams.Builder.builder().build() ;
		Response<List<CatalogService>> services = client.getCatalogService("MySQL_datasource", tag, queryParams) ;
		List<CatalogService> list = services.getValue() ;
		for(CatalogService service:list) {
			client.agentServiceDeregister(service.getServiceId()) ;
		}
        assertEquals(0, client.getCatalogService("MySQL_datasource", tag, queryParams).getValue().size());
    }
	
	
    
	@Test
    public void testLeaveAgentServer(){
		Response<Void> response = client.agentForceLeave(consulServer) ;
		System.out.println(response.toString());
        assertTrue( true );
    }
    
}
