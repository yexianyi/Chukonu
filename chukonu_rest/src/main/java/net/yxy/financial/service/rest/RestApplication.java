
package net.yxy.financial.service.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {
	 public RestApplication() {
	     // add rest service packages
	     packages("net.yxy.financial.service.rest");
	 }
}
