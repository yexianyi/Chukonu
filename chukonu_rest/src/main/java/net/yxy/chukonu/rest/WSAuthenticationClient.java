package net.yxy.chukonu.rest;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class WSAuthenticationClient {
	
	public static boolean validateWithBasicAuth(String url, String username, String password){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = httpClientBuilder.build();
		URL urlObj = null;
		CloseableHttpResponse response = null;
		try {
			urlObj = new URL(url);
			HttpHost targetHost = new HttpHost(urlObj.getHost(), 80, "http");
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
					new UsernamePasswordCredentials(username, password));
	
			// Create AuthCache instance
			AuthCache authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local auth cache
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);
	
			// Add AuthCache to the execution context
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credsProvider);
			context.setAuthCache(authCache);
	
			HttpGet httpget = new HttpGet(urlObj.getPath());
				response = httpclient.execute(targetHost, httpget, context);
				int code = response.getStatusLine().getStatusCode();
				if (code < 400) {
					return true;
				}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}
	

	public static void main(String[] args) {
		System.out.println(validateWithBasicAuth("http://httpbin.org/basic-auth/aaa/aaa", "aaa", "bbb"));

	}

}
