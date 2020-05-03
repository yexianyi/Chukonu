package com.yxy.chukonu.netty.single.thread.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


public class Server implements Runnable{
	
	Selector selector ;
	ServerSocketChannel serverSocketChannel ;
	int port ;
	
	public Server(int port) {
		this.port = port ;
	}
	

	@Override
	public void run() {
		
		try {
			//1. get Selector()
			selector = Selector.open() ;
			//2. get Channel
			serverSocketChannel = ServerSocketChannel.open();
			ServerSocket serverSocket = serverSocketChannel.socket() ;
			//3. bind non-blocking mode
			serverSocketChannel.configureBlocking(false) ;
			//4. bind port
			serverSocket.bind(new InetSocketAddress(port)) ;
			
			System.out.println("Server is listening on port : " + port);
			
			//5.register ACCEPT Event
			SelectionKey event = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT) ;
			//6. bind event handler
			event.attach(new AcceptHandler()) ;
			
			while(!Thread.interrupted()) {
				selector.select() ;
				//7. get READY event keys
				Set<SelectionKey> selected = selector.selectedKeys() ;
				Iterator<SelectionKey> iter = selected.iterator() ;
				while(iter.hasNext()) {
					SelectionKey eventKey = iter.next() ;
					if(eventKey.isAcceptable()) {
						// this line is essential, 
						// otherwise the ACCEPT event will be replayed
						serverSocketChannel.accept() ;
					}
					
					//8. dispatch event
					dispatch(eventKey) ;
				}
				
				//9. clear event list, 
				//   otherwise above events will be replayed.
				selected.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	private void dispatch(SelectionKey eventKey) {
		//get binded handler
		Runnable handler = (Runnable) eventKey.attachment() ;
		if(handler != null) {
			handler.run();
		}
		
	}
	
	
	class AcceptHandler implements Runnable{

		@Override
		public void run() {
			System.out.println("handling new connection.");
			
		}
		
	}


	public static void main(String[] args) throws Exception {
		Server server = new Server(1234) ;
		server.run();
	}

}
