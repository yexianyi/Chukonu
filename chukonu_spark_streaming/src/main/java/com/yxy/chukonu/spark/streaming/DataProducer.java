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
package com.yxy.chukonu.spark.streaming;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.yxy.chukonu.spark.streaming.util.StringUtil;

public class DataProducer {

	public static void main(String[] args) {
		ServerSocket soc = null ;
		
		try {
			System.out.println("Defining new Socket");
			soc = new ServerSocket(9087);
			
			System.out.println("Waiting for Incoming Connection");
			Socket clientSocket = soc.accept() ;
			
			System.out.println("Connection Received");
			
			OutputStream outputStream = clientSocket.getOutputStream() ;
			
			while(true){
				PrintWriter out = new PrintWriter(outputStream, true) ;
//				BufferedReader read = new BufferedReader(new InputStreamReader(System.in)) ;
//				System.out.println("Waiting for user to input some data");
				String data = "{data:[{col1:val1, col2:val2, col3:val3}, {col1:val4, col2:val5, col3:val6}, {col1:val7, col2:val8, col3:val9}]}" ;
				
				System.out.println("Sending data...["+data+"]");
				out.println(data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(soc!=null){
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		

	}

}
