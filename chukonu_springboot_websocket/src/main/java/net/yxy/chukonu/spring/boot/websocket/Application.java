package net.yxy.chukonu.spring.boot.websocket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chukonu.consul.api.util.NetUtil;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
//    	registerService() ;
    	if(args.length==0) {
    		SpringApplication.run(Application.class, args);
    	}

    	if(args[0].equalsIgnoreCase("server")) {
    		SpringApplication.run(Application.class, args);
    	}else if(args[0].equalsIgnoreCase("client")){
    		try {
				registerService() ;
				WebsocketTestClient.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}else {
    		System.exit(0);
    	}
    	
    }
    
    
    public static void registerService() throws Exception {
		//register service
    	String consulAgent = System.getenv().get("CONSUL_AGENT"); ;
    	NewService newService = new NewService();
    	String id= System.currentTimeMillis()+"" ;
		newService.setId("Datasource_"+id);
		newService.setName("MySQL_datasource_"+id);
		newService.setAddress(NetUtil.getLocalHostLANAddress().getHostAddress());
		newService.setTags(Arrays.asList("Datasource Adapter", "MySQL"));
		newService.setPort(80);
	
		
		Map<String, String> metas = new HashMap<String, String>() ;
		metas.put("db_url", "jdbc:mysql://localhost:3306/Test") ;
		metas.put("db_username", "root") ;
		metas.put("db_password", "root") ;
		newService.setMeta(metas);
		
		try {
			ConsulClient client = new ConsulClient(consulAgent);
			Response<Void> response = client.agentServiceRegister(newService);
			
//			QueryParams queryParams = QueryParams.Builder.builder().build();
//			Response<List<Node>> nodes = client.getNodes(queryParams) ;
//			List<Node> list = nodes.getValue() ;
//			client.get
			
			//internal container service check
			NewCheck selfCheck = new NewCheck();
			selfCheck.setName("MySelfCheck");
			selfCheck.setHttp(NetUtil.getLocalHostLANAddress().getHostAddress());
			selfCheck.setInterval("10s");
			selfCheck.setServiceId(newService.getId());
			response = client.agentCheckRegister(selfCheck) ;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
}