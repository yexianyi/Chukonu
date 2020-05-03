package com.yxy.chukonu.reactor.java.nio.multi.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEventHandler implements IEventHandler {
	final SocketChannel channel ;
	final SelectionKey sk ;
	static final int RECEIVING = 0, SENDING = 1;
	int state = RECEIVING ;
	final ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
	static ExecutorService pool = Executors.newFixedThreadPool(4) ;
	
	public MultiThreadEventHandler(Selector selector, SocketChannel channel) throws IOException {
		this.channel = channel ;
		channel.configureBlocking(false) ;
		sk = channel.register(selector, 0) ;
		sk.attach(this) ;
		sk.interestOps(SelectionKey.OP_READ) ;
		selector.wakeup() ;
		
	}

	@Override
	public void handle() {
		// execute the real event job in asynchronized mode
		pool.execute(new Runnable() {
			@Override
			public void run() {
				MultiThreadEventHandler.this.asynExec();			
			}
			
		});
	}
	
	public synchronized void asynExec() {
		System.out.println("handling request in asyn...") ;
		try {
			switch(state) {
				case RECEIVING:
					int length = 0 ;
					while((length = channel.read(byteBuffer)) > 0) {
						System.out.println(new String(byteBuffer.array(), 0, length)) ;
					}
					byteBuffer.flip() ;
					sk.interestOps(SelectionKey.OP_WRITE) ;
					state = SENDING ;
					break ;
					
				case SENDING:
					channel.write(byteBuffer) ;
					byteBuffer.clear() ;
					sk.interestOps(SelectionKey.OP_READ) ;
					state = RECEIVING ;
					break ;
					
				default:
			}
			
			sk.cancel(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
