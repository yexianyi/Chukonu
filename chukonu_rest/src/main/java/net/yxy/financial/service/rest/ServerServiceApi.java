package net.yxy.financial.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import net.sf.json.JSONException;
import net.yxy.financial.service.global.Constants;

@Path("/admin/info")
public class ServerServiceApi {
	public static final int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	static private Logger logger = LoggerFactory.getLogger(ServerServiceApi.class);  
	
	@GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listInfo(@QueryParam(value = "symbol") String symbol) {
		Client client = Client.create();
		client.setConnectTimeout(Constants.CONNENTION_TIMEOUT);
		WebResource webResource = client.resource("http://finance.yahoo.com/rss/headline?s=" + "ms");
		ClientResponse rsp = webResource.accept("application/json").get(ClientResponse.class);
		
		//redirect
		if(rsp.getStatus()==301){
			// get redirect url from "location" header field
			String newUrl = rsp.getHeaders().getFirst("Location");

			// get the cookie if need, for login
			String cookies = rsp.getHeaders().getFirst("Set-Cookie");

			// open the new connnection again
			client = Client.create();
			client.setConnectTimeout(Constants.CONNENTION_TIMEOUT);
			webResource = client.resource(newUrl);
			webResource.setProperty("Cookie", cookies);
			webResource.setProperty("Accept-Language", "en-US,en;q=0.8");
			webResource.setProperty("User-Agent", "Mozilla");
			webResource.setProperty("Referer", "google.com");
			
			rsp = webResource.accept("application/json").get(ClientResponse.class);
		}
		
		if (rsp.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + rsp.getStatus());
		}

		String output = rsp.getEntity(String.class);

//		System.out.println("Output from Server .... \n");
//		System.out.println(output);
		
		String jsonRsp = null ;
		 try {
	            org.json.JSONObject xmlJSONObj = XML.toJSONObject(output);
	            jsonRsp = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
//	            System.out.println(jsonRsp);
	        } catch (JSONException je) {
	            System.out.println(je.toString());
	        }

		Response.ResponseBuilder response = Response.ok(jsonRsp).type(MediaType.APPLICATION_JSON);
		
		
		///////////////HTTP Cache by Expire////////////////
		//Expires:
		//The Expires response header indicates the amount of time that the response entity should be cached. 
		//It is useful to set the expiration for data that is not going to change for a known time length. 
		//Browsers use this response header to manage their caches among other user agents.
		//The javax.ws.rs.core.Response.ResponseBuilder#expires() method can be used to set the Expires header.
//		Date expirationDate = new Date(System.currentTimeMillis() + 1000*60);
//		response.expires(expirationDate);
		
		///////////////HTTP Cache by Duration////////////////
		CacheControl cc = new CacheControl() ;
		cc.setMaxAge(Constants.REFRESH_INTERVAL);
		response.cacheControl(cc) ;
		
		
		///////////////HTTP Cache by E-tag////////////////

		
		//		logger.debug("Checking if there an Etag and whether there is a change in the order...");
//		EntityTag etag = computeEtagForOrder(list);
//		Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(etag);
//		if (responseBuilder != null) {
//			// Etag match
//			list.debug("Order has not changed..returning unmodified response code");
//			return responseBuilder.build();
//		}
//		list.debug("Returning full Order to the Client");
//		OrderDto orderDto = (OrderDto) beanMapper.map(order, OrderDto.class);
//		responseBuilder = Response.ok(orderDto).tag(etag);
//		return responseBuilder.build();
//		
		
		return response.build();
	}
	
	
	@POST
	@Path("/calProfit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public Response calulateProfit(String requestBody) {
		String result = null ;
		try {
			JSONObject reqBody = new JSONObject(requestBody) ;
			float outcome= Float.parseFloat(reqBody.get("outcome").toString()) ;
			float income = Float.parseFloat(reqBody.get("income").toString()) ;
			int duration= Integer.parseInt(reqBody.get("duration").toString()) ;
			int baseYear= Integer.parseInt(reqBody.get("baseYear").toString()) ;
			
			float RI = income-outcome ;
			//ROE = [((outputMoney-inputMoney) / income)/ duration] * baseYear * 100%
			float ROE = (float)((float)((float)(income-outcome) / outcome)/ duration) * baseYear * 100 ;
		
			result = "{\"RI\":\""+RI+"\","
					+ "\"ROE\":\""+ROE+"\"}";
			
		} catch (org.codehaus.jettison.json.JSONException e) {
			e.printStackTrace();
		}
		
		
		Response.ResponseBuilder response = Response.ok(result).type(MediaType.APPLICATION_JSON);
		
		///////////////HTTP Cache by Duration////////////////
		CacheControl cc = new CacheControl() ;
		cc.setMaxAge(Constants.REFRESH_INTERVAL);
		response.cacheControl(cc) ;
		
		return response.build();

	}
	
}
