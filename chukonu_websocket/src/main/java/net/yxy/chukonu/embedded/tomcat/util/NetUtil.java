package net.yxy.chukonu.embedded.tomcat.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetUtil {
	
	public static InetAddress getHostInfo() {
		 try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;   
	}

	public static void main(String[] args) {
		System.out.println(NetUtil.getHostInfo().getHostName());
		System.out.println(NetUtil.getHostInfo().getHostAddress());

	}

}
