package com.chokonu.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestConnection {
	
	protected static void pingTest(String url, int timeout) {
    	// replace "https" with "http" to avoid SSL certificate
        url = url.replaceFirst("^https", "http");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if( responseCode < 200 || responseCode > 399){
            	throw new IOException(Integer.valueOf(responseCode).toString()) ;
            }
        } catch (IOException e) {
        	System.out.println("Failed") ;
        	e.printStackTrace();
        }
        
        System.out.println("PASS") ;

    }

	public static void main(String[] args) {
		pingTest("http://www.google.com",5000) ;
	}

}
