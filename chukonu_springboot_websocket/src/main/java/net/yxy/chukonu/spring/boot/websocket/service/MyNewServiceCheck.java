package net.yxy.chukonu.spring.boot.websocket.service;

import com.ecwid.consul.v1.agent.model.NewService;

public class MyNewServiceCheck extends NewService.Check {
	
//	@SerializedName("service_id")
	private String serviceId ;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return "Check{" +
				"script='" + getScript() + '\'' +
				", interval='" + getInterval() + '\'' +
				", service_id='" + getServiceId() + '\'' +
				", ttl='" + getTtl() + '\'' +
				", http='" + getHttp() + '\'' +
				", tcp='" + getTcp() + '\'' +
				", timeout='" + getTimeout() + '\'' +
				", deregisterCriticalServiceAfter='" + getDeregisterCriticalServiceAfter() + '\'' +
				", tlsSkipVerify=" + getTlsSkipVerify() +
				", status='" + getStatus() + '\'' +
				'}';
	}

	
	
}
