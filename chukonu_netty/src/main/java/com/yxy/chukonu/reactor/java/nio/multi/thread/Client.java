package com.yxy.chukonu.reactor.java.nio.multi.thread;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import org.apache.commons.io.IOUtils;


public class Client {

	public void connect() throws IOException, InterruptedException {
		SocketChannel socketChannel = SocketChannel.open() ;
		socketChannel.socket().connect(new InetSocketAddress("localhost", 1234));
		socketChannel.configureBlocking(false) ;
		
		while(!socketChannel.finishConnect()) {
			System.out.println("connecting to server ...") ;
			Thread.sleep(3000);
		}
		
		//do something
		Thread.sleep(3000);
		
		socketChannel.shutdownOutput() ;
		socketChannel.close();
		System.out.println("Client is closed.") ;
		
	}
	
	public static void main(String[] args) throws Exception {
		Client client = new Client() ;
		client.connect();

	}

}
