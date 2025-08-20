/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.java.ping.test;

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
