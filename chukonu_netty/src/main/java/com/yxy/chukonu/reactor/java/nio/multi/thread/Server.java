package com.yxy.chukonu.reactor.java.nio.multi.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class Server {
	
	Selector[] selectors = new Selector[2] ;
	ServerSocketChannel serverSocketChannel ;
	AtomicInteger next = new AtomicInteger(0) ;
	SubReactor[] subReactors = null ;
	int port ;
	
	public Server(int port) {
		this.port = port ;
		try {
			//1. open Selector()
			selectors[0] = Selector.open() ;
			selectors[1] = Selector.open() ;
			
			//2. get Channel
			serverSocketChannel = ServerSocketChannel.open();
			//3. bind non-blocking mode
			serverSocketChannel.configureBlocking(false) ;
			//4. bind port
			ServerSocket serverSocket = serverSocketChannel.socket() ;
			serverSocket.bind(new InetSocketAddress(port)) ;
			
			System.out.println("Server is listening on port : " + port);
			
			//5. listening on new connection event
			SelectionKey event = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT) ;
			//6. bind event handler
			event.attach(new AcceptHandler()) ;
			
			SubReactor subReactor1 = new SubReactor(selectors[0]) ;
			SubReactor subReactor2 = new SubReactor(selectors[1]) ;
			subReactors = new SubReactor[] {subReactor1, subReactor2} ;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public void launch() {
		new Thread(subReactors[0]).start();
		new Thread(subReactors[1]).start();
	}
	
	
	class AcceptHandler implements IEventHandler{

		@Override
		public void handle() {
			try {
				// accept new connection
				SocketChannel channel = serverSocketChannel.accept() ;
				if(channel != null) {
					// execute task in asyn
					new MultiThreadEventHandler(selectors[next.get()] ,channel).handle();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(next.incrementAndGet() == selectors.length) {
				next.set(0);
			}
			
		}
		
	}
	
	
	class SubReactor implements Runnable {
		final Selector selector ;
		
		public SubReactor(Selector selector) {
			this.selector = selector ;
		}
		
		@Override
		public void run() {
			System.out.println("Sub-reactor# " + Thread.currentThread().getId() + " is running...") ;
			try {
				while(!Thread.interrupted()) {
					selector.select() ;
					
					//7. get READY event keys
					Set<SelectionKey> selected = selector.selectedKeys() ;
					Iterator<SelectionKey> iter = selected.iterator() ;
					while(iter.hasNext()) {
						SelectionKey eventKey = iter.next() ;
						//8. dispatch event
						dispatch(eventKey) ;
					}
					
					//9. clear event list, 
					//  otherwise above events will be replayed.
					selected.clear();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void dispatch(SelectionKey eventKey) {
			System.out.println("Sub-reactor# " + Thread.currentThread().getId() + " is dispatching event." ) ;
			
			//get binded handler
			IEventHandler handler = (IEventHandler) eventKey.attachment() ;
			if(handler != null) {
				handler.handle();
			}
		}
		
	}


	public static void main(String[] args) throws Exception {
		Server server = new Server(1234) ;
		server.launch();
	}

}

