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
				String data = StringUtil.getRandomString(8) ;
				
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
